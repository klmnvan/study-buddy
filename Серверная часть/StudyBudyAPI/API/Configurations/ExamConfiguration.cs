using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class ExamConfiguration : IEntityTypeConfiguration<Exam>
    {
        public void Configure(EntityTypeBuilder<Exam> builder)
        {
            builder.HasKey(e => e.IdExam);
            builder.HasMany(e => e.Notes).WithOne(n => n.Exam).OnDelete(DeleteBehavior.Cascade).HasForeignKey(n => n.IdExam); //Связь один ко многим (у экзамена много заметок, у заметки один экзамен)
            builder.HasOne(e => e.User).WithMany(u => u.Exams).OnDelete(DeleteBehavior.Cascade).HasForeignKey(e => e.IdUser);
        }
    }
}
