package com.demo4.services;

import java.util.List;

public interface IMoneyService<E> {
    List<E> findAll();

    void save(E e);

    void delete(long id);


}
