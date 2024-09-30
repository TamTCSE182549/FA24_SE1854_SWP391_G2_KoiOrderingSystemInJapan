package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.AccountRequest;
import fall24.swp391.KoiOrderingSystem.model.AccountResponse;
import fall24.swp391.KoiOrderingSystem.model.RegisterRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;

import java.util.List;

public interface IAuthenticationService {
    public AccountResponse register(AccountRequest accountRequest) throws Exception;

    public AccountResponse login(RegisterRequest registerRequest);

    public List<Account> getAllAccount();
}
