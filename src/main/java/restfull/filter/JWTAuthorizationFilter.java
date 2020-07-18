package restfull.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static restfull.filter.JWTAuthenticationFilter.HEADER_STRING;
import static restfull.filter.JWTAuthenticationFilter.SECRET;
import static restfull.filter.JWTAuthenticationFilter.TOKEN_PREFIX;

/**
 * JWTAuthorizationFilter.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(final AuthenticationManager manager) {
        super(manager);
    }

    @Override
    protected final void doFilterInternal(final HttpServletRequest request,
                                          final HttpServletResponse response,
                                          final FilterChain chain) throws IOException, ServletException {
        final String header = request.getHeader(HEADER_STRING);
        if (header == null || header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        final UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(final HttpServletRequest req) {
        final String token = req.getHeader(HEADER_STRING);
        if (token != null) {
            final String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }
}
