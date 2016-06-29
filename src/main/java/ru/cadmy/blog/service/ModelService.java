package ru.cadmy.blog.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Cadmy on 22.03.2016.
 */
public abstract class ModelService {
    @PersistenceContext
    EntityManager em;
}
