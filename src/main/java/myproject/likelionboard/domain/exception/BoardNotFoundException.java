package myproject.likelionboard.domain.exception;

public class BoardNotFoundException extends RuntimeException{
    public BoardNotFoundException(String s) {
        super(s);
    }
}
