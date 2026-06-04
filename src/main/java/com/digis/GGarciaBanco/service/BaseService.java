package com.digis.ggarciabanco.service;

import com.digis.ggarciabanco.dto.Result;
import com.digis.ggarciabanco.exception.ErrorCode;
import java.util.List;
import java.util.function.Supplier;

public abstract class BaseService {

    protected <T> Result<T> ejecutar(Supplier<T> accion) {
        try {
            return Result.ok(accion.get());
        } catch (IllegalArgumentException e) {
            return Result.error(ErrorCode.INVALID_INPUT, e.getLocalizedMessage(), e);
        } catch (java.util.NoSuchElementException e) {
            return Result.error(ErrorCode.NOT_FOUND, e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            return Result.error(
                    ErrorCode.INTERNAL_ERROR,
                    "Ocurrió un error inesperado en el servidor.",
                    e
            );
        } catch (Exception e) {
            return Result.error(
                    ErrorCode.INTERNAL_ERROR,
                    "Ocurrió un error en el servidor.",
                    e
            );
        }
    }

    protected <T> Result<T> ejecutarLista(Supplier<List<T>> accion) {
        try {
            return Result.okList(accion.get());
        } catch (IllegalArgumentException e) {
            return Result.error(ErrorCode.INVALID_INPUT, e.getLocalizedMessage(), e);
        } catch (java.util.NoSuchElementException e) {
            return Result.error(ErrorCode.NOT_FOUND, e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            return Result.error(
                    ErrorCode.INTERNAL_ERROR,
                    "Ocurrió un error inesperado en el servidor.",
                    e
            );
        } catch (Exception e) {
            return Result.error(
                    ErrorCode.INTERNAL_ERROR,
                    "Ocurrió un error en el servidor.",
                    e
            );
        }
    }

    protected Result<Void> ejecutarVoid(Runnable accion) {
        try {
            accion.run();
            return Result.ok();
        } catch (IllegalArgumentException e) {
            return Result.error(ErrorCode.INVALID_INPUT, e.getLocalizedMessage(), e);
        } catch (java.util.NoSuchElementException e) {
            return Result.error(ErrorCode.NOT_FOUND, e.getLocalizedMessage(), e);
        } catch (RuntimeException e) {
            return Result.error(
                    ErrorCode.INTERNAL_ERROR,
                    "Ocurrió un error inesperado en el servidor.",
                    e
            );
        } catch (Exception e) {
            return Result.error(
                    ErrorCode.INTERNAL_ERROR,
                    "Ocurrió un error en el servidor.",
                    e
            );
        }
    }
}