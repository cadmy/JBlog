package ru.cadmy.blog.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class ModelService {
    @PersistenceContext
    EntityManager em;
}
