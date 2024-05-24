package myproject.likelionboard.domain.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.entity.UpdateDto;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * H2 데이터베이스 연결
 */
@Slf4j
@Repository
public class JDBCBoardRepository implements BoardRepository{

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JDBCBoardRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("lion")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Board save(Board board) {
        board.setCreateDate(LocalDateTime.now());

        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(board);
        Number key = jdbcInsert.executeAndReturnKey(param);
        board.setId(key.longValue());
        return board;
    }

    @Override
    public List<Board> findAll() {
        try{
            String sql = "select * from lion order by 1 desc";
            return template.query(sql, boardRowMapper());
        }catch (DataAccessException e) {
            throw new DataAccessException("Error accessing data for id: ", e) {};
        }
    }

    @Override
    public Optional<Board> findById(Long id) {
        String sql = "select * from lion where id = :id";
        Map<String, Object> param = Map.of("id", id);
        Board board = template.queryForObject(sql, param, boardRowMapper());
        return Optional.of(board);
    }

    @Override
    public void update(Long id, UpdateDto param) {
        String sql = "update lion set title = :title, content = :content, name = :name " +
                        "where id = :id";
        MapSqlParameterSource updateParam = new MapSqlParameterSource()
                .addValue("title", param.getTitle())
                .addValue("content", param.getContent())
                .addValue("name", param.getName())
                .addValue("id", id);
        template.update(sql, updateParam);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from lion where id = :id";
        Map<String, Object> param = Map.of("id", id);
        template.update(sql, param);
    }
    private RowMapper<Board> boardRowMapper(){
        return BeanPropertyRowMapper.newInstance(Board.class);
    }

}
