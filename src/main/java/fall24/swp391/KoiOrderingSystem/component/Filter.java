package fall24.swp391.KoiOrderingSystem.component;

import fall24.swp391.KoiOrderingSystem.exception.AuthException;
import fall24.swp391.KoiOrderingSystem.pojo.Account;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.List;
@Component
public class Filter extends OncePerRequestFilter {

    @Autowired
    Token tokenService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    HandlerExceptionResolver resolver;

    private final List<String> AUTH_PERMISSION = List.of(
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/api/login",
            "/api/register",
            "/api/forgot-password",
            "/api/google",
            "/tour/listTourResponseActive",
            "/tour/showAll",
            "/tour/showAllPageable",
            "/tour/showTourByName/{tourName}",
            "/kois/all",
            "/kois/getby/{Id}",
            "/koi-farm/list-farm-active",
            "/koi-farm/get/{id}",
            "/quotations/all",
            "/tour/findById/{tourId}",
            "/tour/findFarmByKoiName/{koiName}"

    );

    public boolean checkIsPublicAPI(String uri){
        //uri: /api/register
        AntPathMatcher pathMatcher = new AntPathMatcher();
        //neu gap api tren list cho phep truy cap neu ko check token
        return AUTH_PERMISSION.stream().anyMatch(pattern -> pathMatcher.match(pattern,uri));
    }
    public String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null; // Kiểm tra xem header có tồn tại và bắt đầu bằng "Bearer " không
        }
        return authHeader.substring(7); // Trích xuất token (bỏ tiền tố "Bearer ")
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
        //check xem api nguoi dung yeu cau co cho phep ai truy cap duoc
        boolean isPublicAPI = checkIsPublicAPI(request.getRequestURI());
     //   isPublicAPI = true;
        if(isPublicAPI){
            filterChain.doFilter(request,response);
        } else{
            String token = getToken(request);
            if(token==null){
                //ko truy cap dc
                resolver.resolveException(request,response,null,new AuthException("Empty token!"));
                return;
            }
            //=> co token
            //check xem token co dung hay ko => lay thong tin account tu token
            Account account = new Account();
            try {
                account = tokenService.getAccountByToken(token);
            } catch (ExpiredJwtException e){
                //token het han
                resolver.resolveException(request,response,null,new AuthException("Expired token!"));
                return;
            } catch (MalformedJwtException malformedJwtException){
                // token sai
                resolver.resolveException(request,response,null,new AuthException("Invalid token!"));
                return;
            }
            //token dung roi => cho phep truy cap
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    account,token,account.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            //token ok cho vao`
            filterChain.doFilter(request,response);
        }



    }

}
