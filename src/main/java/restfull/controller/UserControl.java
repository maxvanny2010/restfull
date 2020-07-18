package restfull.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restfull.model.Person;
import restfull.repository.UserStore;

import java.util.List;

/**
 * UserControl.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
@RestController
@RequestMapping("/users")
public class UserControl {
    private final UserStore users;
    private final BCryptPasswordEncoder encoder;

    public UserControl(final UserStore users, final BCryptPasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @PostMapping("/sign-up")
    public final void singUp(@RequestBody final Person person) {
        person.setPassword(this.encoder.encode(person.getPassword()));
        this.users.save(person);
    }

    @GetMapping("/all")
    public final List<Person> findAll() {
        return this.users.findAll();
    }

}
