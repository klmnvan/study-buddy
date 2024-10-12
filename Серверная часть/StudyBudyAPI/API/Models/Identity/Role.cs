using Microsoft.AspNetCore.Identity;

namespace StudyBudyAPI.Models.Account
{
    public class Role : IdentityRole<Guid>
    {
        public Role()
        {
            Id = Guid.NewGuid();
        }

        public Role(string roleName) : this()
        {
            Name = roleName;
        }
    }
}
