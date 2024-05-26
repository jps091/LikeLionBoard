package myproject.likelionboard.domain.service;

import lombok.RequiredArgsConstructor;
import myproject.likelionboard.domain.entity.Member;
import myproject.likelionboard.domain.repository.member.SpringDataJpaMemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final SpringDataJpaMemberRepository memberRepository;
    public Optional<Member> login(String loginId, String password){
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .stream().findFirst();
    }
}
