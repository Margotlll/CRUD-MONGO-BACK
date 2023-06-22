package com.tutorial.crudmongoback.global.Entity;

import org.springframework.data.annotation.Id;

public abstract class EntityId {
    @Id
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
