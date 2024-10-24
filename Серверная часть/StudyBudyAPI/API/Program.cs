using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Mvc.Infrastructure;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using StudyBudyAPI.Data;
using StudyBudyAPI.Interfaces;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Service;
using System.Text;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc.Abstractions;
using StudyBudyAPI.Repository;
using Microsoft.Extensions.Options;
using Microsoft.OpenApi.Any;
using System.ComponentModel;

namespace StudyBudyAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddControllers().AddJsonOptions(options =>
            {
                options.JsonSerializerOptions.Converters.Add(new DateOnlyJsonConverter());
            }); 

            builder.Services.AddEndpointsApiExplorer();

            builder.Services.AddSwaggerGen(option =>
            {
                option.EnableAnnotations();
                option.SwaggerDoc("v1", new OpenApiInfo { Title = "StudyBuddy API", Version = "v1", Description = "API для системы приложений Study Buddy" });
                //option.OperationFilter<SwaggerDefaultValues>();
                option.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
                {
                    In = ParameterLocation.Header,
                    Description = "Введите действительный токен",
                    Name = "Authorization",
                    Type = SecuritySchemeType.Http,
                    BearerFormat = "JWT",
                    Scheme = "Bearer"
                });
                option.AddSecurityRequirement(new OpenApiSecurityRequirement
                {
                    {
                        new OpenApiSecurityScheme
                        {
                            Reference = new OpenApiReference
                            {
                                Type=ReferenceType.SecurityScheme,
                                Id="Bearer"
                            }
                        },
                        new List<string>()
                    }
                });
                option.MapType<DateOnly>(() => new OpenApiSchema
                {
                    Type = "string",
                    Format = "date",
                    Example = new OpenApiString("2022-01-01")
                });
            });

            builder.Services.AddDbContext<StudyBuddyDbContext>(options =>
            {
                options.UseNpgsql(
                    builder.Configuration.GetConnectionString(nameof(StudyBuddyDbContext)));
            });

            builder.Services.AddScoped<ITokenService, TokenService>();
            builder.Services.AddScoped<ITaskRepository, TaskRepository>();
            builder.Services.AddScoped<IDisciplineRepository, DisciplineRepository>();
            builder.Services.AddScoped<IRequirementRepository, RequirementRepository>();
            builder.Services.AddScoped<ITeacherRepository, TeacherRepository>();
            builder.Services.AddScoped<IExamRepository, ExamRepository>();
            builder.Services.AddScoped<INoteRepository, NoteRepository>();

            builder.Services.AddIdentity<AppUser, Role>(options =>
            {
                options.Password.RequiredLength = 8;
                options.Password.RequireDigit = false;
                options.Password.RequireNonAlphanumeric = false;
                options.Password.RequireUppercase = false;
                options.Password.RequireLowercase = false;
                options.User.RequireUniqueEmail = true;
            })
                .AddRoles<Role>()
                .AddEntityFrameworkStores<StudyBuddyDbContext>();

            var validIssuer = builder.Configuration.GetValue<string>("JWT:Issuer");
            var validAudience = builder.Configuration.GetValue<string>("JWT:Audience");
            var symmetricSecurityKey = builder.Configuration.GetValue<string>("JWT:SigningKey");

            builder.Services.AddAuthentication(options =>  // схема аутентификации - с помощью jwt-токенов
            {
                options.DefaultAuthenticateScheme = 
                options.DefaultChallengeScheme = 
                options.DefaultScheme = 
                options.DefaultForbidScheme = 
                options.DefaultSignInScheme = 
                options.DefaultSignOutScheme = JwtBearerDefaults.AuthenticationScheme;
            }).AddJwtBearer(options => // подключение аутентификации с помощью jwt-токенов;
            {
                options.RequireHttpsMetadata = false;
                options.SaveToken = true;
                options.IncludeErrorDetails = true;
                options.TokenValidationParameters = new TokenValidationParameters
                {
                    ClockSkew = TimeSpan.Zero,
                    ValidateIssuer = true,
                    ValidateLifetime = true,
                    ValidateAudience = true,
                    ValidIssuer = validIssuer,
                    ValidAudience = validAudience,
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(
                        Encoding.UTF8.GetBytes(builder.Configuration["JWT:SigningKey"])
                    )
                };
                options.Events = new JwtBearerEvents //Для создания кастомного сообщения о том, что пользователь не авторизован
                {
                    OnChallenge = async context =>
                    {
                        // Call this to skip the default logic and avoid using the default response
                        context.HandleResponse();

                        var httpContext = context.HttpContext;
                        var statusCode = StatusCodes.Status401Unauthorized;

                        var routeData = httpContext.GetRouteData();
                        var actionContext = new ActionContext(httpContext, routeData, new ActionDescriptor());

                        var factory = httpContext.RequestServices.GetRequiredService<ProblemDetailsFactory>();
                        var problemDetails = factory.CreateProblemDetails(httpContext, statusCode);

                        var result = new ObjectResult(problemDetails) { StatusCode = statusCode };
                        await result.ExecuteResultAsync(actionContext);
                    }
                };
            });
            builder.Services.AddAuthorization(options =>
            {
                options.FallbackPolicy = new AuthorizationPolicyBuilder()
                    .RequireAuthenticatedUser()
                    .Build();
            }); //для того, чтобы для каждого запроса нужна была атворизация


            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment() || app.Environment.IsProduction())
            {
                app.UseDeveloperExceptionPage();
                app.UseSwagger();
                app.UseSwaggerUI(options =>
                {
                    options.SwaggerEndpoint("./v1/swagger.json", "v1"); //Относительный путь до пользовательского интерфейса
                });
            }

            app.UseHttpsRedirection();

            app.UseAuthentication();
            app.UseAuthorization();

            app.MapControllers();

            /*app.MapControllerRoute(
                name: "default",
                pattern: "{controller=Home}/{action=Index}/{id?}");*/

            app.Run();
        }
    }
}
