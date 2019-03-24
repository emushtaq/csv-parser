package assignment.eshan.csvparser;

import assignment.eshan.csvparser.service.FileParserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestConfiguration {
    @Bean
    @Primary
    public FileParserService parserService() {
        return Mockito.mock(FileParserService.class);
    }
}