package restfull.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import restfull.model.Person;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * JWTAuthenticationFilter.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; /* 10 days */
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";

    private final AuthenticationManager auth;

    public JWTAuthenticationFilter(final AuthenticationManager auth) {
        this.auth = auth;
    }

    @Override
    public final Authentication attemptAuthentication(final HttpServletRequest request,
                                                      final HttpServletResponse response)
            throws AuthenticationException {
        try {
            final Person person = new ObjectMapper().readValue(request.getInputStream(), Person.class);
            return this.auth.authenticate(new UsernamePasswordAuthenticationToken(
                    person.getUsername(),
                    person.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected final void successfulAuthentication(final HttpServletRequest request,
                                                  final HttpServletResponse response,
                                                  final FilterChain chain,
                                                  final Authentication authResult) {
        final String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
