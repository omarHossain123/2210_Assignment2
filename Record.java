public class Record {
    // Instance variables
    private Key theKey;
    private String data;

    // Constructor
    public Record(Key k, String theData) {
        // Initialize the key and data with the provided parameters
        this.theKey = k;
        this.data = theData;
    }

    // Getter for the key
    public Key getKey() {
        return theKey;
    }

    // Getter for the data
    public String getDataItem() {
        return data;
    }
}
