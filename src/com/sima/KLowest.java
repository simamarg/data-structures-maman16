package com.sima;

/**
 * This class represents the algorithm for the assignment.
 * The algorithm passes over the array once and prints the k lowest elements encountered up to checkpoints
 * 1, 2, 3 and 4 (4th point is the end of the array).
 * The algorithm uses the RBTreePlus data structure for storing the k lowest elements checked so far.
 *
 * @author Sima Margulis Davidi
 * @version 30-07-2017
 */

public class KLowest {

    /**
     * Runs the algorithm. Receives the InputData (object which contains n, k, array with random numbers
     * (integers between 0-1023) and 4 checkpoints), passes over the array once and prints the k lowest elements
     * encountered up to checkpoints 1, 2, 3 and 4.
     * Uses replaceNode method if the RBTreePlus structure has k (lowest) elements to replace the node that has the
     * max. key with a new node with smaller key.
     * Time complexity: Θ(n), where n is the total number of elements in the array (passes once over the array elements).
     *
     * @param input the input data
     */
    public void run(InputData input){
        int[] array = input.getArray();
        RBTreePlus tree = new RBTreePlus();
        int checkpointsCount = 0;
        for (int i=0; i < input.getN(); i++){
            int elements = tree.getNumOfElements();
            if (elements < input.getK()){ // if there are less than k elements - insert into the tree
                tree.insert(array[i]);
            } else { // if there are k elements - use replaceNode to handle max node replacement
                replaceNode(tree, array[i]);
            }
            if (i == input.getCheckpointN1()-1 || i == input.getCheckpointN2()-1 || i == input.getCheckpointN3()-1 ||
                    i == input.getCheckpointN4()-1){
                // if checkpoint is reached - print k lowest elements up to this point
                checkpointsCount++;
                System.out.println("\n" + input.getK() + " lowest elements in check point " + checkpointsCount + ":");
                tree.printKMin();
            }
        }
    }

    /**
     * Handles max key node replacement, if the input key is smaller than max key. (Does nothing if the input key
     * is not smaller than max key.)
     * Time complexity: Θ(logk), where k is the total number of elements in the tree. (Θ(logk) for delete(RBTreePlusNode) +
     *  + Θ(1) for setMax(RBTreePlusNode) + Θ(logk) for insert(RBTreePlusNode))
     *
     * @param tree the input tree on which replacement occurs
     * @param key the key to be inserted to the tree instead of the max node if it's smaller than max key
     */
    private void replaceNode(RBTreePlus tree, int key){
        RBTreePlusNode maxNode = tree.getMaxNode();
        if (key < maxNode.getKey()){
            RBTreePlusNode maxPredNode = tree.predecessor(maxNode); // used for the predecessor of the max node
            if (key >= maxPredNode.getKey()){ // if key is the new maximum - changes the key of the max node
                maxNode.setKey(key);
            } else { // if key isn't the new maximum - deletes the max node and inserts key into the tree
                tree.delete(maxNode);
                tree.setMaxNode(maxPredNode);
                tree.insert(key);
            }
        }
    }
}