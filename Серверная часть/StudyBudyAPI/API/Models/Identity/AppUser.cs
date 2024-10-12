using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using StudyBudyAPI.Models.DB;
using System.Security;

namespace StudyBudyAPI.Models.Account
{
    public class AppUser : IdentityUser<Guid>
    {
        public User UserNavigation { get; set; }

        public AppUser()
        {
            Id = Guid.NewGuid();
            SecurityStamp = Guid.NewGuid().ToString();
        }

        public AppUser(string userName) : this()
        {
            UserName = userName;
        }
    }


}