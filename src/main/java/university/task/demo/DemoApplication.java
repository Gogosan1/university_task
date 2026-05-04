package university.task.demo;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DemoApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure()
			.directory(System.getProperty("user.dir"))
			.ignoreIfMissing()
			.load();

		dotenv.entries().forEach(entry -> {
			if (System.getProperty(entry.getKey()) == null) {
				System.setProperty(entry.getKey(), entry.getValue());
			}
		});

		SpringApplication.run(DemoApplication.class, args);
	}

}

// TODO: Написать README
// TODO: добавить валидацию полей и собственные классы исключений
// TODO: вероятно, group number должен быть уникальным, но так как в ТЗ не указано, 
// TODO: то я не стал это реализовывать, так как это может быть не нужно
