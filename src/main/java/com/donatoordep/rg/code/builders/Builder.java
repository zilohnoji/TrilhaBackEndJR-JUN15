package com.donatoordep.rg.code.builders;

public interface Builder<T> {

    T build();

    void reset();
}