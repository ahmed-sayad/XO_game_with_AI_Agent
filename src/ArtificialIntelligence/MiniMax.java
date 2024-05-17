package ArtificialIntelligence;

import TicTacToe.Board;

class MiniMax {

    private static double maxPly;

    private MiniMax() {
    }
    
    static void run(Board.State player, Board board, double maxPly) {
        if (maxPly < 1) {                       // if there is any players then throw exception
            throw new IllegalArgumentException("maximum depth must be greater than 0");
        }

        MiniMax.maxPly = maxPly;
        miniMax(player, board, 0);
    }

    /*
     the bone of the algorithm
  
     */
    private static int miniMax(Board.State player, Board board, int currentPly) {
        if (currentPly++ == maxPly || board.isGameOver()) {
            return score(player, board);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, currentPly);
        } else {
            return getMin(player, board, currentPly);
        }

    }

    /*
     play the move with the highest score
     
     */
    private static int getMax(Board.State player, Board board, int currentPly) {
        double bestScore = Double.NEGATIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentPly);

            if (score >= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int) bestScore;
    }

    /*
     play the move with the lowest score

     */
    private static int getMin(Board.State player, Board board, int currentPly) {
        double bestScore = Double.POSITIVE_INFINITY;
        int indexOfBestMove = -1;

        for (Integer theMove : board.getAvailableMoves()) {

            Board modifiedBoard = board.getDeepCopy();
            modifiedBoard.move(theMove);

            int score = miniMax(player, modifiedBoard, currentPly);

            if (score <= bestScore) {
                bestScore = score;
                indexOfBestMove = theMove;
            }

        }

        board.move(indexOfBestMove);
        return (int) bestScore;
    }

    /*
     get the score of the board
     
     * @param player the play that the AI will identify as
     * @param board the XO board to play on
     * @return the score of the board
     */
    private static int score(Board.State player, Board board) {
        if (player == Board.State.Blank) {           // if board is empty
            throw new IllegalArgumentException("Player must be X or O");
        }

        Board.State opponent = (player == Board.State.X) ? Board.State.O : Board.State.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10;   // player wins
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10;   // AI wins
        } else {
            return 0;   // balance
        }
    }

}
