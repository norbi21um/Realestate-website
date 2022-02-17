package com.realestate.springbootrealestate.config;

import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {


    private EntityManager entityManager;

    //EntityManager DI-ja
    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {

        HttpMethod[] theUnsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};

        // letiltja a HTTP methodokat a Property-re: PUT, POST and DELETE
        config.getExposureConfiguration()
                .forDomainType(Property.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

        // letiltja a HTTP methodokat a User-re: PUT, POST and DELETE
        config.getExposureConfiguration()
                .forDomainType(User.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));

        // belső segítőt hívja
        exposeIds(config);
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // megjeleníti az entity id-kat

        //Lista az összes entity classról az entity managgertől
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // létrehoz egy tömböt az entity típusokból
        List<Class> entityClasses = new ArrayList<>();

        // megkapja az entity típusokat az entityknek.
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // Megjeleníti az entity id-kat az entity tömb típusoknak
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}