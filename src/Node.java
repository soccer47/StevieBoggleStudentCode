public class Node {
    // Char representing character value of node
    char character;
    // Boolean representing whether node is last character of a word in the dictionary
    boolean isWord;
    // Array of nodes representing possible subsequent letters
    Node[] kids = new Node[26];
    // Ints representing coordinated of character of node on the board
    int x;
    int y;


    // Initialize node variables
    public Node (boolean isInDict) {
        isWord = isInDict;
        kids = new Node[26];

        int x = -1;
        int y = -1;
    }

    // Method to set x and y according to the cell coordinate of the letter on the board
    public void setCoordinates(int newX, int newY) {
        x = newX;
        y = newY;
    }

}
