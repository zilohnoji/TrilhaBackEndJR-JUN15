package com.donatoordep.rg.code.configurations.filters.jwt.exceptions.base;

import java.time.Instant;

public interface JWTExceptionSpecification {

    Integer getStatus();

    String getError();

    String getPath();

    default Instant getTimestamp() {
        return Instant.now();
    }
}
