package com.sima;

/**
 * This class represents the data structure that I chose for keeping the k lowest elements from the array.
 * The data structure consists of:
 * 1. Red black tree (chosen to fulfill assignment's requirement of inserting element in Θ(logk) time complexity).
 * 2. The node with the max key (by value) in the tree.
 * 3. A variable for the number of elements in the tree.
 *
 * @author Sima Margulis Davidi
 * @version 30-07-2017
 */

public class RBTreePlus {

    // Instance variables - root of the tree, nilT node (the sentinel), pointer to the node with the max key and
    // number of elements in the tree
    private RBTreePlusNode _root, _nilT;
    private RBTreePlusNode _maxNode;
    private int _numOfElements;

    /**
     * Constructor - initializes the nilT node (assigns a key of the minimum integer value, so it won't affect the max
     * node in the tree, and assigns a black color to it as per sentinel definition in red-black trees), sets the
     * pointers of root and max node to nilT, and sets the number of elements to 0.
     */
    public RBTreePlus() {
        _nilT = new RBTreePlusNode(Integer.MIN_VALUE, RBTreePlusNode.Color.BLACK);
        _root = _nilT;
        _maxNode = _nilT;
        _numOfElements = 0;
    }

    // Getters for the num. of elements and max. node
    public int getNumOfElements() {
        return _numOfElements;
    }

    public RBTreePlusNode getMaxNode() {
        return _maxNode;
    }

    // Setter for the max node
    public void setMaxNode(RBTreePlusNode max) {
        _maxNode = max;
    }
    
    /**
     * Checks whether the input node is the sentinel - nilT.
     * Since nilT is the only node which key is the minimum integer value, the method only checks the key of
     * the input node, which results in a time complexity of Θ(1).
     *
     * @param node the input node
     * @return true if the node is nilT
     */
    private boolean isNilT(RBTreePlusNode node){
        if (node.getKey() == Integer.MIN_VALUE){
            return true;
        }
        return false;
    }

    /**
     * Performs a left rotation of the tree on node x.
     * Time complexity: Θ(1) (as explained in the book, page 234)
     *
     * @param x the node on which the rotation is executed
     */
    private void leftRotate(RBTreePlusNode x){
        RBTreePlusNode y = x.getRightSon(); // set y to x's right son
        if (!isNilT(y)){ // the rotation can only be executed if y isn't nilT (as explained in the book, page 234)
            RBTreePlusNode yLeftSon = y.getLeftSon();
            x.setRightSon(yLeftSon); // turn y's left subtree into x's right subtree
            if (!isNilT(yLeftSon)){
                yLeftSon.setParent(x);
            }
            RBTreePlusNode xParent = x.getParent();
            y.setParent(xParent); // link x's parent to y
            if (isNilT(xParent)){
                _root = y;
            } else if (x.equals(xParent.getLeftSon())){ // if x is his father's left son
                xParent.setLeftSon(y);
            } else {
                xParent.setRightSon(y);
            }
            y.setLeftSon(x);
            x.setParent(y);
        }
    }

    /**
     * Performs a right rotation of the tree on node y.
     * Time complexity: Θ(1) (as explained in the book, page 234, symmetrical to leftRotate)
     *
     * @param y the node on which the rotation is executed
     */
    private void rightRotate(RBTreePlusNode y){
        RBTreePlusNode x = y.getLeftSon(); // set x to y's left son
        if (!isNilT(x)) { // the rotation can only be executed if x isn't nilT (as explained in the book, page 234)
            RBTreePlusNode xRightSon = x.getRightSon();
            y.setLeftSon(xRightSon); // turn x's right subtree into y's left subtree
            if (!isNilT(xRightSon)) {
                xRightSon.setParent(y);
            }
            RBTreePlusNode yParent = y.getParent();
            x.setParent(yParent); // link y's parent to x
            if (isNilT(yParent)) {
                _root = x;
            } else if (y.equals(yParent.getRightSon())) { // if y is his father's right son
                yParent.setRightSon(x);
            } else {
                yParent.setLeftSon(x);
            }
            x.setRightSon(y);
            y.setParent(x);
        }
    }

