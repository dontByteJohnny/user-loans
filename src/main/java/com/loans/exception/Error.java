package com.loans.exception;

import com.google.gson.Gson;
import java.io.Serializable;
import lombok.Data;

@Data
public class Error implements Serializable {
    private Integer code;
    private String title;

    public Error(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}