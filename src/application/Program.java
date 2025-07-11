package application;


import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Classe principal que executa o jogo
public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<ChessPiece> captured = new ArrayList<>();

        // Inicia uma nova partida
        ChessMatch chessMatch = new ChessMatch();

        // Loop principal do jogo
        while (!chessMatch.getCheckmate()) {
            try {
                // Limpa a tela e mostra o estado atual
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
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

                // Adiciona à lista 'captured' qualquer peça que tenha sido capturada durante o movimento
                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if (chessMatch.getPromotedPiece() != null) {
                    System.out.println("Enter the piece to promote to (B/N/R/Q): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")) {
                        System.out.println("Invalid piece type. Valid values are: B, N, R, Q.");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
    }
}
