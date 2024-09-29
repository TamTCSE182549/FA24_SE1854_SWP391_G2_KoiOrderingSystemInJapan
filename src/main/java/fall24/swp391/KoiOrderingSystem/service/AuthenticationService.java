package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.component.Token;
import fall24.swp391.KoiOrderingSystem.model.AccountRequest;
import fall24.swp391.KoiOrderingSystem.model.AccountResponse;
import fall24.swp391.KoiOrderingSystem.model.RegisterRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    @Autowired
    Token token;

    @Autowired
    IAccountRepository accountRepository;
    @Override
    public AccountResponse register(AccountRequest accountRequest) {
        return null;
    }

    @Override
    public AccountResponse login(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public List<Account> getAllAccount() {
        return List.of();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(email);
    }
}
