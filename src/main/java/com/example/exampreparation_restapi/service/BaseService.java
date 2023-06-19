package com.example.exampreparation_restapi.service;

import java.util.UUID;

public interface BaseService<T, E>{
    T save(E e);
    void deleteById(UUID id);
    T getById(UUID id);
}
