package com.agile.spirit.openapi.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {

    private static EntityManagerFactory emf;

    public static final void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("OpenApiPu");
    }

    public static final void closeEntityManagerFactory() {
        emf.close();
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

}
