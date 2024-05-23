package myproject.likelionboard.domain.repository;

import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.entity.UpdateDto;
import myproject.likelionboard.domain.exception.BoardNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Repository
public class MemoryBoardRepository implements BoardRepository{

    private static Long sequence = 0L;
    private Map<Long, Board> store = new HashMap<>();
    @Override
    public Board save(Board board) {
        board.setId(++sequence);
        store.put(board.getId(), board);
        return board;
    }

    @Override
    public List<Board> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<Board> findById(Long id) {
        return findAll().stream()
                .filter(m -> m.getId().equals(id))
                .findFirst();
                //.orElseThrow(()-> new BoardNotFoundException("게시판 ID<" + id + ">는 존재 하지 않습니다."));
    }

    @Override
    public void update(Long id, UpdateDto param) {
        Board updateBoard = findById(id).get();
        setBoard(param, updateBoard);
    }

    private static void setBoard(UpdateDto source, Board target) {
        target.setName(source.getName());
        target.setTitle(source.getTitle());
        target.setContent(source.getContent());
        target.setCreateDate(source.getUpdateDate());
    }

    @Override
    public void delete(Long id) {
        Board deleteBoard = findById(id).get();
        store.remove(deleteBoard.getId());
    }
}
