package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

// Classe que representa uma partida de xadrez
public class ChessMatch {
    // Referência ao tabuleiro da partida
    private Board board;

    // Construtor que inicializa uma nova partida
    public ChessMatch() {
        // Cria um novo tabuleiro 8x8 (padrão do xadrez)
        board = new Board(8, 8);
        //Organiza as peças na partida
        initialSetup();
    }

    // Método que retorna a matriz de peças da partida
    public ChessPiece[][] getPieces() {
        // Cria uma matriz com as dimensões do tabuleiro
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        // Percorre todo o tabuleiro
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                // Converte cada Piece para ChessPiece e adiciona na matriz
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        // Retorna a matriz de peças
        return mat;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placeNewPiece(piece, new ChessPosition(column, row).toPosition());
    }

    // Cria um setup inicial colocando as peças em sua respectiva posição no tabuleiro
    private void initialSetup() {
        placeNewPiece('b', 6, new Rook(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
    }

}