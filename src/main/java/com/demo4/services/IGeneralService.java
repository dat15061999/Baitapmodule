package com.demo4.services;

import java.util.List;

public interface IGeneralService<E, T> {
    List<E> findAll();

    E findById(T id);

    E save(E e);

    void delete(E e);

    void deleteById(T t);
}
