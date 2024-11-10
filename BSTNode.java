public class BSTNode {
    // Instance variable to store the record in this node.
    private Record record;

    // Left and right child nodes for binary search tree structure.
    private BSTNode leftChild;
    private BSTNode rightChild;

    // Parent node reference to allow upward traversal.
    private BSTNode parent;

    // Constructor that initializes the node with a given Record.
    // Sets children and parent to null initially.
    public BSTNode(Record item) {
        this.record = item;
        this.leftChild = null;
        this.rightChild = null;
        this.parent = null;
    }

    // Returns the Record object stored in this node.
    public Record getRecord() {
        return record;
    }

    // Updates the Record object stored in this node to the specified value.
    public void setRecord(Record d) {
        this.record = d;
    }

    // Returns the left child node.
    public BSTNode getLeftChild() {
        return leftChild;
    }

    // Returns the right child node.
    public BSTNode getRightChild() {
        return rightChild;
    }

    // Returns the parent node.
    public BSTNode getParent() {
        return parent;
    }

    // Sets the left child node to the specified value.
    public void setLeftChild(BSTNode u) {
        this.leftChild = u;
    }

    // Sets the right child node to the specified value.
    public void setRightChild(BSTNode u) {
        this.rightChild = u;
    }

    // Sets the parent node to the specified value.
    public void setParent(BSTNode u) {
        this.parent = u;
    }

    // Returns true if this node is a leaf (i.e., both children are null), false otherwise.
    public boolean isLeaf() {
        return (leftChild == null && rightChild == null);
    }

}
