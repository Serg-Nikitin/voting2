package ru.javaops.topjava2.web;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.topjava2.model.BaseEntity;
import ru.javaops.topjava2.repository.BaseRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static ru.javaops.topjava2.util.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.topjava2.util.validation.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractEntityBaseController<T> {


    public ResponseEntity<T> get(@PathVariable int id, BaseRepository<T> repository) {
        log.info("get {}", id);
        return ResponseEntity.of(repository.findById(id));
    }

    public List<T> getAll(BaseRepository<T> repository) {
        log.info("getAll");
        return repository.findAll();
    }

    public void delete(@PathVariable int id, BaseRepository<T> repository) {
        repository.delete(id);
    }

    public ResponseEntity<T> create(@Valid @RequestBody T t, String REST_URL, BaseRepository<T> repository) {
        log.info("create restaurant {}", t);
        checkNew((BaseEntity) t);
        T created = repository.save(t);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(((BaseEntity) created).getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    public void update(@Valid @RequestBody T t, @PathVariable int id, BaseRepository<T> repository) {
        log.info("update {} with id={}", t, id);
        assureIdConsistent((BaseEntity) t, id);
        repository.save(t);
    }

}
