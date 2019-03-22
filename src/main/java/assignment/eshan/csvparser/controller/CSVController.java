package assignment.eshan.csvparser.controller;

import assignment.eshan.csvparser.payload.ParseFileResponse;
import assignment.eshan.csvparser.service.FileParserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
public class CSVController {

    private static final Logger logger = LoggerFactory.getLogger(CSVController.class);

    @Autowired
    private FileParserService fileParserService;

    @PostMapping("/parseFile")
    public ParseFileResponse parseFile(@RequestParam("file") MultipartFile file) {
        fileParserService.parseCSV(file);
        return new ParseFileResponse(new ArrayList(), file.getSize());
    }
}