    /**
     * Gets a key and inserts a node with this key into the red-black tree.
     * Increments the number of elements in the tree.
     * Uses a method named insertFixup to maintain the red-black tree properties.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 241).
     *
     * @param key the key to be inserted into the tree
     */
    public void insert(int key){
        // z is the node to be inserted - z's key is set to the input key and it is colored red based on the insertion
        // algorithm in the book (page 236)
        RBTreePlusNode z = new RBTreePlusNode(key, RBTreePlusNode.Color.RED);
        RBTreePlusNode y = _nilT;
        RBTreePlusNode x = _root;
        while (!isNilT(x)){ // find the right place to insert z node
            y = x;
            if (z.getKey() < x.getKey()){
                x = x.getLeftSon();
            } else {
                x = x.getRightSon();
            }
        }
        z.setParent(y);
        if (isNilT(y)){ // if the tree was empty - z is the root
            _root = z;
        } else if (z.getKey() < y.getKey()){
            y.setLeftSon(z);
        } else {
            y.setRightSon(z);
        }
        z.setLeftSon(_nilT);
        z.setRightSon(_nilT);
        if (z.getKey() >= _maxNode.getKey()){ // if z's key is greater/equal to maximum - set _max to point to z
            _maxNode = z;
        }
        insertFixup(z);
        _numOfElements++;
    }

    /**
     * Restores the red-black tree properties of the tree after z's insertion.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 241).
     *
     * @param z the input node that was inserted to the tree
     */
    private void insertFixup(RBTreePlusNode z){
        RBTreePlusNode zParent = z.getParent();
        while (zParent.getColor() == RBTreePlusNode.Color.RED){
            RBTreePlusNode zGrandpa = zParent.getParent();
            if (zParent.equals(zGrandpa.getLeftSon())){ // if z's parent is his parent's left son
                RBTreePlusNode zUncle = zGrandpa.getRightSon();
                if (zUncle.getColor() == RBTreePlusNode.Color.RED){
                    // case 1: z's uncle is red - recolor z's parent, uncle and grandpa
                    zParent.setColor(RBTreePlusNode.Color.BLACK);
                    zUncle.setColor(RBTreePlusNode.Color.BLACK);
                    zGrandpa.setColor(RBTreePlusNode.Color.RED);
                    z = zGrandpa;
                } else{
                    if (z.equals(zParent.getRightSon())){
                        // case 2: z is his father's right son - perform left rotation on z's parent
                        z = zParent;
                        leftRotate(z);
                        zParent = z.getParent(); // get z's parent after rotation to use in case 3
                        zGrandpa = zParent.getParent(); // get z's grandpa after rotation to use in case 3
                    }
                    // case 3: z is his father's left son - recolor z's parent and grandpa and perform
                    // right rotation on grandpa
                    zParent.setColor(RBTreePlusNode.Color.BLACK);
                    zGrandpa.setColor(RBTreePlusNode.Color.RED);
                    rightRotate(zGrandpa);
                }
            } else { // if z's parent is his parent's right son (symmetrical to z's parent is his father's left son)
                RBTreePlusNode zUncle = zGrandpa.getLeftSon();
                if (zUncle.getColor() == RBTreePlusNode.Color.RED){
                    // case 1: z's uncle is red - recolor z's parent, uncle and grandpa
                    zParent.setColor(RBTreePlusNode.Color.BLACK);
                    zUncle.setColor(RBTreePlusNode.Color.BLACK);
                    zGrandpa.setColor(RBTreePlusNode.Color.RED);
                    z = zGrandpa;
                } else{
                    if (z.equals(zParent.getLeftSon())){
                        // case 2: z is his father's left son - perform right rotation on z's parent
                        z = zParent;
                        rightRotate(z);
                        zParent = z.getParent(); // get z's parent after rotation to use in case 3
                        zGrandpa = zParent.getParent(); // get z's grandpa after rotation to use in case 3
                    }
                    // case 3: z is his father's right son - recolor z's parent and grandpa and perform
                    // left rotation on grandpa
                    zParent.setColor(RBTreePlusNode.Color.BLACK);
                    zGrandpa.setColor(RBTreePlusNode.Color.RED);
                    leftRotate(zGrandpa);
                }
            }
        } // end of while
        // case 0: fix the root's color to correct property num. 2 of red-black tree
        _root.setColor(RBTreePlusNode.Color.BLACK);
    }

