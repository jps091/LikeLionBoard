package myproject.likelionboard.web.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.net.ConnectException;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler(DataAccessException.class)
    public void handleDataAccessException(DataAccessException e, HttpServletResponse response) throws IOException {
        log.error("trace DataAccessException ex", e);
        /*첫 번째 코드는 NonTransientDataAccessException의 직접 원인만 확인하기 때문에, 처음에 CannotGetJdbcConnectionException발생
        만약 ConnectException이 직접 원인이 아니라면 SC_BAD_REQUEST (400) 응답을 보냅니다.
        if (e.getCause() instanceof ConnectException) {
            handleConnectException((ConnectException) e.getCause(), response);
            return; // ConnectException 처리 후 흐름을 종료
        }
        중요
        ConnectException이 원인인지 확인
        예외 원인 체인의 모든 원인을 확인하기 때문에, ConnectException이 예외 원인 체인 어디에 있든지 이를 찾아 처리합니다.
        따라서 ConnectException이 존재하면 올바르게 SC_INTERNAL_SERVER_ERROR (500) 응답을 보낼 수 있습니다.*/
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof ConnectException) {
                handleConnectException((ConnectException) cause, response);
                //log.error("trace ConnectException ex", cause);
                //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "잠시만 기다려주세요. 서버 복구 중입니다.");
                return;
            }
            if(cause instanceof BadSqlGrammarException){
                log.error("trace BadSqlGrammarException ex", cause);
                handleBadSqlGrammarException((BadSqlGrammarException) cause, response);
                return;
                //response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "잠시만 기다려주세요. 서버 복구 중입니다.");
            }
            cause = cause.getCause();
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
    }

    @ExceptionHandler(ConnectException.class)
    public void handleConnectException(ConnectException e, HttpServletResponse response) throws IOException {
        log.error("trace ConnectException ex", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "잠시만 기다려주세요. 서버 복구 중입니다.");
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    public void handleBadSqlGrammarException(BadSqlGrammarException e, HttpServletResponse response) throws IOException {
        log.error("trace BadSqlGrammarException ex", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "잠시만 기다려주세요. 서버 복구 중입니다.");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void handleBoardNotFoundException(EmptyResultDataAccessException e, HttpServletResponse response) throws IOException {
        log.info("trace EmptyResultDataAccessException ex", e);
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "존재하지 정보 요청입니다.");
    }

/*    @ExceptionHandler(TransientDataAccessException.class)
    public void handleTransientDataAccessException(TransientDataAccessException e, HttpServletResponse response) throws IOException {
        log.error("trace TransientDataAccessException ex", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "일시적인 서버 오류입니다. 잠시 후 다시 시도해주세요.");
    }

    @ExceptionHandler(NonTransientDataAccessException.class)
    public void handleNonTransientDataAccessException(NonTransientDataAccessException e, HttpServletResponse response) throws IOException {
        log.error("trace NonTransientDataAccessException ex", e);
        //첫 번째 코드는 NonTransientDataAccessException의 직접 원인만 확인하기 때문에, 처음에 CannotGetJdbcConnectionException발생
        // 만약 ConnectException이 직접 원인이 아니라면 SC_BAD_REQUEST (400) 응답을 보냅니다.
*//*        if (e.getCause() instanceof ConnectException) {
            handleConnectException((ConnectException) e.getCause(), response);
            return; // ConnectException 처리 후 흐름을 종료
        } 중요 *//*

        // ConnectException이 원인인지 확인
        *//*예외 원인 체인의 모든 원인을 확인하기 때문에, ConnectException이 예외 원인 체인 어디에 있든지 이를 찾아 처리합니다.
        따라서 ConnectException이 존재하면 올바르게 SC_INTERNAL_SERVER_ERROR (500) 응답을 보낼 수 있습니다.*//*
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause instanceof ConnectException) {
                handleConnectException((ConnectException) cause, response);
                return;
            }
            cause = cause.getCause();
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
    }*/
}
