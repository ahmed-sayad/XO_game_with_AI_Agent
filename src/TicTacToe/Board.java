package TicTacToe;

import java.util.HashSet;              // to create a collection that uses a hash table for storage

public class Board {

    static final int BOARD_WIDTH = 3;           // will used as 3 cells for width and 3 cells for hieght

    public enum State {                    // enum containes a three variables of cell state {blank - X - O}
        Blank, X, O
    }
    private State[][] board;               // matrix for the bord
    private State playersTurn;             //which of (X - O) player will play
    private State winner;                    // var for the winner
    private HashSet<Integer> movesAvailable;     // availability of mouse

    private int moveCount;                 // counter
    private boolean gameOver;             // bool var for gameover or not yet

    /*
      the board
     */
    Board() {
        board = new State[BOARD_WIDTH][BOARD_WIDTH];
        movesAvailable = new HashSet<>();
        reset();                           // method to recet the bord after gameover
    }

    /*
      set the cells intialized
     */
    private void initialize() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = State.Blank;                  // blank all rows and columns
            }
        }
        movesAvailable.clear();

        for (int i = 0; i < BOARD_WIDTH * BOARD_WIDTH; i++) {
            movesAvailable.add(i);                         // add (X) if the mouse clicked at the space between width and hight of bord
        }
    }

    /*
       restart the game with a new blank board after game over
     */
    void reset() {
        moveCount = 0;             // counter = 0
        gameOver = false;          // game will be start
        playersTurn = State.X;     //player will play with X
        winner = State.Blank;      //winner = null
        initialize();              // method to blanck all rows and all columns
    }

    /*
      put "X" or "O" on the specified index depending on whose turn it
     */
    public boolean move(int index) {
        return move(index % BOARD_WIDTH, index / BOARD_WIDTH);     // @return true if the move has not already been played
    }

    /*
     places an "X" or "O" on the specified location depending on who turn it

     */
    private boolean move(int x, int y) {

        if (gameOver) {
            throw new IllegalStateException("XO play is over! No moves can be played again");
        }

        if (board[y][x] == State.Blank) {
            board[y][x] = playersTurn;         // if bord blanck then player can play
        } else {
            return false;
        }

        moveCount++;                          // add 1 to the count
        movesAvailable.remove(y * BOARD_WIDTH + x);

        // the game is a balanced
        if (moveCount == BOARD_WIDTH * BOARD_WIDTH) {        // if counter = 9 then gameover
            winner = State.Blank;
            gameOver = true;
        }

        // check for the winner
        checkRow(y);
        checkColumn(x);
        checkDiagonalFromTopLeft(x, y);
        checkDiagonalFromTopRight(x, y);

        playersTurn = (playersTurn == State.X) ? State.O : State.X;     // if PlayerState=X the n return state O
        return true;
    }

    /*
     check to see if the game is over (if there is a winner or a balance)
     
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return gameOver;              // gameover = true;
    }

    /*
     get a copy of the array that represents the board
     
     * @return the board array
     */
    State[][] toArray() {
        return board.clone();         //clone of board
    }

    /*
     check to see who's turn it is
     
     * @return the player who's turn it is
     */
    public State getTurn() {
        return playersTurn;       // return the Plater state
    }

    /*
     check to see who won
    
     * @return the player who won (or Blank if the game is a balance)
     */
    public State getWinner() {
        if (!gameOver) {
            throw new IllegalStateException("XO play is not over yet!");
        }
        return winner;
    }

    /*
     get the indexes of all the positions on the board that are empty
      
     @return the empty cells
     */
    public HashSet<Integer> getAvailableMoves() {
        return movesAvailable;
    }

    /*
      checks the specified row to see if there is a winner
     
     */
    private void checkRow(int row) {
        for (int i = 1; i < BOARD_WIDTH; i++) {   
            if (board[row][i] != board[row][i - 1]) {   //check if the is balance
                break;
            }
            if (i == BOARD_WIDTH - 1) {       // if there is three X in one row then the winner=player, and the game is over
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    /*
      checks the specified column to see if there is a winner
   
     
     */
    private void checkColumn(int column) {
        for (int i = 1; i < BOARD_WIDTH; i++) {
            if (board[i][column] != board[i - 1][column]) {      //check if the is balance
                break;
            }
            if (i == BOARD_WIDTH - 1) {          // if there is three X in one column then the winner=player, and the game is over
                winner = playersTurn;
                gameOver = true;
            }
        }
    }

    /*
     check the left diagonal to see if there is a winner
     
     */
    private void checkDiagonalFromTopLeft(int x, int y) {
        if (x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[i][i] != board[i - 1][i - 1]) {     //check if the is balance
                    break;
                }
                if (i == BOARD_WIDTH - 1) {  // if there is three X in left diagonal then the winner=player, and the game is over
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    /*
     check the right diagonal to see if there is a winner.
    
     */
    private void checkDiagonalFromTopRight(int x, int y) {
        if (BOARD_WIDTH - 1 - x == y) {
            for (int i = 1; i < BOARD_WIDTH; i++) {
                if (board[BOARD_WIDTH - 1 - i][i] != board[BOARD_WIDTH - i][i - 1]) {    //check if the is balance
                    break;
                }
                if (i == BOARD_WIDTH - 1) {    // if there is three X in right diagonal then the winner=player, and the game is over
                    winner = playersTurn;
                    gameOver = true;
                }
            }
        }
    }

    /*
      Get a deep copy of XO board
     
     * @return an identical copy of the board
     */
    public Board getDeepCopy() {
        Board board = new Board();   // object from board

        for (int i = 0; i < board.board.length; i++) {
            board.board[i] = this.board[i].clone();
        }

        board.playersTurn = this.playersTurn;
        board.winner = this.winner;
        board.movesAvailable = new HashSet<>();
        board.movesAvailable.addAll(this.movesAvailable);
        board.moveCount = this.moveCount;
        board.gameOver = this.gameOver;
        return board;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < BOARD_WIDTH; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {

                if (board[y][x] == State.Blank) {
                    sb.append("-");
                } else {
                    sb.append(board[y][x].name());
                }
                sb.append(" ");

            }
            if (y != BOARD_WIDTH - 1) {
                sb.append("\n");
            }
        }

        return new String(sb);
    }

}
