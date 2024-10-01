package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.component.Token;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.DuplicateEntity;
import fall24.swp391.KoiOrderingSystem.model.AccountRequest;
import fall24.swp391.KoiOrderingSystem.model.AccountResponse;
import fall24.swp391.KoiOrderingSystem.model.RegisterRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.User;
import fall24.swp391.KoiOrderingSystem.repo.IAccountRepository;
import fall24.swp391.KoiOrderingSystem.repo.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthenticationService implements IAuthenticationService, UserDetailsService {

    @Autowired
    Token token;

    @Autowired
    IAccountRepository accountRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    Token getToken;

    @Override
    public AccountResponse register(RegisterRequest registerRequest) throws Exception {
        Account account = modelMapper.map(registerRequest,Account.class);
        User user = modelMapper.map(registerRequest,User.class);
       try {
           account.setPassword(passwordEncoder.encode(account.getPassword()));
           Account newAccount = accountRepository.save(account);
           User newUser = userRepository.save(user);
           return modelMapper.map(account,AccountResponse.class);
       } catch (Exception e){
            if(e.getMessage().contains(account.getEmail())){
                throw new DuplicateEntity("Duplicate email");
            }
            else throw new Exception(e.getMessage());
       }
    }

    @Override
    public AccountResponse login(AccountRequest accountRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    accountRequest.getEmail(),accountRequest.getPassword()
            ));
            Account account = (Account) authentication.getPrincipal();
            AccountResponse accountResponse = modelMapper.map(account,AccountResponse.class);
            accountResponse.setToken(getToken.generateToken(account));
            return  accountResponse;
        } catch (Exception e){
            throw new AccountNotFoundException("Username or password invalid");
        }
    }

    @Override
    public List<Account> getAllAccount() {
        List<Account> list = accountRepository.findAll();
        return list;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return accountRepository.findAccountByEmail(email);
    }
}
