package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";


    // Método que recebe a matriz de peças e imprime o tabuleiro
    public static void printBoard(ChessPiece[][] pieces) {
        // Loop para percorrer as linhas do tabuleiro (8 até 1)
        for (int i = 0; i < pieces.length; i++) {
            // Imprime o número da linha (8 - i para começar do 8 e ir até 1)
            System.out.print((8 - i) + " ");

            // Loop para percorrer as colunas do tabuleiro (a até h)
            for (int j = 0; j < pieces.length; j++) {
                // Imprime cada peça (ou espaço vazio) do tabuleiro
                printPiece(pieces[i][j]);
            }
            // Pula para próxima linha após imprimir todas as colunas
            System.out.println();
        }
        // Imprime as letras das colunas na parte inferior do tabuleiro
        System.out.println("  a b c d e f g h");
    }

    // Método auxiliar para imprimir uma única peça
    private static void printPiece(ChessPiece piece) {
        if (piece == null) {
            System.out.print("-");
        }
        else {
            if (piece.getColor() == Color.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
        }
    }



