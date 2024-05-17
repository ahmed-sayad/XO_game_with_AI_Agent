package ArtificialIntelligence;

import TicTacToe.Board;

/*
  uses various algorithms to play XO
 */
public class Algorithms {

    /*
      Algorithms cannot be instantiated
     */
    private Algorithms() {
    }

    /*
      if he play a random move
     *
     * @param board the XO board to play on
     */
    public static void random(Board board) {
        Random.run(board);
    }

    /*
       play using the MiniMax Algorithm.
     *
     * @param board the XO board to play on
     */
    public static void miniMax(Board board) {
        MiniMax.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    /*
      Play using the MiniMax algorithm, include a depth limit
     
     * @param board the XO board to play on
     * @param ply the maximum depth
     */
    public static void miniMax(Board board, int ply) {
        MiniMax.run(board.getTurn(), board, ply);
    }

    /*
     Play using the Alpha-Beta Pruning algorithm.
     
     * @param board the XO board to play on
     */
    public static void alphaBetaPruning(Board board) {
        AlphaBetaPruning.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    /*
     Play using the Alpha-Beta Pruning algorithm, include a depth limit
     
     * @param board the XO board to play on
     * @param ply the maximum depth
     */
    public static void alphaBetaPruning(Board board, int ply) {
        AlphaBetaPruning.run(board.getTurn(), board, ply);
    }

    /*
     play using the Alpha-Beta Pruning algorithm. Include depth in the evaluation function
     
     * @param board the XO board to play on
     */
    public static void alphaBetaAdvanced(Board board) {
        AlphaBetaAdvanced.run(board.getTurn(), board, Double.POSITIVE_INFINITY);
    }

    /*
     play using the Alpha-Beta Pruning algorithm. Include depth in the evaluation function and a depth limit
     * @param board the XO board to play on
     * @param ply the maximum depth
     */
    public static void alphaBetaAdvanced(Board board, int ply) {
        AlphaBetaAdvanced.run(board.getTurn(), board, ply);
    }

}
