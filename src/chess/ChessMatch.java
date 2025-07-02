package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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

    // Método que executa um movimento de xadrez a partir de uma posição fonte para uma posição destino
    // Retorna a peça capturada, se houver
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        Piece capturedPiece = makeMove(source, target);
        return (ChessPiece) capturedPiece;
    }

    // Método auxiliar que realiza a movimentação da peça no tabuleiro
// Remove a peça da posição origem, captura a peça na posição destino (se existir)
// e coloca a peça movida na posição destino
    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placeNewPiece(p, target);
        return capturedPiece;
    }

     // Método que valida se existe uma peça na posição de origem
    // Lança uma exceção caso não exista peça na posição informada
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
    }

     // Método auxiliar para facilitar a colocação de uma nova peça no tabuleiro
    // Recebe a coluna, linha e a peça a ser posicionada
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placeNewPiece(piece, new ChessPosition(column, row).toPosition());
    }

    // Cria um setup inicial colocando as peças em suas respectivas posições no tabuleiro
    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }
}