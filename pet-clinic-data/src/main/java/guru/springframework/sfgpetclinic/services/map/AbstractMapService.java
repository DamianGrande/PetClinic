package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {
    protected Map<Long, T> map = new HashMap<Long, T>();

    Set<T> findAll() {
        return new HashSet<T>(this.map.values());
    }

    T findById(ID id) {
        return this.map.get(id);
    }

    T save(T object) {
        if (object != null) {
            if (object.getId() == null)
                object.setId(this.getNextId());
            this.map.put(object.getId(), object);
        } else
            throw new RuntimeException("Object cannot be null.");
        return object;
    }

    void deleteById(ID id) {
        this.map.remove(id);
    }

    void delete(T object) {
        this.map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }

    private Long getNextId() {
        return Collections.max(this.map.keySet()) + 1;
    }
}
