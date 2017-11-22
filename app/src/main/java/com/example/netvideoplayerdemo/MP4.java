package com.example.netvideoplayerdemo;

import java.io.Serializable;

/**
 * @version $Rev$
 * @anthor Administrator
 * @dsc ${TOOD}
 * @updateAuthor $Author
 * @updateDsc ${TOOD}
 */
public class MP4 implements Serializable{

    private String name;

    private String path;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    private String kind;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
