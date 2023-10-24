package org.backend.service;

import java.util.List;
import java.util.Optional;

public interface ServiceImpl<T> {

    T save(T t);

    Optional<T> findById(String id);

    List<T> findAll();

    void deleteById(String id);
}
