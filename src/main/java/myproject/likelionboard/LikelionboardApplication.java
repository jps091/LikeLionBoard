package myproject.likelionboard;

import myproject.likelionboard.domain.repository.BoardRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class LikelionboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(LikelionboardApplication.class, args);
	}

	@Bean
	public TestDataInit testDataInit(BoardRepository boardRepository){
		return new TestDataInit(boardRepository);
	}
}
