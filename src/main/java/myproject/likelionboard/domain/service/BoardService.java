package myproject.likelionboard.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.entity.UpdateDto;
import myproject.likelionboard.domain.exception.BoardNotFoundException;
import myproject.likelionboard.domain.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void create(Board board) {
        boardRepository.save(board);
        log.info("save board={}",board);
    }

    public List<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    public Board findById(Long id) {
        if(boardRepository.findById(id).isPresent()){
            return boardRepository.findById(id).get();
        }else{
            throw new BoardNotFoundException(id + " : 는 존재 하지않는 게시판 입니다.");
        }
    }
    public void updateBoard(Long id, UpdateDto param) {
        if(boardRepository.findById(id).isPresent()){
            boardRepository.update(id, param);
        }else{
            throw new BoardNotFoundException(id + " : 는 존재 하지않는 게시판 입니다.");
        }
    }

    public void deleteBoard(Long id) {
        if(boardRepository.findById(id).isPresent()){
            boardRepository.delete(id);
        }else{
            throw new BoardNotFoundException(id + " : 는 존재 하지않는 게시판 입니다.");
        }
    }
}
