package controller;

import fall24.swp391.KoiOrderingSystem.component.Email;
import fall24.swp391.KoiOrderingSystem.component.Token;
import fall24.swp391.KoiOrderingSystem.exception.AccountNotFoundException;
import fall24.swp391.KoiOrderingSystem.exception.AuthException;
import fall24.swp391.KoiOrderingSystem.exception.DuplicateEntity;
import fall24.swp391.KoiOrderingSystem.model.request.AccountRequest;
import fall24.swp391.KoiOrderingSystem.model.request.RegisterRequest;
import fall24.swp391.KoiOrderingSystem.model.response.AccountResponse;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.repo.IAccountRepository;
import fall24.swp391.KoiOrderingSystem.service.AuthenticationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
@WebMvcTest
public class TestService {
    private AuthenticationService authenticationService = new AuthenticationService();
    @Autowired
    AuthenticationService authenticationServiceMock;


    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Token getToken;

    @Mock
    private Authentication authentication;

    @Mock
    private IAccountRepository accountRepository;


    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private Email emailService;



    private RegisterRequest registerRequest;
    private Account account;
    private AccountResponse accountResponse;
    private Validator validator;
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = (Validator) Validation.buildDefaultValidatorFactory().getValidator();

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        account = new Account();
        account.setEmail(registerRequest.getEmail());
        account.setPassword(registerRequest.getPassword());

        accountResponse = new AccountResponse();
        accountResponse.setEmail(account.getEmail());
    }



    @Test
    public void testRegister_Successful() {
        // Mock the modelMapper to map RegisterRequest to Account
//        when(modelMapper.map(registerRequest, Account.class)).thenReturn(account);
        // Mock password encoder to return encoded password
//        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword123");
        // Mock accountRepository to return saved account
//        when(accountRepository.save(any(Account.class))).thenReturn(account);
        // Mock the modelMapper to map Account to AccountResponse
//        when(modelMapper.map(account, AccountResponse.class)).thenReturn(accountResponse);

        // Call the method
//        AccountResponse result = authenticationService.register(registerRequest);
        AccountResponse response = authenticationService.register(registerRequest);
        // Verify the method worked as expected
        assertNotNull(response);
        assertEquals(response.getEmail(), registerRequest.getEmail());

        // Verify interactions with mocks
        verify(passwordEncoder).encode(registerRequest.getPassword());
        verify(accountRepository).save(account);
//        verify(emailService).sendEmail(any(EmailDetail.class));
    }

    @Test(expectedExceptions = DuplicateEntity.class)
    public void testRegister_DuplicateEmail() {
        // Mock the modelMapper to map RegisterRequest to Account
        when(modelMapper.map(registerRequest, Account.class)).thenReturn(account);
        // Mock an exception when saving the account
        when(accountRepository.save(any(Account.class)))
                .thenThrow(new RuntimeException("registerRequest.getEmail()"));

        // Call the method, expecting an exception
        authenticationService.register(registerRequest);
    }

    @Test(expectedExceptions = AuthException.class)
    public void testRegister_AuthException() {
        // Mock the modelMapper to map RegisterRequest to Account
        when(modelMapper.map(registerRequest, Account.class)).thenReturn(account);
        // Mock an exception when saving the account
        when(accountRepository.save(any(Account.class)))
                .thenThrow(new RuntimeException("Some other error"));

        // Call the method, expecting an AuthException
        authenticationService.register(registerRequest);
    }

    @Test
    public void testLoginSuccess() {
        // Dữ liệu mẫu cho AccountRequest
        AccountRequest accountRequest = new AccountRequest("minh@gmail.com", "string");

        // Dữ liệu mẫu cho Account
        Account mockAccount = mock(Account.class); // giả lập đối tượng
        when(mockAccount.getId()).thenReturn(1L); // mô phỏng hàm get trả đúng giá trị

        when(authentication.getPrincipal()).thenReturn(mockAccount);

        // Giả lập hành vi của authenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);

        // Giả lập hành vi của getToken
        when(getToken.generateToken(mockAccount)).thenReturn("mockToken");

        // Gọi phương thức login
        AccountResponse response = authenticationService.login(accountRequest);

        // Kiểm tra phản hồi
        assertNotNull(response);
        assertEquals(response.getEmail(), accountRequest.getEmail());
        assertEquals(response.getPassword(), accountRequest.getPassword());
        assertEquals(response.getToken(), "mockToken111");
    }

    @Test//(expectedExceptions = AccountNotFoundException.class)
    public void testLoginFailure() {
        // Dữ liệu mẫu cho AccountRequest
        AccountRequest accountRequest = new AccountRequest("minh@gmail.com", "string");

        // Giả lập hành vi của authenticationManager ném ra ngoại lệ
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AccountNotFoundException("Username or password invalid"));

        // Gọi phương thức login, mong muốn ném ra AccountNotFoundException

        try {
            // Call the login method, expecting it to throw an AccountNotFoundException
            authenticationService.login(accountRequest);
            fail("Expected AccounetNotFoundExcption was not thrown");
        } catch (Exception e) {
            // Capture and assert the exception message
//            assertEquals(e.getMessage(), "Username or password invalid");
            assertTrue(e.getMessage().equals("Username or password invalidasd"));
        }
    }
    @Test
    public void testInvalidEmail() {
        // Create AccountRequest with invalid email
        AccountRequest accountRequest = new AccountRequest("invalidEmail", "password");

        // Validate
        Set<ConstraintViolation<AccountRequest>> violations = validator.validate(accountRequest);

        // Ensure there is a violation for the email
        assertEquals(violations.size(), 1);
        assertTrue(violations.iterator().next().getMessage().contains("Email not valid"));
    }

}
