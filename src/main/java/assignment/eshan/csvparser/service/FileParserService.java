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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

/**
 * Service class for file parsing
 */
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

    /** The main service that parses a CSV file and returns a map of median values grouped by the 'label' field in the CSV.
     * @param file : The input CSV File
     * @return medians: A hashmap containing median csv rows grouped by the 'label' field in the CSV
     * @throws IOException
     */
    public Map parseCSV(MultipartFile file) throws IOException{
        BufferedReader br;
        HashMap medians = new HashMap();
        InputStream is = file.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));

        CSVParser csvParser = new CSVParser(br, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withDelimiter(DELIMITER) // The sample CSV files used this delimiter
                .withTrim());

        Map<String, List<CSVRecord>> groupedRows = StreamSupport.stream(csvParser.spliterator(), false)
                .collect(Collectors.groupingBy(x->x.get(LABEL_COLUMN_NAME)));

        if (groupedRows.size()==0){
            return medians;
        }

        for (Map.Entry<String, List<CSVRecord>> entry : groupedRows.entrySet()) {
            String label = entry.getKey();
            List<CSVRecord> values = entry.getValue();
            medians.put(label, getMedian(values));
        }

        return medians;
    }

    /** A method that computes the median value for a list of CSV Records
     * @param values : A list of CSVRecords
     * @return median values for the given CSVRecords
     */
    private Map getMedian(List<CSVRecord> values) {
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

        //If n is odd then Median (M) = value of ((n + 1)/2)th item term.
        //If n is even then Median (M) = value of [((n)/2)th item term + ((n)/2 + 1)th item term ]/2
        return computeMedian(values);
    }

    private Map computeMedian(List<CSVRecord> values) {
        if (values.size()>1) {
            int middle = values.size() / 2;
            if (middle % 2 != 0) {
                Map median = values.get(middle).toMap();
                median.remove("label");
                return median;
            } else {
                CSVRecord row1 = values.get(middle - 1);
                CSVRecord row2 = values.get(middle);
                Map averageMap = row1.toMap();
                System.out.println(row1);
                System.out.println(averageMap);
                averageMap.forEach((k, v) -> {
                    try {
                        averageMap.put(k, Double.valueOf(row1.get(String.valueOf(k)) + row2.get(String.valueOf(k))) / 2);
                    } catch (NumberFormatException e) {
                        return; // Use string value of the first row
                    }
                });

                averageMap.remove("label");
                return averageMap;
            }
        } else {
            Map median = values.get(0).toMap();
            median.remove("label");
            return median;
        }
    }

}