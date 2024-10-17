package fall24.swp391.KoiOrderingSystem.component;

import fall24.swp391.KoiOrderingSystem.pojo.Account;
import fall24.swp391.KoiOrderingSystem.repo.IAccountRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class Token {
    // chia khoa de xac nhan token
//    public final String SECRET_KEY = "fb64a681139d1586b6f2004d18159afd57c8c79136d7490630407c";
    public final String SECRET_KEY = "fb64a681139d1586b6f2004d18159afd57c8c79136d7490630407casqwserkwqq";

    @Autowired
    IAccountRepository accountRepository;

    // Tạo chìa khóa để xác thực token
    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
        //hmacShaKeyFor nhận mảng byte của khóa (keyBytes) và tạo ra một đối tượng SecretKey
        // có thể dùng để ký hoặc xác thực JWT.
    }
    public String generateToken(Account account){
        String token = Jwts.builder().subject(account.getId()+"")
                .claim("role", account.getRole())
                .claim("lastName",account.getLastName())
                .claim("firstName",account.getFirstName())
                .claim("email",account.getEmail())
                .claim("phone",account.getPhone())
                .claim("address",account.getAddress())
                .claim("gender",account.getGender())
                .claim("nationality",account.getNationality())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                .signWith(getSignInKey())
                .compact();
        return token;
    }
    public Account getAccountByToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long id = Long.parseLong(claims.getSubject());
        return accountRepository.findAccountById(id);
    }

}
