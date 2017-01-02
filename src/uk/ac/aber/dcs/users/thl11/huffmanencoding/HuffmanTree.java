package uk.ac.aber.dcs.users.thl11.huffmanencoding;

import java.util.*;

/**
 * Created by Thomas Lycett on 18/11/2016.
 */
public class HuffmanTree<E> {

    private HuffmanTreeNode<E> root;
    private int size = 0;
    private int height = 0;
    private int depthSum = 0;
    private float averageDepth;


    /**
     * constructor for HuffmanTree
     * @param frequencies map of values to frequencies to build the tree from
     */
    public HuffmanTree(Map<E, Integer> frequencies) {
        createTree(frequencies);
        calculateTreeStatistics(root, 0);
        averageDepth = (float)depthSum/size;
    }


    /**
     * creates the huffman tree
     * @param frequencies frequencies to build the tree from
     * @return the root node
     */
    private HuffmanTreeNode<E> createTree(Map<E, Integer> frequencies) {
        PriorityQueue<HuffmanTreeNode<E>> nodes =  new PriorityQueue<>((a, b) -> a.compareTo(b));

        System.out.print("creating tree...\n");

        //create a list of nodes from the map of characters and frequencies
        for (Map.Entry<E, Integer> entry: frequencies.entrySet()) {
            E key = entry.getKey();
            Integer frequency = entry.getValue();
            nodes.add(new HuffmanTreeNode<E>(key, frequency));
        }

        size = nodes.size();

        while (nodes.size() > 1) {
            //combine 2 nodes with the lowest frequency
            HuffmanTreeNode<E> a = nodes.poll(), b = nodes.poll(), c;
            c = new HuffmanTreeNode<E>(a.getFrequency() + b.getFrequency(), a, b);
            nodes.add(c);
            size++;
        }

        root = nodes.peek();

        generateBinaryStrings(root);

        return root;
    }


    /**
     * generates and sets the huffman encoding binary strings
     * @param n the root of the tree
     */
    private void generateBinaryStrings(HuffmanTreeNode<E> n) {
        generateBinaryStrings(n, "");
    }


    /**
     * recursive function for setting the huffman encoding binary strings
     * @param n the current node
     * @param code the huffman encoding of the current node
     */
    private void generateBinaryStrings(HuffmanTreeNode<E> n, String code) {
        if (n.isLeaf()) {
            n.setHuffmanEncoding(code);
            return;
        }
        if (n.getLeftChild() != null) {
            generateBinaryStrings(n.getLeftChild(), code + "1");
        }
        if (n.getRightChild() != null) {
            generateBinaryStrings(n.getRightChild(), code + "0");
        }
    }


    /**
     * returns the huffman encoding of a given value, returns null if value doesn't exist
     * @param value the value to return the huffman coding for
     * @return huffman encoding as string, null if not found
     */
    public String getHuffmanEncoding(E value) {
        return find(value).getHuffmanEncoding();
    }


    /**
     * searches the tree for the node containing the value given using a breadth-first search
     * @param value the value to find
     * @return the node containing the value, null if not found
     */
    public HuffmanTreeNode<E> find(E value) {
        Queue<HuffmanTreeNode<E>> queue = new LinkedList<>();
        HuffmanTreeNode<E> n;

        queue.offer(root);
        while (!queue.isEmpty()) {
            n = queue.poll();
            if (n.getValue() == value) {
                return n;
            }
            if (n.getLeftChild() != null) {
                queue.offer(n.getLeftChild());
            }
            if (n.getRightChild() != null) {
                queue.offer(n.getRightChild());
            }
        }
        return null;
    }


    /**
     * calculates the sum of depths and height of the tree
     * @param n the current node
     * @param currentDepth depth at the current node
     */
    private void calculateTreeStatistics(HuffmanTreeNode<E> n, int currentDepth) {
        depthSum += currentDepth;
        if (currentDepth > height) {
            height = currentDepth;
        }
        if (n.getLeftChild() != null) {
            calculateTreeStatistics(n.getLeftChild(), currentDepth + 1);
        }
        if (n.getRightChild() != null) {
            calculateTreeStatistics(n.getRightChild(), currentDepth + 1);
        }
    }


    /**
     * returns the number of nodes in the tree
     * @return the number of nodes in the tree
     */
    public int getSize() {
        return size;
    }


    /**
     * returns the height of the tree
     * @return the height of the tree
     */
    public int getHeight() {
        return height;
    }


    /**
     * returns the average depth
     * @return the average depth
     */
    public float getAverageDepth() {
        return averageDepth;
    }
}
