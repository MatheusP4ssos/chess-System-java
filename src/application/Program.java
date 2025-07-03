package application;


import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.InputMismatchException;
import java.util.Scanner;

// Classe principal que executa o jogo
public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Inicia uma nova partida
        System.out.println("Board test");
        ChessMatch chessMatch = new ChessMatch();

        // Loop principal do jogo
        while (true) {
            try {
                // Limpa a tela e mostra o estado atual
                UI.clearScreen();
                UI.printMatch(chessMatch);
                System.out.println();

                // Lê a posição de origem
                System.out.println("Source ");
                ChessPosition source = UI.readChessPosition(sc);

                // Mostra movimentos possíveis
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                System.out.println();

                // Lê a posição de destino
                System.out.println("Target: ");
                ChessPosition target = UI.readChessPosition(sc);

                // Executa o movimento
                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }
}
