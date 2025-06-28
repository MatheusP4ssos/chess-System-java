package application;

import chess.ChessMatch;
import chess.ChessPiece;

public class UI {
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
        // Se não houver peça na posição (null)
        if (piece == null) {
            // Imprime um traço representando espaço vazio
            System.out.print("-");
        }
        else {
            // Se houver uma peça, imprime a representação dela
            // (será definida pelo método toString() da peça)
            System.out.print(piece);
        }
        // Adiciona um espaço após cada peça para formatação
        System.out.print(" ");

    }
}


