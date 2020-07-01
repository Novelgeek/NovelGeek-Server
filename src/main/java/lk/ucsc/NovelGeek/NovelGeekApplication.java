package lk.ucsc.NovelGeek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableAsync
public class NovelGeekApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(NovelGeekApplication.class, args);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
