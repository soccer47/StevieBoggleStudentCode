import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Stack;

public class Boggle {

    // Stack that holds the next possible letters on the board that can be added to the current String
    static Stack<Node> nextLetters = new Stack<>();

    public static String[] findWords(char[][] board, String[] dictionary) {

        ArrayList<String> goodWords = new ArrayList<String>();

        // Make a TST containing the words in the dictionary
        Trie dict = new Trie();

        for (int i = 0; i < dictionary.length; i++) {
            dict.insert(dictionary[i]);
        }

        // 2D boolean array that represents characters on board that have been visited
        boolean[][] isVisited = new boolean[board.length][board[0].length];

        // Iterate through every letter in the board
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                // Current node being visited
                Node current = dict.root[getLetter(board[i][j])];
                // String representing current String constructed from board
                String word = current.character + "";
                // Add the next cells to be visited to the stack
                addNextCells(i, j, isVisited, board, current);
                // Explore possible words on the board until no new possibilities are left
                while (!nextLetters.isEmpty()) {
                    // Make the current Node the next cell to be explored
                    current = nextLetters.pop();

                    // Add the next possible cells to the stack
                    addNextCells(current.x, current.y, isVisited, board, current);

                    if (current.isWord) {
                        goodWords.add(word);
                        current.isWord = false;
                    }
                }
            }
        }

        // Convert the list into a sorted array of strings, then return the array.
        String[] sol = new String[goodWords.size()];
        goodWords.toArray(sol);
        Arrays.sort(sol);
        return sol;
    }

    // Method to get the value of the letter (0-25) from the inputted ascii value
    public static int getLetter(int asciiVal) {
        return (asciiVal - 97);
    }

    // Method that returns whether the inputted cell is visitable on the board
    public static boolean isValidCell(int x, int y, boolean[][] isVisited) {
        // Return false if the coordinates are out of the possible bounds
        if (x < 0 || x > isVisited[0].length || y < 0 || y > isVisited.length) {
            return false;
        }
        // Also return false if the cell has already been visited
        else if (isVisited[x][y]) {
            return false;
        }
        // Otherwise return true
        return true;
    }

    // Method that adds the next visitable cells on the board to the stack nextLetters
    public static void addNextCells(int x, int y, boolean[][] isVisited, char[][] board, Node current) {
        if (isValidCell(x - 1, y, isVisited)) {
            current.kids[getLetter(board[x - 1][y])].x = x - 1;
            current.kids[getLetter(board[x - 1][y])].y = y;
            nextLetters.add(current.kids[getLetter(board[x - 1][y])]);
        }
        if (isValidCell(x + 1, y, isVisited)) {
            current.kids[getLetter(board[x + 1][y])].x = x + 1;
            current.kids[getLetter(board[x + 1][y])].y = y;
            nextLetters.add(current.kids[getLetter(board[x + 1][y])]);
        }
        if (isValidCell(x, y - 1, isVisited)) {
            current.kids[getLetter(board[x][y - 1])].x = x;
            current.kids[getLetter(board[x][y - 1])].y = y - 1;
            nextLetters.add(current.kids[getLetter(board[x][y - 1])]);
        }
        if (isValidCell(x, y + 1, isVisited)) {
            current.kids[getLetter(board[x][y + 1])].x = x;
            current.kids[getLetter(board[x][y + 1])].y = y + 1;
            nextLetters.add(current.kids[getLetter(board[x][y + 1])]);
        }
    }
}
