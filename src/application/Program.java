package application;

import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.Scanner;

//classe destinada a testes
public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Board test");
        ChessMatch chessMatch = new ChessMatch();

        while (true){
            UI.printBoard(chessMatch.getPieces());
            System.out.println();
            System.out.println("Source ");
            ChessPosition source = UI.readChessPosition(sc);

            System.out.println();
            System.out.println("Target ");
            ChessPosition target = UI.readChessPosition(sc);

            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
        }
    }
}