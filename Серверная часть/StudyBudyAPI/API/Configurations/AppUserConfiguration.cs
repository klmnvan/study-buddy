using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyBudyAPI.Models.Account;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class AppUserConfiguration : IEntityTypeConfiguration<AppUser>
    {
        public void Configure(EntityTypeBuilder<AppUser> builder)
        {
            builder.HasOne(au => au.UserNavigation).WithOne(u => u.AppUser).HasForeignKey<User>(u => u.IdUser).OnDelete(DeleteBehavior.Cascade).IsRequired();
        }
    }
}
