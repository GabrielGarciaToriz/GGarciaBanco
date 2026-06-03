package com.digis.GGarciaBanco.controller;

import com.digis.GGarciaBanco.dto.Result;
import com.digis.GGarciaBanco.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected ResponseEntity<Result<?>> responder(Result<?> result) {
        return new ResponseEntity<>(
                result,
                result.isCorrect() ? HttpStatus.OK : resolverStatus(result)
        );
    }

    protected ResponseEntity<Result<?>> responderCreado(Result<?> result) {
        return new ResponseEntity<>(
                result,
                result.isCorrect() ? HttpStatus.CREATED : resolverStatus(result)
        );
    }

    protected ResponseEntity<Result<?>> responderEliminado(Result<?> result) {
        if (result.isCorrect()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(result, resolverStatus(result));
    }

    private HttpStatus resolverStatus(Result<?> result) {
        if (result.getErrorCode() == null) {
            return HttpStatus.BAD_REQUEST;
        }

        return switch (result.getErrorCode()) {
            case ErrorCode.NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ErrorCode.DUPLICATE -> HttpStatus.CONFLICT;
            case ErrorCode.INVALID_INPUT -> HttpStatus.UNPROCESSABLE_ENTITY;
            case ErrorCode.INTERNAL_ERROR -> HttpStatus.INTERNAL_SERVER_ERROR;
            case ErrorCode.ACCOUNT_NOT_ACTIVATED -> HttpStatus.FORBIDDEN;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}