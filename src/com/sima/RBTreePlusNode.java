package com.sima;

/**
 * This class represents a node in the red-black tree.
 * Each node contains three pointers (to parent, left son and right son nodes), key (by value) and color.
 *
 * @author Sima Margulis Davidi
 * @version 30-07-2017
 */

public class RBTreePlusNode {

    // Enum Color used for the color of the red-black tree nodes
    public enum Color {
        RED, BLACK
    }

    // Instance variables - 3 pointers (to parent, left son and right son nodes), key (by value) and color
    private RBTreePlusNode _parent, _leftSon, _rightSon;
    private int _key;
    private Color _color;

    /**
     * Constructor - sets the key and color of the node, and assigns the values of the 3 pointers - parent,
     * left son and right son - to null.
     *
     * @param key
     * @param color
     */
    public RBTreePlusNode(int key, Color color) {
        _parent = null;
        _leftSon = null;
        _rightSon = null;
        _key = key;
        _color = color;
    }

    // Getters for all instance variables
    public RBTreePlusNode getParent() {
        if (_parent == null) {
            return this;
        }
        return _parent;
    }

    public RBTreePlusNode getLeftSon() {
        if (_leftSon == null) {
            return this;
        }
        return _leftSon;
    }

    public RBTreePlusNode getRightSon() {
        if (_rightSon == null) {
            return this;
        }
        return _rightSon;
    }

    public int getKey() {
        return _key;
    }

    public Color getColor() {
        return _color;
    }

    // Setters for all instance variables
    public void setParent(RBTreePlusNode parent) {
        _parent = parent;
    }

    public void setLeftSon(RBTreePlusNode left) {
        _leftSon = left;
    }

    public void setRightSon(RBTreePlusNode right) {
        _rightSon = right;
    }

    public void setKey(int key) {
        _key = key;
    }

    public void setColor(Color color) {
        _color = color;
    }
}
