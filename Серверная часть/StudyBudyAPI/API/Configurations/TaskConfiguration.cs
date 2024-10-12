using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

namespace StudyBudyAPI.Configurations
{
    public class TaskConfiguration : IEntityTypeConfiguration<Models.DB.Task>
    {
        public void Configure(EntityTypeBuilder<Models.DB.Task> builder)
        {
            builder.HasKey(t => t.IdTask);
            builder.HasOne(t => t.User).WithMany(u => u.Tasks).OnDelete(DeleteBehavior.Cascade).HasForeignKey(t => t.IdUser);
            builder.HasOne(t => t.Discipline).WithMany(d => d.Tasks).OnDelete(DeleteBehavior.Cascade).HasForeignKey(t => t.IdDiscipline);
        }
    }


}
