using StudyBudyAPI.Models.Account;

namespace StudyBudyAPI.Interfaces
{
    public interface ITokenService
    {
        string CreateToken(AppUser user);
    }
}
