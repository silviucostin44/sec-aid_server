package com.example.secaidserver.model.file;

public class ResponseFile {
    private final String id;
    private long size;
    private String name;
    private final String url;
    private  String type;

    public ResponseFile(String id, String name, String url, String type, long size) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.type = type;
        this.size = size;
    }

    public ResponseFile(String id, String name, String url, String type) {
        this(id, name, url, type, 0L);
    }

    public ResponseFile(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }
}
