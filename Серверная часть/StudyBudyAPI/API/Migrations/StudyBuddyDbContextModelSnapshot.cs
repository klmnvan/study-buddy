﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using Npgsql.EntityFrameworkCore.PostgreSQL.Metadata;
using StudyBudyAPI.Data;

#nullable disable

namespace StudyBudyAPI.Migrations
{
    [DbContext(typeof(StudyBuddyDbContext))]
    partial class StudyBuddyDbContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "8.0.10")
                .HasAnnotation("Relational:MaxIdentifierLength", 63);

            NpgsqlModelBuilderExtensions.UseIdentityByDefaultColumns(modelBuilder);

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityRoleClaim<System.Guid>", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("Id"));

                    b.Property<string>("ClaimType")
                        .HasColumnType("text");

                    b.Property<string>("ClaimValue")
                        .HasColumnType("text");

                    b.Property<Guid>("RoleId")
                        .HasColumnType("uuid");

                    b.HasKey("Id");

                    b.HasIndex("RoleId");

                    b.ToTable("AspNetRoleClaims", (string)null);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserClaim<System.Guid>", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("Id"));

                    b.Property<string>("ClaimType")
                        .HasColumnType("text");

                    b.Property<string>("ClaimValue")
                        .HasColumnType("text");

                    b.Property<Guid>("UserId")
                        .HasColumnType("uuid");

                    b.HasKey("Id");

                    b.HasIndex("UserId");

                    b.ToTable("AspNetUserClaims", (string)null);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserLogin<System.Guid>", b =>
                {
                    b.Property<string>("LoginProvider")
                        .HasColumnType("text");

                    b.Property<string>("ProviderKey")
                        .HasColumnType("text");

                    b.Property<string>("ProviderDisplayName")
                        .HasColumnType("text");

                    b.Property<Guid>("UserId")
                        .HasColumnType("uuid");

                    b.HasKey("LoginProvider", "ProviderKey");

                    b.HasIndex("UserId");

                    b.ToTable("AspNetUserLogins", (string)null);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserRole<System.Guid>", b =>
                {
                    b.Property<Guid>("UserId")
                        .HasColumnType("uuid");

                    b.Property<Guid>("RoleId")
                        .HasColumnType("uuid");

                    b.HasKey("UserId", "RoleId");

                    b.HasIndex("RoleId");

                    b.ToTable("AspNetUserRoles", (string)null);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserToken<System.Guid>", b =>
                {
                    b.Property<Guid>("UserId")
                        .HasColumnType("uuid");

                    b.Property<string>("LoginProvider")
                        .HasColumnType("text");

                    b.Property<string>("Name")
                        .HasColumnType("text");

                    b.Property<string>("Value")
                        .HasColumnType("text");

                    b.HasKey("UserId", "LoginProvider", "Name");

                    b.ToTable("AspNetUserTokens", (string)null);
                });

            modelBuilder.Entity("StudyBudyAPI.Models.Account.AppUser", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<int>("AccessFailedCount")
                        .HasColumnType("integer");

                    b.Property<string>("ConcurrencyStamp")
                        .IsConcurrencyToken()
                        .HasColumnType("text");

                    b.Property<string>("Email")
                        .HasMaxLength(256)
                        .HasColumnType("character varying(256)");

                    b.Property<bool>("EmailConfirmed")
                        .HasColumnType("boolean");

                    b.Property<bool>("LockoutEnabled")
                        .HasColumnType("boolean");

                    b.Property<DateTimeOffset?>("LockoutEnd")
                        .HasColumnType("timestamp with time zone");

                    b.Property<string>("NormalizedEmail")
                        .HasMaxLength(256)
                        .HasColumnType("character varying(256)");

                    b.Property<string>("NormalizedUserName")
                        .HasMaxLength(256)
                        .HasColumnType("character varying(256)");

                    b.Property<string>("PasswordHash")
                        .HasColumnType("text");

                    b.Property<string>("PhoneNumber")
                        .HasColumnType("text");

                    b.Property<bool>("PhoneNumberConfirmed")
                        .HasColumnType("boolean");

                    b.Property<string>("SecurityStamp")
                        .HasColumnType("text");

                    b.Property<bool>("TwoFactorEnabled")
                        .HasColumnType("boolean");

                    b.Property<string>("UserName")
                        .HasMaxLength(256)
                        .HasColumnType("character varying(256)");

                    b.HasKey("Id");

                    b.HasIndex("NormalizedEmail")
                        .HasDatabaseName("EmailIndex");

                    b.HasIndex("NormalizedUserName")
                        .IsUnique()
                        .HasDatabaseName("UserNameIndex");

                    b.ToTable("AspNetUsers", (string)null);
                });

            modelBuilder.Entity("StudyBudyAPI.Models.Account.Role", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid");

                    b.Property<string>("ConcurrencyStamp")
                        .IsConcurrencyToken()
                        .HasColumnType("text");

                    b.Property<string>("Name")
                        .HasMaxLength(256)
                        .HasColumnType("character varying(256)");

                    b.Property<string>("NormalizedName")
                        .HasMaxLength(256)
                        .HasColumnType("character varying(256)");

                    b.HasKey("Id");

                    b.HasIndex("NormalizedName")
                        .IsUnique()
                        .HasDatabaseName("RoleNameIndex");

                    b.ToTable("AspNetRoles", (string)null);

                    b.HasData(
                        new
                        {
                            Id = new Guid("5aa4fea9-c52f-4a01-a052-78d3d4f9d52f"),
                            Name = "Admin",
                            NormalizedName = "ADMIN"
                        },
                        new
                        {
                            Id = new Guid("44620187-2cfd-4418-a2c6-950794d86d77"),
                            Name = "User",
                            NormalizedName = "USER"
                        });
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Discipline", b =>
                {
                    b.Property<int>("IdDiscipline")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("IdDiscipline"));

                    b.Property<int?>("IdTeacher")
                        .HasColumnType("integer");

                    b.Property<Guid>("IdUser")
                        .HasColumnType("uuid");

                    b.Property<string>("Title")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("IdDiscipline");

                    b.HasIndex("IdTeacher");

                    b.HasIndex("IdUser");

                    b.ToTable("Disciplines");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Exam", b =>
                {
                    b.Property<int>("IdExam")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("IdExam"));

                    b.Property<DateOnly>("DateExam")
                        .HasColumnType("date");

                    b.Property<string>("Duration")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<Guid>("IdUser")
                        .HasColumnType("uuid");

                    b.Property<int>("NumberTickets")
                        .HasColumnType("integer");

                    b.HasKey("IdExam");

                    b.HasIndex("IdUser");

                    b.ToTable("Exams");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Note", b =>
                {
                    b.Property<int>("IdNote")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("IdNote"));

                    b.Property<string>("Content")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<int>("IdExam")
                        .HasColumnType("integer");

                    b.HasKey("IdNote");

                    b.HasIndex("IdExam");

                    b.ToTable("Notes");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Requirement", b =>
                {
                    b.Property<int>("IdRequirement")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("IdRequirement"));

                    b.Property<string>("Content")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<int>("IdDiscipline")
                        .HasColumnType("integer");

                    b.HasKey("IdRequirement");

                    b.HasIndex("IdDiscipline");

                    b.ToTable("Requirements");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Task", b =>
                {
                    b.Property<int>("IdTask")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("IdTask"));

                    b.Property<DateOnly>("Deadline")
                        .HasColumnType("date");

                    b.Property<string>("Description")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<int?>("IdDiscipline")
                        .HasColumnType("integer");

                    b.Property<Guid>("IdUser")
                        .HasColumnType("uuid");

                    b.Property<bool>("IsCompleted")
                        .HasColumnType("boolean");

                    b.Property<string>("Title")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("IdTask");

                    b.HasIndex("IdDiscipline");

                    b.HasIndex("IdUser");

                    b.ToTable("Tasks");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Teacher", b =>
                {
                    b.Property<int>("IdTeacher")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("integer");

                    NpgsqlPropertyBuilderExtensions.UseIdentityByDefaultColumn(b.Property<int>("IdTeacher"));

                    b.Property<string>("FullName")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<Guid>("IdUser")
                        .HasColumnType("uuid");

                    b.Property<string>("OfficeNumber")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("IdTeacher");

                    b.HasIndex("IdUser");

                    b.ToTable("Teachers");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.User", b =>
                {
                    b.Property<Guid>("IdUser")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("uuid")
                        .HasDefaultValueSql("gen_random_uuid()");

                    b.Property<string>("Email")
                        .IsRequired()
                        .HasColumnType("text");

                    b.Property<string>("Nickname")
                        .IsRequired()
                        .HasColumnType("text");

                    b.HasKey("IdUser");

                    b.ToTable("Users");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityRoleClaim<System.Guid>", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.Account.Role", null)
                        .WithMany()
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserClaim<System.Guid>", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.Account.AppUser", null)
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserLogin<System.Guid>", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.Account.AppUser", null)
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserRole<System.Guid>", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.Account.Role", null)
                        .WithMany()
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.HasOne("StudyBudyAPI.Models.Account.AppUser", null)
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.IdentityUserToken<System.Guid>", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.Account.AppUser", null)
                        .WithMany()
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Discipline", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.DB.Teacher", "Teacher")
                        .WithMany("Disciplines")
                        .HasForeignKey("IdTeacher")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("StudyBudyAPI.Models.DB.User", "User")
                        .WithMany("Disciplines")
                        .HasForeignKey("IdUser")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Teacher");

                    b.Navigation("User");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Exam", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.DB.User", "User")
                        .WithMany("Exams")
                        .HasForeignKey("IdUser")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("User");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Note", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.DB.Exam", "Exam")
                        .WithMany("Notes")
                        .HasForeignKey("IdExam")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Exam");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Requirement", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.DB.Discipline", "Discipline")
                        .WithMany("Requirements")
                        .HasForeignKey("IdDiscipline")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Discipline");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Task", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.DB.Discipline", "Discipline")
                        .WithMany("Tasks")
                        .HasForeignKey("IdDiscipline")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("StudyBudyAPI.Models.DB.User", "User")
                        .WithMany("Tasks")
                        .HasForeignKey("IdUser")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("Discipline");

                    b.Navigation("User");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Teacher", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.DB.User", "User")
                        .WithMany("Teachers")
                        .HasForeignKey("IdUser")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("User");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.User", b =>
                {
                    b.HasOne("StudyBudyAPI.Models.Account.AppUser", "AppUser")
                        .WithOne("UserNavigation")
                        .HasForeignKey("StudyBudyAPI.Models.DB.User", "IdUser")
                        .OnDelete(DeleteBehavior.Cascade)
                        .IsRequired();

                    b.Navigation("AppUser");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.Account.AppUser", b =>
                {
                    b.Navigation("UserNavigation")
                        .IsRequired();
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Discipline", b =>
                {
                    b.Navigation("Requirements");

                    b.Navigation("Tasks");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Exam", b =>
                {
                    b.Navigation("Notes");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.Teacher", b =>
                {
                    b.Navigation("Disciplines");
                });

            modelBuilder.Entity("StudyBudyAPI.Models.DB.User", b =>
                {
                    b.Navigation("Disciplines");

                    b.Navigation("Exams");

                    b.Navigation("Tasks");

                    b.Navigation("Teachers");
                });
#pragma warning restore 612, 618
        }
    }
}
