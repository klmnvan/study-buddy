using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class TeacherConfiguration : IEntityTypeConfiguration<Teacher>
    {
        public void Configure(EntityTypeBuilder<Teacher> builder)
        {

            builder.HasKey(t => t.IdTeacher);
            builder.HasMany(t => t.Disciplines).WithOne(d => d.Teacher).OnDelete(DeleteBehavior.Cascade).HasForeignKey(d => d.IdTeacher).IsRequired(false);
            builder.HasOne(d => d.User).WithMany(u => u.Teachers).OnDelete(DeleteBehavior.Cascade).HasForeignKey(d => d.IdUser);
            builder.HasIndex(it => it.FullName).IsUnique();
        }
    }


}
