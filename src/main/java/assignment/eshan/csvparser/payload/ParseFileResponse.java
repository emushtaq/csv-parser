package assignment.eshan.csvparser.payload;

import java.util.Map;

public class ParseFileResponse {
    private Map medians;

    private String filename;

    public ParseFileResponse(Map medians, String filename) {
        this.medians = medians;
        this.filename = filename;
    }

    public Map getMedians() {
        return medians;
    }

    public void setMedians(Map medians) {
        this.medians = medians;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}