public class BSTDictionary implements BSTDictionaryADT{

    // BinarySearchTree instance to manage nodes in this dictionary
    private BinarySearchTree bst;

    // Constructor initializes the binary search tree
    public BSTDictionary() {
        this.bst = new BinarySearchTree();
    }

    // Returns the Record associated with key 'k' if it exists, or null otherwise
    public Record get(Key k) {
        BSTNode node = bst.get(bst.getRoot(), k);
        if (node != null) {
            return node.getRecord();
        } else {
            return null;
        }
    }

    // Inserts a new Record into the dictionary. Throws DictionaryException if
    // a Record with the same Key already exists
    public void put(Record r) throws DictionaryException {
        bst.insert(bst.getRoot(), r);
    }

    // Removes the Record associated with Key 'k'. Throws DictionaryException if
    // the Record is not found in the dictionary
    public void remove(Key k) throws DictionaryException {
        bst.remove(bst.getRoot(), k);
    }

    // Returns the Record with the smallest Key larger than 'k', or null if no successor exists
    public Record successor(Key k) {
        BSTNode node = bst.successor(bst.getRoot(), k);
        if (node != null) {
            return node.getRecord();
        } else {
            return null;
        }
    }

    // Returns the Record with the largest Key smaller than 'k', or null if no predecessor exists
    public Record predecessor(Key k) {
        BSTNode node = bst.predecessor(bst.getRoot(), k);
        if (node != null) {
            return node.getRecord();
        } else {
            return null;
        }
    }

    // Returns the Record with the smallest Key in the dictionary, or null if the dictionary is empty
    public Record smallest() {
        BSTNode node = bst.smallest(bst.getRoot());
        if (node != null) {
            return node.getRecord();
        } else {
            return null;
        }
    }

    // Returns the Record with the largest Key in the dictionary, or null if the dictionary is empty
    public Record largest() {
        BSTNode node = bst.largest(bst.getRoot());
        if (node != null) {
            return node.getRecord();
        } else {
            return null;
        }
    }
}
