using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class RequirementConfiguration : IEntityTypeConfiguration<Requirement>
    {
        public void Configure(EntityTypeBuilder<Requirement> builder)
        {
            builder.HasKey(r => r.IdRequirement);
            builder.HasOne(r => r.Discipline).WithMany(d => d.Requirements).OnDelete(DeleteBehavior.Cascade).HasForeignKey(r => r.IdDiscipline);
            builder.HasIndex(r => r.Content).IsUnique();
        }
    }


}
