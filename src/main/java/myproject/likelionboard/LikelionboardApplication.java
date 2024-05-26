package myproject.likelionboard;

import myproject.likelionboard.domain.repository.board.BoardRepository;
import myproject.likelionboard.domain.repository.member.SpringDataJpaMemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

//@Import(SpringDataJpaMemberRepository.class)
@SpringBootApplication
public class LikelionboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikelionboardApplication.class, args);
	}

	@Bean
	public TestDataInit testDataInit(BoardRepository boardRepository, SpringDataJpaMemberRepository memberRepository){
		return new TestDataInit(boardRepository, memberRepository);
	}
}
