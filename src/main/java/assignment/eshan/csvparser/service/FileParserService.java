package assignment.eshan.csvparser.service;

import assignment.eshan.csvparser.exception.FileParsingException;
import assignment.eshan.csvparser.property.FileStorageProperties;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class FileParserService {

    private final Path fileStorageLocation;
    private char DELIMITER = ';';
    private String LABEL_COLUMN_NAME = "label";

    @Autowired
    public FileParserService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileParsingException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public void parseCSV(MultipartFile file) {
        BufferedReader br;
        try {
            InputStream is = file.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));

            CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withDelimiter(DELIMITER) // The sample CSV files used this delimiter
                    .withTrim());

            System.out.println("CSV COLUMNS : " + csvParser.getHeaderMap());

            Map<String, List<CSVRecord>> groupedRows = StreamSupport.stream(csvParser.spliterator(), false)
                    .collect(Collectors.groupingBy(x->x.get(LABEL_COLUMN_NAME)));

            for (Map.Entry<String, List<CSVRecord>> entry : groupedRows.entrySet()) {
                String label = entry.getKey();
                List<CSVRecord> values = entry.getValue();
                System.out.println("---------------");
                System.out.println("Label : " + label);
                System.out.println("---------------\n\n");
                CSVRecord median = getMedian(values);
                System.out.println("-------- MEDIAN : " + median);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private CSVRecord getMedian(List<CSVRecord> values) {
        values.forEach( (value) -> System.out.println(value) );
        Collections.sort(values, (row1, row2) -> {
            CompareToBuilder rowComparer = new CompareToBuilder();
            for (int i=0; i<row1.size(); i++) {
                try {
                    rowComparer.append(Double.valueOf(row1.get(i)), Double.valueOf(row2.get(i)));
                } catch (NumberFormatException e) {
                    rowComparer.append(row1.get(i), row2.get(i));
                }
            }
            return rowComparer.toComparison();
        });

        System.out.println("---------------");
        values.forEach( (value) -> System.out.println(value) );
        System.out.println("---------------");
        System.out.println("---------------");
        //If n is odd then Median (M) = value of ((n + 1)/2)th item term.
        //If n is even then Median (M) = value of [((n)/2)th item term + ((n)/2 + 1)th item term ]/2
        int middle = values.size()/2;
        if(middle % 2 != 0){
            return values.get(middle);
        } else {
            return averageRows(values.get(middle-1), values.get(middle));
        }
    }

    private CSVRecord averageRows(CSVRecord row1, CSVRecord row2) {
        for (int i=0; i<row1.size(); i++) {

        }

        // TODO Handle string averaging
        return row1;
    }

}