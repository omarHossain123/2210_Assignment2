public class Key {
    // Instance variables
    private String label;
    private int type;

    // Constructor
    public Key(String theLabel, int theType) {
        // Convert the label to lowercase before storing it
        this.label = theLabel.toLowerCase();
        this.type = theType;
    }

    // Getter for the label
    public String getLabel() {
        return label;
    }

    // Getter for the type
    public int getType() {
        return type;
    }

    // Compares this Key object with another Key object.
    public int compareTo(Key k) {
        // First compare the labels lexicographically
        int labelComparison = this.label.compareTo(k.getLabel());
        
        if (labelComparison < 0) {
            return -1;
        } else if (labelComparison > 0) {
            return 1;
        }
        
        // If labels are the same, compare types
        return Integer.compare(this.type, k.getType());
    }
}
