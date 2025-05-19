package com.example.springboilerplate.component;

import com.example.springboilerplate.annotation.HasOneAttached;
import com.example.springboilerplate.annotation.HasManyAttached;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Entity;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ActiveStorageRegistrar {

    private final Map<Class<?>, Map<String, AttachmentType>> registeredEntities = new HashMap<>();

    public enum AttachmentType {
        ONE,
        MANY
    }

    public Map<Class<?>, Map<String, AttachmentType>> getRegisteredEntities() {
        return registeredEntities;
    }

    @PostConstruct
    public void register() {
        Reflections reflections = new Reflections("com.example"); // Scan packages here

        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        for (Class<?> entityClass : entities) {
            Map<String, AttachmentType> attachments = new HashMap<>();

            for (Field field : entityClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(HasOneAttached.class)) {
                    attachments.put(field.getName(), AttachmentType.ONE);
                    log.info("Registered @HasOneAttached: {}.{}", entityClass.getSimpleName(), field.getName());
                }
                if (field.isAnnotationPresent(HasManyAttached.class)) {
                    attachments.put(field.getName(), AttachmentType.MANY);
                    log.info("Registered @HasManyAttached: {}.{}", entityClass.getSimpleName(), field.getName());
                }
            }

            if (!attachments.isEmpty()) {
                registeredEntities.put(entityClass, attachments);
            }
        }
    }
}