    /**
     * Finds the node with the maximum key in a tree rooted in a given node.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 217).
     *
     * @return the node with the maximum key
     */
    private RBTreePlusNode treeMaxNode(RBTreePlusNode node){
        while (!isNilT(node.getRightSon())){
            node = node.getRightSon();
        }
        return node;
    }

    /**
     * Finds the node with the minimum key in a tree rooted in a given node.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 217).
     *
     * @return the node with the minimum key
     */
    private RBTreePlusNode treeMinNode(RBTreePlusNode node){
        while (!isNilT(node.getLeftSon())){
            node = node.getLeftSon();
        }
        return node;
    }

    /**
     * Returns a node which is the predecessor of the input node.
     * In order to find the predecessor, the method first checks if the node has a left son. If so - the predecessor
     * is the the maximum of left son's subtree (to find this maximum - treeMaxNode method is used). Otherwise, the
     * predecessor is the node's lowest ancestor that has a right son who is also an ancestor of the node.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 218).
     *
     * @param node the input node
     * @return the predecessor node
     */
    public RBTreePlusNode predecessor(RBTreePlusNode node){
        if (!isNilT(node.getLeftSon())){ // if the node has a left son
            return treeMaxNode(node.getLeftSon());
        }
        // if the node doesn't have a left son
        RBTreePlusNode y = node.getParent();
        while (!isNilT(y) && node.equals(y.getLeftSon())){
            node = y;
            y = y.getParent();
        }
        return y;
    }

    /**
     * Returns a node which is the successor of the input node.
     * In order to find the successor, the method first checks if the node has a right son. If so - the successor
     * is the the minimum of right son's subtree (to find this minimum - treeMinNode method is used). Otherwise, the
     * successor is the node's lowest ancestor that has a left son who is also an ancestor of the node.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 218).
     *
     * @param node the input node
     * @return the successor node
     */
    public RBTreePlusNode successor(RBTreePlusNode node){
        if (!isNilT(node.getRightSon())){ // if the node has a right son
            return treeMinNode(node.getRightSon());
        }
        // if the node doesn't have a right son
        RBTreePlusNode y = node.getParent();
        while (!isNilT(y) && node.equals(y.getRightSon())){
            node = y;
            y = y.getParent();
        }
        return y;
    }

    /**
     * Deletes a node from the red-black tree, and uses deleteFixup method to restore the red-black tree properties.
     * Decrements the number of elements in the tree.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 246).
     *
     * @param z the node to be deleted
     * @return the deleted node
     */
    public RBTreePlusNode delete(RBTreePlusNode z){
        RBTreePlusNode y, x;
        if (isNilT(z.getLeftSon()) || isNilT(z.getRightSon())){
            y = z;
        } else {
            y = successor(z);
        }
        if (!isNilT(y.getLeftSon())){
            x = y.getLeftSon();
        } else {
            x = y.getRightSon();
        }
        RBTreePlusNode yParent = y.getParent();
        x.setParent(yParent);
        if (isNilT(yParent)){
            _root = x;
        } else if (y.equals(yParent.getLeftSon())){
            yParent.setLeftSon(x);
        } else {
            yParent.setRightSon(x);
        }
        if (!y.equals(z)){
            z.setKey(y.getKey());
        }
        if (y.getColor() == RBTreePlusNode.Color.BLACK){
            // the fixup should be performed only if the deleted node was black, as a red node deletion does not
            // violate red-black properties of the tree
            deleteFixup(x);
        }
        _numOfElements--;
        return y;
    }

