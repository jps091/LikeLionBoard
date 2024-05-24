package myproject.likelionboard.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.exception.BoardNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalHandlerException {

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public void handleBoardNotFoundException(EmptyResultDataAccessException e, HttpServletResponse response) throws IOException {
        log.info("trace db ex", e);
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "존재하지 정보 요청입니다.");
    }
    @ExceptionHandler(DataAccessException.class)
    public void handleDataAccessException(DataAccessException e, HttpServletResponse response) throws IOException {
        log.error("trace db ex", e);
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
    }
}
