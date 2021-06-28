package br.com.agoliveira.springbootcommysql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(scanBasePackages = "br.com.agoliveira.springbootcommysql")
@EntityScan(basePackageClasses = { SpringBootComMysqlApplication.class, Jsr310JpaConverters.class })
//@EntityScan(basePackages = "br.com.agoliveira.springbootcommysql.model")
//@EnableJpaRepositories(basePackageClasses = UserRepository.class)
public class SpringBootComMysqlApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootComMysqlApplication.class, args);
	}

}