    /**
     * Restores the red-black tree properties of the tree after z's deletion.
     * Time complexity: Θ(logk), where k is the total number of elements in the tree (as explained in the book, page 246).
     *
     * @param x the input node that was deleted from the tree
     */
    private void deleteFixup(RBTreePlusNode x){
        while (!x.equals(_root) && x.getColor() == RBTreePlusNode.Color.BLACK){
            RBTreePlusNode w;
            if (x.equals(x.getParent().getLeftSon())){ // x is a left son
                w = x.getParent().getRightSon();
                if (w.getColor() == RBTreePlusNode.Color.RED){
                    // case 1: w (x's brother) is red
                    w.setColor(RBTreePlusNode.Color.BLACK);
                    x.getParent().setColor(RBTreePlusNode.Color.RED);
                    leftRotate(x.getParent());
                    w = x.getParent().getRightSon();
                }

                if (w.getLeftSon().getColor() == RBTreePlusNode.Color.BLACK &&
                        w.getRightSon().getColor() == RBTreePlusNode.Color.BLACK){
                    // case 2: w and his sons are black
                    w.setColor(RBTreePlusNode.Color.RED);
                    x = x.getParent();
                } else {
                    if (w.getRightSon().getColor() == RBTreePlusNode.Color.BLACK){
                        // case 3: w is black, w's left son is red and w's right son is black
                        w.getLeftSon().setColor(RBTreePlusNode.Color.BLACK);
                        w.setColor(RBTreePlusNode.Color.RED);
                        rightRotate(w);
                        w = x.getParent().getRightSon();
                    }
                    // case 4: w is black and w's right son is red
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(RBTreePlusNode.Color.BLACK);
                    w.getRightSon().setColor(RBTreePlusNode.Color.BLACK);
                    leftRotate(x.getParent());
                    x = _root;
                }

            } else { // x is a right son (symmetrical to x is a left son)
                w = x.getParent().getLeftSon();
                if (w.getColor() == RBTreePlusNode.Color.RED){
                    // case 1: w (x's brother) is red
                    w.setColor(RBTreePlusNode.Color.BLACK);
                    x.getParent().setColor(RBTreePlusNode.Color.RED);
                    rightRotate(x.getParent());
                    w = x.getParent().getLeftSon();
                }

                if (w.getRightSon().getColor() == RBTreePlusNode.Color.BLACK &&
                        w.getLeftSon().getColor() == RBTreePlusNode.Color.BLACK){
                    // case 2: w and his sons are black
                    w.setColor(RBTreePlusNode.Color.RED);
                    x = x.getParent();
                } else {
                    if (w.getLeftSon().getColor() == RBTreePlusNode.Color.BLACK){
                        // case 3: w is black, w's right son is red and w's left son is black
                        w.getRightSon().setColor(RBTreePlusNode.Color.BLACK);
                        w.setColor(RBTreePlusNode.Color.RED);
                        leftRotate(w);
                        w = x.getParent().getLeftSon();
                    }
                    // case 4: w is black and w's left son is red
                    w.setColor(x.getParent().getColor());
                    x.getParent().setColor(RBTreePlusNode.Color.BLACK);
                    w.getLeftSon().setColor(RBTreePlusNode.Color.BLACK);
                    rightRotate(x.getParent());
                    x = _root;
                }
            }
        } // end of while
        x.setColor(RBTreePlusNode.Color.BLACK);
    }

    /**
     * Performs an inorder tree walk and prints the keys of the tree in ascending order.
     * Time complexity: Θ(k), where k is the total number of elements in the tree (as explained in the book, page 214).
     * @param node the start node (root of the tree/sub-tree on which inorder walk is performed)
     */
    private void printTreeInorder(RBTreePlusNode node){
        if (!isNilT(node)){
            printTreeInorder(node.getLeftSon());
            if (node.equals(_maxNode)){
                System.out.println(node.getKey());
            } else {
                System.out.print(node.getKey() + ",");
            }
            printTreeInorder(node.getRightSon());
        }
    }

    /**
     * Prints the keys of the tree in ascending order, by using printTreeInorder method on the root of the tree.
     */
    public void printKMin(){
        printTreeInorder(_root);
    }
}
