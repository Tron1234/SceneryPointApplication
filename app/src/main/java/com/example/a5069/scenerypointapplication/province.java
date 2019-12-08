package com.example.a5069.scenerypointapplication;

/**
 * Created by a5069 on 2017/6/19.
 */
public class province {
    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public province(String name) {
        this.name = name;
    }
}
