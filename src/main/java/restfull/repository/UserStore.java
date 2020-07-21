package restfull.repository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import restfull.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * UserStore.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/18/2020
 */
@Component
public class UserStore {
    private final ConcurrentHashMap<String, Person> users = new ConcurrentHashMap<>();

    public final void save(final Person person) {
        users.put(person.getUsername(), person);
    }


    public final Person findByUserName(final String username) {
        return users.get(username);
    }

    public final List<Person> findAll() {
        return new ArrayList<>(users.values());
    }
}
