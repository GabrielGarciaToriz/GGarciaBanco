package com.digis.GGarciaBanco.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Result<T> {

    public boolean correct;
    public String errorMessage;
    public String errorCode;
    public T object;
    public List<T> objects;
    @JsonIgnore
    public Exception ex;

    public static <T> Result<T> ok(T object) {
        Result<T> result = new Result<>();
        result.correct = true;
        result.object = object;
        return result;
    }

    public static <T> Result<T> okList(List<T> objects) {
        Result<T> result = new Result<>();
        result.correct = true;
        result.objects = objects;
        return result;
    }

    public static <T> Result<T> error(String errorCode, String errorMessage) {
        Result<T> result = new Result<>();
        result.correct = false;
        result.errorCode = errorCode;
        result.errorMessage = errorMessage;
        return result;
    }

    public static <T> Result<T> error(String errorCode, String errorMessage, Exception ex) {
        Result<T> result = Result.error(errorCode, errorMessage);
        result.ex = ex;
        return result;
    }

}
