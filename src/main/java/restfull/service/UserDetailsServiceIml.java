package restfull.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import restfull.model.Person;
import restfull.repository.UserStore;

import java.util.Collections;

/**
 * UserDetailsServiceIml.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
@Service
public class UserDetailsServiceIml implements UserDetailsService {
    private final UserStore store;

    public UserDetailsServiceIml(final UserStore store) {
        this.store = store;
    }

    @Override
    public final UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        final Person person = this.store.findByUserName(username);
        if (person != null) {
            return new User(person.getUsername(), person.getPassword(), Collections.emptyList());
        }
        throw new UsernameNotFoundException(username);
    }
}
