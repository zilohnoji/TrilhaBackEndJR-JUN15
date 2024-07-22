package com.donatoordep.rg.code.enums;

import com.donatoordep.rg.code.exceptions.ONBEnumTypeNotExistsException;

public enum TaskStatus {
    COMPLETED, PROGRESS, QUIT;

    public static TaskStatus valueOfOrThrowNotExists(String string) {
        try {
            return TaskStatus.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ONBEnumTypeNotExistsException(String.format("%s, %s, %s", TaskStatus.COMPLETED, TaskStatus.PROGRESS, TaskStatus.QUIT));
        }
    }
}