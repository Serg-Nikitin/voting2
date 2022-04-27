package ru.javaops.topjava2.web;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.topjava2.model.BaseEntity;
import ru.javaops.topjava2.repository.BaseRepository;
import ru.javaops.topjava2.util.JsonUtil;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractEntityAdminControllerTest<T> extends AbstractControllerTest {

    public void get(String requestURL, int id , MatcherFactory.Matcher<T> matcher, T t) throws Exception {
        perform(MockMvcRequestBuilders.get(requestURL + id))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher.contentJson(t));
    }

    public void getAll(String requestURL, MatcherFactory.Matcher<T> matcher, List<T> t) throws Exception {
        perform(MockMvcRequestBuilders.get(requestURL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(matcher.contentJson(t));
    }

    public void delete(String requestURL, int id, BaseRepository<T> repository) throws Exception {
        perform(MockMvcRequestBuilders.delete(requestURL + id))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(id).isPresent());
    }

    public void create(T t, String requestURL, MatcherFactory.Matcher<T> matcher, BaseRepository<T> repository) throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(requestURL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(t)))
                .andExpect(status().isCreated());
        T created = matcher.readFromJson(action);
        int newId = ((BaseEntity) created).id();
        ((BaseEntity) t).setId(newId);
        matcher.assertMatch(created, t);
        matcher.assertMatch(repository.getById(newId), t);
    }

    public void update(BaseEntity t, String requestURL, int id) throws Exception {
        t.setId(null);
        perform(MockMvcRequestBuilders.put(requestURL + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(t)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}
