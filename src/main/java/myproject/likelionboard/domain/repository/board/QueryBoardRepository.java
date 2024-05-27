package myproject.likelionboard.domain.repository.board;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myproject.likelionboard.domain.dto.SearchBoard;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.entity.QBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static myproject.likelionboard.domain.entity.QBoard.*;

@Repository
public class QueryBoardRepository{

    private final JPAQueryFactory query;

    @Autowired
    public QueryBoardRepository(EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    public List<Board> findAll(SearchBoard searchBoard){
        String boardAuthor = searchBoard.getName();
        String boardTitle = searchBoard.getTitle();

        return query.select(board)
                .from(board)
                .where(likeBoardAuthor(boardAuthor), likeBoardTitle(boardTitle))
                .orderBy(board.id.desc())
                .fetch();
    }

    private BooleanExpression likeBoardAuthor(String boardAuthor){
        if(StringUtils.hasText(boardAuthor)){
            return board.name.like("%" + boardAuthor + "%");
        }
        return null;
    }
    private BooleanExpression likeBoardTitle(String boardTitle){
        if(StringUtils.hasText(boardTitle)){
            return board.title.like("%" + boardTitle + "%");
        }
        return null;
    }
}
