import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Boggle {

    // Make a TST to contain the words in the dictionary
    static Trie dict;
    // 2D array of booleans representing visited squares on board
    static boolean[][] isVisited;
    // Placeholder for board of chars
    static char[][] theBoard;
    // ArrayList of words to be returned
    static ArrayList<String> goodWords;

    public static String[] findWords(char[][] board, String[] dictionary) {

        // Set theBoard to board
        theBoard = board;
        // Reset and initialize goodWords;
        goodWords = new ArrayList<String>();
        // Reset and initialize dict
        dict = new Trie();
        // Add all the words in the dictionary to the trie dict
        for (int i = 0; i < dictionary.length; i++) {
            dict.insert(dictionary[i]);
        }
        // 2D boolean array that represents characters on board that have been visited
        isVisited = new boolean[board.length][board[0].length];

        // Call DFS recursively on each cell of the board, starting with the top left cell on the board
        for (int i = 0; i < theBoard.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // String representing current String constructed from board
                String word = "";
                DFS(i, j, word);
            }
        }

        // Convert the list into a sorted array of strings, then return the array.
        String[] sol = new String[goodWords.size()];
        goodWords.toArray(sol);
        Arrays.sort(sol);
        return sol;
    }


    // Recursive method approach to DFS
    public static void DFS (int row, int col, String word) {
        // If the coordinate on the board is valid, add the character of the cell to the word
        if (isValidCell(row, col)) {
            word = word + theBoard[row][col];
        }
        // Otherwise the coordinate cannot be visited, so return
        else {
            return;
        }
        // Return if the current word isn't a valid prefix for any words in the dictionary
        if (!dict.isValidPrefix(word)) {
            return;
        }

        if (dict.isInTrie(word)) {
            // If the word exists in the dictionary, add it to goodWords
            goodWords.add(word);
            // Remove the word from the dictionary to prevent duplicates
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
        // Return false if the coordinates are out of the possible bounds
        if (x < 0 || x >= theBoard[0].length || y < 0 || y >= theBoard.length) {
            return false;
        }
        // Also return false if the cell has already been visited, otherwise return true
        return !isVisited[x][y];
    }
}
