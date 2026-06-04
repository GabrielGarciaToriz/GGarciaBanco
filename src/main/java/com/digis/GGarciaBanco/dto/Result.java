package com.digis.ggarciabanco.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
@Setter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean correct;
    private String errorMessage;
    private String errorCode;
    private T object;
    private List<T> objects;

    @JsonIgnore
    private transient Exception ex;

    public static <T> Result<T> ok() {
        Result<T> result = new Result<>();
        result.correct = true;
        return result;
    }

    public static <T> Result<T> ok(T object) {
        Result<T> result = new Result<>();
        result.correct = true;
        result.object = object;
        return result;
    }

    public static <T> Result<T> okList(List<T> objects) {
        Result<T> result = new Result<>();
        result.correct = true;
        result.objects = objects != null ? objects : Collections.emptyList();
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

    @JsonIgnore
    public boolean hasObject() {
        return object != null;
    }

    @JsonIgnore
    public boolean hasObjects() {
        return objects != null && !objects.isEmpty();
    }
}