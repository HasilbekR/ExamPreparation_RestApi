package com.example.exampreparation_restapi.service;

import java.util.UUID;

public interface BaseService<T, E>{
    T save(E e);
    T update(E e, UUID id);
    void deleteById(UUID id);
    T getById(UUID id);
}
