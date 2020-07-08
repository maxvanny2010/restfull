package restfull.repository;

import org.springframework.data.repository.CrudRepository;
import restfull.model.Person;

/**
 * PersonRepository.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/8/2020
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {
}
