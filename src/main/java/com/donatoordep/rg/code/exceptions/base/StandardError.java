package com.donatoordep.rg.code.exceptions.base;

import java.time.Instant;

public class StandardError {

    private Instant timestamp;
    private Integer status;
    private String error;
    private String path;

    public StandardError(Integer status, String error, String path) {
        this.status = status;
        this.error = error;
        this.path = path;
        this.timestamp = Instant.now();
    }

    public Integer getStatus() {
        return status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
