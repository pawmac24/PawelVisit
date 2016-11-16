package com.pm.company.dto.primarykey;

/**
 * Created by pmackiewicz on 2015-10-27.
 */
public abstract class PrimaryKeyDTO<T extends Number> {

    private T id;

    public PrimaryKeyDTO(T id) {
        this.id = id;
    }

    public T getId() {
        return id;
    }
}
