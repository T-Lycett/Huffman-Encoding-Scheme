package uk.ac.aber.dcs.users.thl11.huffmanencoding;

/**
 * Created by Thomas Lycett on 18/11/2016.
 */
public class HuffmanTreeNode<E> implements Comparable<HuffmanTreeNode<E>> {
    private E value;
    private Integer frequency;
    private String huffmanEncoding;
    private HuffmanTreeNode<E> leftChild, rightChild;


    /**
     * constructor for the HuffmanTreeNode class
     * @param value symbol/value to represent
     * @param frequency the frequency this symbol occurs
     * @param leftChild node on the left branch
     * @param rightChild node on the right branch
     */
    public HuffmanTreeNode(E value, Integer frequency, HuffmanTreeNode<E> leftChild, HuffmanTreeNode<E> rightChild) {
        this.value = value;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }


    /**
     * constructor for the HuffmanTreeNode class
     * @param frequency the frequency this symbol occurs
     * @param leftChild node on the left branch
     * @param rightChild node on the right branch
     */
    public HuffmanTreeNode(Integer frequency, HuffmanTreeNode<E> leftChild, HuffmanTreeNode<E> rightChild) {
        this.value = null;
        this.huffmanEncoding = null;
        this.frequency = frequency;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }


    /**
     * constructor for the HuffmanTreeNode class
     * @param value symbol/value to represent
     * @param frequency the frequency this symbol occurs
     */
    public HuffmanTreeNode(E value, Integer frequency) {
        this.value = value;
        this.frequency = frequency;
    }


    /**
     * returns the value this node represents
     * @return the value this node represents
     */
    public E getValue() {
        return value;
    }


    /**
     * sets the value this node represents
     * @param value the new value to represent
     */
    public void setValue(E value) {
        this.value = value;
    }


    /**
     * returns the number of times this value occurs
      * @return the number of times this value occurs
     */
    public Integer getFrequency() {
        return frequency;
    }


    /**
     * sets the frequency for this node
     * @param frequency the new frequency
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }


    /**
     * returns the Huffman encoding for this node as a string
     * @return the Huffman encoding as a string
     */
    public String getHuffmanEncoding() {
        return huffmanEncoding;
    }


    /**
     * sets the Huffman encoding for this node
     * @param huffmanEncoding the new huffman encoding
     */
    public void setHuffmanEncoding(String huffmanEncoding) {
        this.huffmanEncoding = huffmanEncoding;
    }


    /**
     * returns the left child of this node
     * @return the left child of this node
     */
    public HuffmanTreeNode<E> getLeftChild() {
        return leftChild;
    }


    /**
     * sets the left child of this node
     * @param leftChild the new left child
     */
    public void setLeftChild(HuffmanTreeNode<E> leftChild) {
        this.leftChild = leftChild;
    }


    /**
     * returns the right child of this node
     * @return the right child of this node
     */
    public HuffmanTreeNode<E> getRightChild() {
        return rightChild;
    }


    /**
     * sets the right child of this node
     * @param rightChild the new right child
     */
    public void setRightChild(HuffmanTreeNode<E> rightChild) {
        this.rightChild = rightChild;
    }


    /**
     * returns true if this node is a leaf node, false if not. a node is a leaf if it has no children
     * @return true if this node is a leaf node, false if not
     */
    public boolean isLeaf() {
        if (leftChild == null && rightChild == null) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * compares the node frequencies
     * @param node the node to compare to
     * @return 1 if this node has a higher frequency, 0 if both nodes are equal, -1 if frequency is lower
     */
    @Override
    public int compareTo(HuffmanTreeNode<E> node) {
        if (this == node)
            return 0;

        int r = this.getFrequency().compareTo(node.getFrequency());
        if (r != 0)
            return r;

        if (this.getValue() != null && node.getValue() != null) {
            if (!(this.getValue().equals(node.getValue()))) {
                r = 1;
            } else {
                r = 0;
            }
        } else {
            r = 1;
        }

        return r;
    }
}
