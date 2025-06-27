package application;

import boardgame.Position;
import chess.ChessMatch;

//classe destinada a testes
public class Program {
    public static void main(String[] args) {

        System.out.println("Position test");
        Position p1 = new Position(0, 0);
        Position p2 = new Position(1, 0);
        Position p3 = new Position(2, 0);
        System.out.println(p1.toString());
        System.out.println(p2.toString());
        System.out.println(p3.toString());

        System.out.println("Board test");
        ChessMatch chessMatch = new ChessMatch();
        UI.printBoard(chessMatch.getPieces());
    }
}