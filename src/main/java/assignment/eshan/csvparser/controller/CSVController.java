package assignment.eshan.csvparser.controller;

import assignment.eshan.csvparser.payload.ParseFileResponse;
import assignment.eshan.csvparser.service.FileParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class CSVController {

    private static final Logger logger = LoggerFactory.getLogger(CSVController.class);

    @Autowired
    private FileParserService fileParserService;

    @PostMapping("/parseFile")
    public ParseFileResponse parseFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map medians = fileParserService.parseCSV(file);
        if(medians.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return new ParseFileResponse(medians, file.getOriginalFilename());
    }
}