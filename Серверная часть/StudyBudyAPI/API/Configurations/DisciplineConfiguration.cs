using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class DisciplineConfiguration : IEntityTypeConfiguration<Discipline>
    {
        public void Configure(EntityTypeBuilder<Discipline> builder)
        {
            builder.HasKey(d => d.IdDiscipline);
            builder.HasMany(d => d.Requirements).WithOne(r => r.Discipline).OnDelete(DeleteBehavior.Cascade).HasForeignKey(r => r.IdDiscipline);
            builder.HasMany(d => d.Tasks).WithOne(t => t.Discipline).OnDelete(DeleteBehavior.Cascade).HasForeignKey(t => t.IdDiscipline);
            builder.HasOne(d => d.User).WithMany(u => u.Disciplines).OnDelete(DeleteBehavior.Cascade).HasForeignKey(d => d.IdUser);
            builder.HasOne(d => d.Teacher).WithMany(t => t.Disciplines).OnDelete(DeleteBehavior.Cascade).HasForeignKey(d => d.IdTeacher).IsRequired(false);
            builder.HasIndex(it => it.Title).IsUnique();
        }
    }


}
