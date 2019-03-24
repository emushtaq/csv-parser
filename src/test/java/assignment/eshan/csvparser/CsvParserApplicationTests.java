package assignment.eshan.csvparser;

import assignment.eshan.csvparser.controller.CSVController;
import assignment.eshan.csvparser.service.FileParserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CSVController.class)
public class CsvParserApplicationTests {

    @MockBean
    FileParserService fileParserService;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void showErrorInvalidFile() {

        try {
            String mockFile = "";
            MediaType mediaType = new MediaType("multipart", "form-data");
            MockMultipartFile csvFile = new MockMultipartFile(
                    "test.csv",
                    "",
                    "text/csv",
                    mockFile.getBytes());

            mockMvc.perform(MockMvcRequestBuilders.multipart("/parseFile")
                    .file("file", csvFile.getBytes())
                    .contentType(mediaType)
                    .characterEncoding("UTF-8"))
                    .andDo(print())
                    .andExpect(status().is4xxClientError());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
