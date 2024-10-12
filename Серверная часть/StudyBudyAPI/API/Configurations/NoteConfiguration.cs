using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;
using StudyBudyAPI.Models.DB;

namespace StudyBudyAPI.Configurations
{
    public class NoteConfiguration : IEntityTypeConfiguration<Note>
    {
        public void Configure(EntityTypeBuilder<Note> builder)
        {
            builder.HasKey(n => n.IdNote);
            //у одной заметки только 1 экзамен, а у экзаменов множество
            builder.HasOne(n => n.Exam).WithMany(e => e.Notes).OnDelete(DeleteBehavior.Cascade).HasForeignKey(n => n.IdExam);
        }
    }


}
