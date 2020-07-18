package restfull.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Person.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/8/2020
 */
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private String username;
    private String password;

    public Person() {
    }

    public Person(final int aId, final String aUserName, final String aPassword) {
        this.id = aId;
        this.username = aUserName;
        this.password = aPassword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        final Person person = (Person) o;
        return getId().equals(person.getId())
                && getUsername().equals(person.getUsername())
                && getPassword().equals(person.getPassword());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword());
    }

    @Override
    public final String toString() {
        return new StringJoiner(", ",
                Person.class.getSimpleName() + "[", "]")
                .add("id=" + this.id)
                .add("usename='" + this.username + "'")
                .add("password='" + this.password + "'")
                .toString();
    }
}
