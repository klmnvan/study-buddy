using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using Microsoft.EntityFrameworkCore.Metadata.Internal;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class UserConfiguration : IEntityTypeConfiguration<User>
    {
        public void Configure(EntityTypeBuilder<User> builder)
        {
            builder.HasKey(u => u.IdUser);
            builder.Property(u => u.IdUser).HasDefaultValueSql("gen_random_uuid()");
            builder.HasMany(u => u.Tasks).WithOne(t => t.User).OnDelete(DeleteBehavior.Cascade).HasForeignKey(t => t.IdUser);
            builder.HasMany(u => u.Exams).WithOne(e => e.User).OnDelete(DeleteBehavior.Cascade).HasForeignKey(e => e.IdUser);
            builder.HasMany(u => u.Disciplines).WithOne(d => d.User).OnDelete(DeleteBehavior.Cascade).HasForeignKey(d => d.IdUser);
            
        }
    }
}
