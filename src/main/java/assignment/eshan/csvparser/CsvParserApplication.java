package assignment.eshan.csvparser;

import assignment.eshan.csvparser.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})public class CsvParserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvParserApplication.class, args);
	}

}
