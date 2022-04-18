package cn.myst.web.esjob.entity;

import java.io.Serializable;

public class Foo implements Serializable {

    private String id;
    private String name;

    private static final long serialVersionUID = 7132977462144364904L;

    public Foo() {
    }

    public Foo(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
