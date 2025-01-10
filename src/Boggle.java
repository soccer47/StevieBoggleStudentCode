import java.util.ArrayList;
import java.util.Arrays;

public class Boggle {

    // Make a Trie to contain the words in the dictionary
    static Trie dict;
    // 2D array of booleans representing visited squares on board
    static boolean[][] isVisited;
    // Placeholder for board of chars
    static char[][] theBoard;
    // ArrayList of words to be returned
    static ArrayList<String> goodWords;

    public static String[] findWords(char[][] board, String[] dictionary) {
        // Set theBoard to the inputted board array
        theBoard = board;
        // Reset and initialize goodWords;
        goodWords = new ArrayList<String>();
        // Reset and initialize the Trie representing the dictionary
        dict = new Trie();
        // Add all the words in the dictionary to the trie dict
        for (String s : dictionary) {
            dict.insert(s);
        }
        // Reset and initialize isVisited to a 2D array of booleans of the same dimensions as the board
        isVisited = new boolean[board.length][board[0].length];

        // Call DFS recursively on each cell of the board, starting with the top left cell on the board
        for (int i = 0; i < theBoard.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // Call DFS on the current cell, inputting an empty String to start
                DFS(i, j, "");
            }
        }

        // Convert the list into a sorted array of strings, then return the array.
        String[] sol = new String[goodWords.size()];
        goodWords.toArray(sol);
        Arrays.sort(sol);
        return sol;
    }


    // Recursive approach to DFS
    public static void DFS (int row, int col, String word) {
        // If the coordinate on the board is valid, add the character of the cell to the word
        if (isValidCell(row, col)) {
            word = word + theBoard[row][col];
        }
        // Otherwise the coordinate cannot be visited, so return
        else {
            return;
        }
        // Also return if the current word isn't a valid prefix for any words in the dictionary
        if (!dict.isValidPrefix(word)) {
            return;
        }

        // If the word exists in the dictionary, add it to goodWords
        if (dict.isInTrie(word)) {
            goodWords.add(word);
            // Remove the word from the dictionary to prevent duplicate additions
            dict.removeWord(word);
        }

        // Show that the current cell has been visited
        isVisited[row][col] = true;
        // Recurse up, down, left, and right with the updated word
        DFS(row, col + 1, word);
        DFS(row, col - 1, word);
        DFS(row - 1, col, word);
        DFS(row + 1, col, word);
        // Mark the current square as not visited
        isVisited[row][col] = false;
    }

    // Method that returns whether the inputted cell is visitable on the board
    public static boolean isValidCell(int x, int y) {
        // Return false if the coordinates are out of the possible bounds of the board
        if (x < 0 || x >= theBoard[0].length || y < 0 || y >= theBoard.length) {
            return false;
        }
        // Also return false if the cell has already been visited, otherwise return true
        return !isVisited[x][y];
    }
}
