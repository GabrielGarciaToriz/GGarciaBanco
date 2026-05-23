package com.digis.GGarciaBanco.service;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.exception.ErrorCode;
import java.util.List;
import java.util.function.Supplier;

public class BaseService {
    protected <T> Result<T> ejecutar(Supplier<T> accion) {
        try {
            return Result.ok(accion.get());
        } catch (IllegalArgumentException e) {
            return Result.error(ErrorCode.INVALID_INPUT, e.getLocalizedMessage(), e);
        } catch (java.util.NoSuchElementException e) {
            return Result.error(ErrorCode.NOT_FOUND, e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            return Result.error(ErrorCode.INTERNAL_ERROR, "Ocurrió un error inesperado en el servidor.", e);
        } catch (Exception e) {
            return Result.error(ErrorCode.INTERNAL_ERROR, "Ocurrió un error en el servidor.", e);
        }
    }

    protected <T> Result<T> ejecutarLista(Supplier<List<T>> accion) {
        try {
            return Result.okList(accion.get());
        } catch (IllegalArgumentException e) {
            return Result.error(ErrorCode.INVALID_INPUT, e.getMessage(), e);
        } catch (java.util.NoSuchElementException e) {
            return Result.error(ErrorCode.NOT_FOUND, e.getMessage(), e);
        } catch (RuntimeException e) {
            return Result.error(ErrorCode.INTERNAL_ERROR, "Ocurrió un error inesperado.", e);
        } catch (Exception e) {
            return Result.error(ErrorCode.INTERNAL_ERROR, "Ocurrió un error inesperado.", e);
        }
    }

    protected Result<Void> ejecutarVoid(Runnable action) {
        try {
            action.run();
            Result<Void> result = new Result<>();
            result.correct = true;
            return result;
        } catch (IllegalArgumentException e) {
            return Result.error(ErrorCode.INVALID_INPUT, e.getLocalizedMessage(), e);
        } catch (java.util.NoSuchElementException e) {
            return Result.error(ErrorCode.NOT_FOUND, e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            return Result.error(ErrorCode.INTERNAL_ERROR, "Ocurrió un error inesperado.", e);
        } catch (Exception e) {
            return Result.error(ErrorCode.INTERNAL_ERROR, e.getLocalizedMessage(), e);
        }
    }

}
