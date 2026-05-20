package com.digis.GGarciaBanco.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Result {

    public boolean correct;
    public String errorMessage;
    public String errorCode;
    public Object object;
    public List<?> objects;
    @JsonIgnore
    public Exception ex;

    public static Result ok(Object object) {
        Result result = new Result();
        result.correct = true;
        result.object = object;
        return result;
    }

    public static Result okList(List<?> objects) {
        Result result = new Result();
        result.correct = true;
        result.objects = objects;
        return result;
    }

    public static Result error(String errorCode, String errorMessage) {
        Result result = new Result();
        result.correct = false;
        result.errorCode = errorCode;
        result.errorMessage = errorMessage;
        return result;
    }

    public static Result error(String errorCode, String errorMessage, Exception ex) {
        Result result = Result.error(errorCode, errorMessage);
        result.ex = ex;
        return result;
    }

}
