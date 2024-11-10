public class BinarySearchTree {
    // Root node of the binary search tree.
    private BSTNode root;

    // Constructor to initialize an empty binary search tree.
    public BinarySearchTree() {
        this.root = null;
    }

    // Returns the root node of this binary search tree.
    public BSTNode getRoot() {
        return root;
    }

    // Searches for a node with the given key in the subtree rooted at r.
    // Returns the node if found, or null if the key is not in the subtree.
    public BSTNode get(BSTNode r, Key k) {
        if (r == null) {
            return null;
        }
        
        // Compare the given key with the key in the current node.
        int comparison = k.compareTo(r.getRecord().getKey());
        
        // If the key matches, return this node.
        if (comparison == 0) {
            return r;
        }
        // If the key is smaller, search the left subtree.
        else if (comparison < 0) {
            return get(r.getLeftChild(), k);
        }
        // If the key is larger, search the right subtree.
        else {
            return get(r.getRightChild(), k);
        }
    }

    // Inserts a record into the binary search tree at the subtree rooted at r.
    // Throws DictionaryException if a record with the same key already exists.
    public void insert(BSTNode r, Record d) throws DictionaryException {
        // If the tree is empty, make the new node the root.
        if (root == null) {
            root = new BSTNode(d);
            return;
        }

        // Compare the key of the new record with the key of the current node.
        int comparison = d.getKey().compareTo(r.getRecord().getKey());

        if (comparison == 0) {
            // Key already exists in the tree; throw an exception.
            throw new DictionaryException("Key already exists in the tree.");
        }
        else if (comparison < 0) {
            // If the key is smaller, go to the left subtree.
            if (r.getLeftChild() == null) {
                // If there is no left child, insert the new node here.
                BSTNode newNode = new BSTNode(d);
                r.setLeftChild(newNode);
                newNode.setParent(r);
            } else {
                // Recursively insert in the left subtree.
                insert(r.getLeftChild(), d);
            }
        } else {
            // If the key is larger, go to the right subtree.
            if (r.getRightChild() == null) {
                // If there is no right child, insert the new node here.
                BSTNode newNode = new BSTNode(d);
                r.setRightChild(newNode);
                newNode.setParent(r);
            } else {
                // Recursively insert in the right subtree.
                insert(r.getRightChild(), d);
            }
        }
    }

    // Removes the node with the specified key from the tree rooted at r.
    // Throws DictionaryException if the key is not found.
    public void remove(BSTNode r, Key k) throws DictionaryException {
        // Find the node to remove.
        BSTNode nodeToRemove = get(r, k);
        if (nodeToRemove == null) {
            // Key not found in the tree; throw an exception.
            throw new DictionaryException("Key not found in the tree.");
        }
        
        // Perform the node removal.
        removeNode(nodeToRemove);
    }

    // Helper method to remove a specific node from the tree.
    private void removeNode(BSTNode node) {
        if (node.isLeaf()) {
            // Case 1: Node is a leaf (has no children).
            replaceNodeInParent(node, null);
        } else if (node.getLeftChild() != null && node.getRightChild() != null) {
            // Case 2: Node has two children.
            BSTNode successor = smallest(node.getRightChild());
            node.setRecord(successor.getRecord());
            removeNode(successor);
        } else {
            // Case 3: Node has one child.
            BSTNode child;
            if (node.getLeftChild() != null) {
                child = node.getLeftChild();
            } else {
                child = node.getRightChild();
            }
            replaceNodeInParent(node, child);
        }
        
    }

    // Replaces the specified node with a new node in the tree, updating parent-child relationships as needed.
    private void replaceNodeInParent(BSTNode node, BSTNode newNode) {
        
        // Check if the node has a parent.
        if (node.getParent() != null) {
            
            // Determine if the node to be replaced is the left or right child of its parent,
            // and set the parent's child reference to the new node.
            if (node == node.getParent().getLeftChild()) {
                node.getParent().setLeftChild(newNode);
            } else {
                node.getParent().setRightChild(newNode);
            }
        } else {
            // If there is no parent, it means the node to be replaced is the root of the tree.
            // Update the root reference to point to the new node.
            root = newNode;
        }
        
        // If the new node is not null, set its parent reference to the parent of the node being replaced.
        if (newNode != null) {
            newNode.setParent(node.getParent());
        }
    }

    // Finds the successor node of the specified key in the tree rooted at r.
    public BSTNode successor(BSTNode r, Key k) {
        BSTNode node = get(r, k);
        if (node == null) return null;
        
        // If there is a right child, the successor is the smallest node in the right subtree.
        if (node.getRightChild() != null) {
            return smallest(node.getRightChild());
        }
        
        // Traverse up to find the successor.
        BSTNode parent = node.getParent();
        while (parent != null && node == parent.getRightChild()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    // Finds the predecessor node of the specified key in the tree rooted at r.
    public BSTNode predecessor(BSTNode r, Key k) {
        BSTNode node = get(r, k);
        if (node == null) return null;
        
        // If there is a left child, the predecessor is the largest node in the left subtree.
        if (node.getLeftChild() != null) {
            return largest(node.getLeftChild());
        }
        
        // Traverse up to find the predecessor.
        BSTNode parent = node.getParent();
        while (parent != null && node == parent.getLeftChild()) {
            node = parent;
            parent = parent.getParent();
        }
        return parent;
    }

    // Finds the smallest node in the subtree rooted at r.
    public BSTNode smallest(BSTNode r) {
        if (r == null) return null;
        
        // Traverse to the leftmost node.
        while (r.getLeftChild() != null) {
            r = r.getLeftChild();
        }
        return r;
    }

    // Finds the largest node in the subtree rooted at r.
    public BSTNode largest(BSTNode r) {
        if (r == null) return null;
        
        // Traverse to the rightmost node.
        while (r.getRightChild() != null) {
            r = r.getRightChild();
        }
        return r;
    }
}
