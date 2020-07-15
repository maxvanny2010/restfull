package restfull.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import restfull.RestFullApp;
import restfull.model.Person;
import restfull.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PersonControllerTest.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 7/15/2020
 */
@RunWith(SpringRunner.class)
@TestPropertySource("classpath:application.properties")
@SpringBootTest(classes = RestFullApp.class)
@AutoConfigureMockMvc
@Sql(scripts = "classpath:db/update_001.sql", config = @SqlConfig(encoding = "UTF-8"))
public class PersonControllerTest {
    @MockBean
    private PersonRepository persons;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper jo;


    @Test
    public void findAll() throws Exception {
        final String substring = "[{\"id\":1,\"login\":\"user\",\"password\":\"123\"}]";
        Person one = new Person(1, "user", "123");
        when(this.persons.findAll()).thenReturn(List.of(one));
        this.mockMvc.perform(get("/person/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(substring)));
    }

    @Test
    public void findById() throws Exception {
        final String substring = "{\"id\":1,\"login\":\"user\",\"password\":\"123\"}";
        final Person person = new Person(1, "user", "123");
        when(this.persons.findById(1)).thenReturn(Optional.of(person));
        this.mockMvc.perform(get("/person/{id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(substring)));

    }

    @Test
    public void create() throws Exception {
        final Person person = new Person(3, "NEW", "123");
        this.mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jo.writeValueAsString(person)))
                .andExpect(status().is2xxSuccessful());

        final ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(this.persons).save(argument.capture());
        final String login = argument.getValue().getLogin();
        assertEquals("NEW", login);
    }

    @Test
    public void update() throws Exception {
        final Person person = new Person(3, "PERSON", "123");
        final Person update = new Person(3, "UPDATE", "123");
        when(this.persons.findById(3)).thenReturn(Optional.of(person));
        this.mockMvc.perform(put("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.jo.writeValueAsString(update)))
                .andExpect(status().isOk());

        final ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(this.persons).save(argument.capture());
        final String login = argument.getValue().getLogin();
        assertEquals("UPDATE", login);
    }

    @Test
    public void whenDeleteIsOk() throws Exception {
        final Person delete = new Person(3, "PERSON", "123");
        when(this.persons.findById(3)).thenReturn(Optional.of(delete));
        this.mockMvc.perform(delete("/person/{id}", delete.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        final ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(this.persons).delete(argument.capture());
        final String login = argument.getValue().getLogin();
        assertNull(login);
    }
}
