package ArtificialIntelligence;

import TicTacToe.Board;

class Random {

    private Random () {
    }

    static void run (Board board) {
        int[] moves = new int[board.getAvailableMoves().size()];        // move[]=size of availed mouse
        int index = 0;

        for (Integer item : board.getAvailableMoves()) {               
            moves[index++] = item;
        }

        int randomMove = moves[new java.util.Random().nextInt(moves.length)];
        board.move(randomMove);           // move board as mouse move.length
    }

}
