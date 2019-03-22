package assignment.eshan.csvparser.payload;

import java.util.ArrayList;

public class ParseFileResponse {
    private ArrayList labelMedians;
    private long size;

    public ParseFileResponse(ArrayList labelMedians, long size) {
        this.labelMedians = labelMedians;
        this.size = size;
    }

    public ArrayList getLabelMedians() {
        return labelMedians;
    }

    public void setLabelMedians(ArrayList labelMedians) {
        this.labelMedians = labelMedians;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}