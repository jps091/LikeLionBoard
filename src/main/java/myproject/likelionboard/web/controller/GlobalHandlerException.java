package myproject.likelionboard.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void handleBoardNotFoundException(EmptyResultDataAccessException e, HttpServletResponse response) throws IOException {
        log.info("trace EmptyResultDataAccessException ex", e);
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "존재하지 정보 요청입니다.");
    }
    @ExceptionHandler(ConnectException.class)
    public void handleConnectException(ConnectException e, HttpServletResponse response) throws IOException {
        log.error("trace ConnectException ex", e);
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "잠시만 기다려주세요. 서버 복구 중입니다.");
    }
}
