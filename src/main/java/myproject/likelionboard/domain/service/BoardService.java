package myproject.likelionboard.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.dto.SearchBoard;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.dto.UpdateDto;
import myproject.likelionboard.domain.repository.board.BoardRepository;
import myproject.likelionboard.domain.repository.board.QueryBoardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final QueryBoardRepository queryBoardRepository;

    public void create(Board board) {
        boardRepository.save(board);
        log.info("save board={}",board);
    }

    public List<Board> findAllBoards(SearchBoard searchBoard) {
        return queryBoardRepository.findAll(searchBoard);
    }

    public Board findById(Long id) {
        return boardRepository.findById(id).get();
    }
    public void updateBoard(Long id, UpdateDto param) {
        boardRepository.update(id, param);
    }

    public void deleteBoard(Long id) {
        boardRepository.delete(id);
    }
}
