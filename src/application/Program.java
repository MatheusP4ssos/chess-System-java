package application;

import boardgame.Position;
import chess.ChessMatch;

//classe destinada a testes
public class Program {
    public static void main(String[] args) {

        System.out.println("Board test");
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}