package fall24.swp391.KoiOrderingSystem.service;

import fall24.swp391.KoiOrderingSystem.model.request.AccountRequest;
import fall24.swp391.KoiOrderingSystem.model.request.AccountUpdateRequest;
import fall24.swp391.KoiOrderingSystem.model.request.GoogleRequest;
import fall24.swp391.KoiOrderingSystem.model.response.AccountResponse;
import fall24.swp391.KoiOrderingSystem.model.request.RegisterRequest;
import fall24.swp391.KoiOrderingSystem.pojo.Account;

import java.util.List;

public interface IAuthenticationService {
    public AccountResponse register(RegisterRequest registerRequest) throws Exception;

    public AccountResponse login(AccountRequest accountRequest);

    public List<Account> getAllAccount();

    public String loginOrRegisterGoogle(GoogleRequest googleRequest);

    public boolean updateAccount(AccountUpdateRequest accountRequest);

    public void banAccount(Long accountId);
}
