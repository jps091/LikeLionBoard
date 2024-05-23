package myproject.likelionboard.domain.service;

import lombok.RequiredArgsConstructor;
import myproject.likelionboard.domain.entity.Board;
import myproject.likelionboard.domain.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final BoardRepository boardRepository;
    public Optional<Board> login(Long id, String password){
        return boardRepository.findById(id)
                .filter(m -> m.getPassword().equals(password))
                .stream().findAny();
    }
}
