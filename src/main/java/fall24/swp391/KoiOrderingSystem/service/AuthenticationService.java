package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.component.Email;
import fall24.swp391.KoiOrderingSystem.component.Token;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.AuthException;
import fall24.swp391.KoiOrderingSystem.exception.DuplicateEntity;
import fall24.swp391.KoiOrderingSystem.model.request.AccountRequest;
import fall24.swp391.KoiOrderingSystem.model.request.ForgotPassRequest;
import fall24.swp391.KoiOrderingSystem.model.request.ResetPasswordRequest;
import fall24.swp391.KoiOrderingSystem.model.response.AccountResponse;
import fall24.swp391.KoiOrderingSystem.model.EmailDetail;
import fall24.swp391.KoiOrderingSystem.model.request.RegisterRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.pojo.Role;
import fall24.swp391.KoiOrderingSystem.pojo.User;
import fall24.swp391.KoiOrderingSystem.repo.IAccountRepository;
import fall24.swp391.KoiOrderingSystem.repo.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    Email emailService;

    @Override
    public AccountResponse register(RegisterRequest registerRequest) {
        Account account = modelMapper.map(registerRequest,Account.class);
        User user = modelMapper.map(registerRequest,User.class);
        user.setAccount(account);
        account.setActive(true);
       try {
           account.setPassword(passwordEncoder.encode(account.getPassword()));
           account.setRole(Role.CUSTOMER);
           Account newAccount = accountRepository.save(account);
           User newUser = userRepository.save(user);

           EmailDetail emailDetail = new EmailDetail();
           emailDetail.setReceiver(newAccount);
           emailDetail.setSubject("Hello");
           emailDetail.setLink("fb.com");
           emailService.sendEmail(emailDetail);


           return modelMapper.map(account,AccountResponse.class);
       } catch (Exception e){
            if(e.getMessage().contains(account.getEmail())){
                throw new DuplicateEntity("Duplicate email");
            }
            else throw new AuthException(e.getMessage());
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
    public Account getCurrentAccount(){
        Account account =(Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountRepository.findAccountById(account.getId());
    }

    public void forgotPassword(ForgotPassRequest forgotPassRequest){
        Account account = accountRepository.findAccountByEmail(forgotPassRequest.getEmail());
        if(account==null) {
            throw new AccountNotFoundException("Account not found");
        } else{
            EmailDetail emailDetail = new EmailDetail();
            emailDetail.setReceiver(account);
            emailDetail.setSubject(token.generateToken(account));
            emailDetail.setLink("fb.com" );
            emailService.sendEmail(emailDetail);
        }
    }

    public void resetPassword(ResetPasswordRequest resetPasswordRequest){
        Account account = getCurrentAccount();
        account.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        accountRepository.save(account);
    }
}
