package uk.ac.aber.dcs.users.thl11.huffmanencoding;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Thomas Lycett on 16/11/2016.
 */
public class HuffmanEncodingSystem {
    private boolean exit = false;
    private String huffmanEncoding;
    private String text;
    private HuffmanTree<Character> tree;
    private HashMap<Character, Integer> characterFrequencies;


    /**
     * runs the Huffman encoding system
     */
    public void run() {
        while (!exit) {
            text = null;
            handleUserInput();
            if (text != null) {
                characterFrequencies = calculateCharacterFrequencies(text);

                tree = new HuffmanTree<>(characterFrequencies);
                huffmanEncoding = encodeString(text, tree);

                System.out.print("done\n\n");

                printStatistics();
            }
        }
    }


    /**
     * prints the statistics
     */
    private void printStatistics() {
        System.out.print("fixed-length size: " + fixedLengthSize() + "\n");
        System.out.print("huffman encoding size: " + huffmanEncoding.length() + "\n");
        System.out.print("compression ratio: " + (float)(fixedLengthSize())/ huffmanEncoding.length() + "\n\n");
        System.out.print("tree height: " + tree.getHeight() + "\n");
        System.out.print("number of nodes in tree: " + tree.getSize() + "\n");
        System.out.print("average depth: " + tree.getAverageDepth() + "\n\n");
    }


    /**
     * returns the text from an text file given the file path
     * @param filePath the path of the file
     * @return the text in the file as a String. null if file is not found.
     */
    public String readFile(String filePath){
        File file;
        String s;
        BufferedInputStream in = null;
        int offset = 0;

        try {
            file = new File(filePath);
            in = new BufferedInputStream(new FileInputStream(file));
            int length = (int)file.length();
            byte[] bytes = new byte[length];

            System.out.println("reading file...");

            int count = 0;
            while(in.available() > 0) {
                count = in.read(bytes, offset, length);
                offset += count;
            }

            s = new String(bytes);
        } catch (IOException e) {
            System.out.println("Could not read file: " + filePath);
            s = null;
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return s;
    }


    /**
     * asks user to select an option
     */
    private void handleUserInput() {
        String filePath = "";
        int option;
        Scanner in = new Scanner(System.in);

        System.out.print("[0]morse.txt\n[1]random10000.txt\n[2]random20000.txt\n[3]randomBiased30000.txt\n[4]shakespeare.txt" +
                "\n[5]other file\n[6]exit\n");
        option = in.nextInt();

        switch (option) {
            case 0:
                filePath = "morse.txt";
                break;
            case 1:
                filePath = "random10000.txt";
                break;
            case 2:
                filePath = "random20000.txt";
                break;
            case 3:
                filePath = "randomBiased30000.txt";
                break;
            case 4:
                filePath = "shakespeare.txt";
                break;
            case 5:
                System.out.print("Enter the path of a text file: ");
                filePath = in.next();
                break;
            case 6:
                exit = true;
                filePath = null;
                break;
        }

        if (filePath != null) {
            text = readFile(filePath);
        }
    }

    /**
     * calculates the frequency of each unique character in a string.
     * @param text the text to calculate characterFrequencies from
     * @return a HashMap using the character as key and frequency as value
     */
    private HashMap<Character, Integer> calculateCharacterFrequencies(String text) {
        HashMap<Character, Integer> frequencies = new HashMap<Character, Integer>();

        System.out.println("calculating character frequencies...");

        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            Integer frequency = frequencies.get(c);
            if (frequency != null) {
                frequencies.put(c, frequency + 1);
            } else {
                frequencies.put(c, 1);
            }
        }
        return frequencies;
    }


    /**
     * encode a string with the Huffman encoding from tree
     * @param text the string to encode
     * @param tree the tree to use to get the huffman encoding
     * @return the encoded string
     */
    private String encodeString(String text, HuffmanTree<Character> tree) {
        StringBuilder binaryCode = new StringBuilder();
        HashMap<Character, String> codes = new HashMap<>();

        System.out.print("encoding text...\n");

        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            String code = codes.get(c);
            if (code == null) {
                code = tree.getHuffmanEncoding(c);
                codes.put(c, code);
            }
            binaryCode.append(code);
        }
        return binaryCode.toString();
    }


    /**
     * returns the size of the fixed length encoding of text
     * @return the size of the fixed length encoding
     */
    private int fixedLengthSize() {
        int bitsPerCharacter = 0;

        while (Math.pow(2, bitsPerCharacter) < characterFrequencies.size()) {
            bitsPerCharacter++;
        }

        return bitsPerCharacter * text.length();
    }
}