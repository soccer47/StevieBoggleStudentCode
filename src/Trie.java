// Trie class
public class Trie {
    // Array of nodes to represent the root of Trie
    Node[] root = new Node[26];

    // Node class
    public class Node {
        // Boolean representing whether node represents last character of a word in the dictionary
        boolean isWord;
        // Array of nodes representing possible subsequent letters
        Node[] kids;

        // Initialize node variables
        public Node (boolean isInDict) {
            isWord = isInDict;
            kids = new Node[26];
        }
    }

    // Method that returns whether given string is in the Trie or not
    public boolean isInTrie(String word) {
        // Start by setting the current node to the index of the first character in the root array
        Node current = root[getLetter(word.charAt(0))];
        // Substring representing remaining suffix
        String remainder = word.substring(1);
        // Iterate through every letter in the word
        while (!remainder.isEmpty()) {
            // Set charVal equal to the integer value of the current word's character (a = 0, z = 25)
            int charVal = getLetter(remainder.charAt(0));

            // If the next letter's index in the Node's kid array is null, word isn't in dictionary, so return false
            if (current == null || current.kids[charVal] == null) {
                return false;
            }
            else {
                // Otherwise set the current node to the node of the subsequent letter
                current = current.kids[charVal];
            }
            // Decrement remaining suffix by 1
            remainder = remainder.substring(1);
        }
        // If the node of the last letter is null, return false
        if (current == null) {
            return false;
        }
        // Return whether current node represents end of real word or not
        return current.isWord;
    }

    public boolean isValidPrefix(String word) {
        // Start by setting the current node to the index of the first character in the root array
        Node current = root[getLetter(word.charAt(0))];
        // Substring representing remaining suffix
        String remainder = word.substring(1);
        // Iterate through every letter in the prefix
        while (!remainder.isEmpty()) {
            // Set charVal equal to the integer value of the current prefix's character (a = 0, z = 25)
            int charVal = getLetter(remainder.charAt(0));

            // If the next letter's index in the Node's kid array is null, prefix isn't in dictionary, so return false
            if (current == null || current.kids[charVal] == null) {
                return false;
            }
            else {
                // Otherwise set the current node to the node of the subsequent letter
                current = current.kids[charVal];
            }
            // Decrement remaining suffix by 1
            remainder = remainder.substring(1);
        }
        // Return true because the prefix is part of a valid word in the trie dictionary
        return true;
    }


    // Method to insert new words into the Trie
    public void insert(String word) {
        // Start by setting the current node to the index of the first character in the root array
        if (root[getLetter(word.charAt(0))] == null) {
            root[getLetter(word.charAt(0))] = new Node(false);
        }
        Node current = root[getLetter(word.charAt(0))];
        // Substring representing remaining suffix
        String remainder = word.substring(1);
        // Iterate through every letter in the word
        while (!remainder.isEmpty()) {
            // Set charVal equal to the integer value of the current word's character (a = 0, z = 25)
            int charVal = getLetter(remainder.charAt(0));
            // Decrement remaining suffix by 1
            remainder = remainder.substring(1);

            // If the next letter's index in the Node's kid array is null, create a new node to represent the letter
            if (current.kids[charVal] == null) {
                current.kids[charVal] = new Node(false);
                // Set current node to the node of the subsequent letter
                current = current.kids[charVal];
            }
            else {
                // Otherwise just set the current node to the node of the subsequent letter
                current = current.kids[charVal];
            }
        }
        // Set isWord of the current node to true to represent that word is in dictionary
        current.isWord = true;
    }

    // Method to make a word not valid in the dictionary (in order to prevent duplicates)
    public void removeWord(String word) {
        // Start by setting the current node to the index of the first character in the root array
        if (root[getLetter(word.charAt(0))] == null) {
            root[getLetter(word.charAt(0))] = new Node(false);
        }
        Node current = root[getLetter(word.charAt(0))];
        // Substring representing remaining suffix
        String remainder = word.substring(1);
        // Iterate through every letter in the word to get to the node of the final letter
        while (!remainder.isEmpty()) {
            // Set charVal equal to the integer value of the current word's character (a = 0, z = 25)
            int charVal = getLetter(remainder.charAt(0));
            // Decrement remaining suffix by 1
            remainder = remainder.substring(1);
            // Set the current node to the node of the subsequent letter
            current = current.kids[charVal];
        }
        // Set isWord of the current node to false
        current.isWord = false;
    }

    // Method to get the value of the letter (0-25) from the inputted ascii value
    public char getLetter(int asciiVal) {
        return (char)(asciiVal - 97);
    }
}