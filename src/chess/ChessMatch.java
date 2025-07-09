package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Classe que representa uma partida de xadrez
public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    // Referência ao tabuleiro da partida
    private Board board;
    private boolean check;
    private boolean checkmate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promotedPiece;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturesPieces = new ArrayList<>();

    // Construtor que inicializa uma nova partida
    public ChessMatch() {
        // Cria um novo tabuleiro 8x8 (padrão do xadrez)
        board = new Board(8, 8);
        //Organiza as peças na partida
        turn = 1; //incia o turno com 1
        currentPlayer = Color.WHITE; //primeiro a jogar = peça branca
        initialSetup();
    }

    // Método que retorna o número do turno atual
    public int getTurn() {
        return turn;
    }

    // Método que retorna a cor do jogador atual
    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckmate() {
        return checkmate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromotedPiece() {
        return promotedPiece;
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

    // Método que retorna uma matriz com os movimentos possíveis para uma peça
    // Recebe a posição de origem no formato do xadrez
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    // Método que executa um movimento de xadrez a partir de uma posição fonte para uma posição destino
    // Retorna a peça capturada, se houver
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put youself in check");
        }

        ChessPiece movedPiece = (ChessPiece) board.piece(target);

        //#specialmove promotion
        promotedPiece = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
                promotedPiece = (ChessPiece) board.piece(target);
                promotedPiece = replacePromotedPiece("Q");
            }
        }

        check = testCheck(opponent(currentPlayer)) ? true : false;

        if (testCheckmate(opponent(currentPlayer))) {
            checkmate = true;
        } else {
            nextTurn();
        }

        //specialmove en passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 ||
                target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type) {
        if (promotedPiece == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }
        if (!type.equals("Q") && !type.equals("B") && !type.equals("N") && !type.equals("R")) {
            return promotedPiece;
        }
        Position pos = promotedPiece.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        piecesOnTheBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promotedPiece.getColor());
        board.placeNewPiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color) {
        if (type.equals("Q")) {
            return new Queen(board, color);
        } else if (type.equals("B")) {
            return new Bishop(board, color);
        } else if (type.equals("N")) {
            return new Knight(board, color);
        } else if (type.equals("R")) {
            return new Rook(board, color);
        } else {
            return promotedPiece;
        }
    }

    // Método auxiliar que realiza a movimentação da peça no tabuleiro
    // Remove a peça da posição origem, captura a peça na posição destino (se existir)
    // e coloca a peça movida na posição destino
    private Piece makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placeNewPiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturesPieces.add(capturedPiece);
        }

        //specialmove castiling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placeNewPiece(rook, targetT);
            rook.increaseMoveCount();
        }

        //specialmove castiling Queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
            board.placeNewPiece(rook, targetT);
            rook.increaseMoveCount();
        }

        //specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                capturesPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    // Método para desfazer o movimento caso o jogador se ponha em cheque
    private void undoMove(Position source, Position target, Piece capturedPiece) {
        ChessPiece p = (ChessPiece) board.removePiece(target);
        p.decreaseMoveCount();
        board.placeNewPiece(p, source);
        if (capturedPiece != null) {
            board.placeNewPiece(capturedPiece, target);
            capturesPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        //specialmove castiling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) board.removePiece(target);
            board.placeNewPiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        //specialmove castiling Queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) board.removePiece(targetT);
            board.placeNewPiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        //specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturesPieces == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) board.removePiece(target);
                Position pawnPosition;
                if (p.getColor() == Color.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                board.placeNewPiece(pawn, pawnPosition);
            }
        }
    }

    // Método que valida se existe uma peça na posição informada
    // Lança uma exceção caso não exista peça na posição informada
    private void validateSourcePosition(Position position) {
        if (!board.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) {
            throw new ChessException("It's not the current player's turn");
        }
        if (!board.piece(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible move for the chosen piece");
        }
    }

    // Método que valida se a posição de destino é válida para a peça selecionada
    // Lança uma exceção se o movimento não for possível
    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to the target position");
        }
    }


    // Método que passa a vez para o próximo jogador
    // Incrementa o turno e alterna entre as cores branca e preta
    private void nextTurn() {
        turn++;
        currentPlayer = currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color) {
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private boolean testCheckmate(Color color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Piece> list = piecesOnTheBoard.stream().
                filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < board.getRows(); i++) {
                for (int j = 0; j < board.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Piece capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // Método auxiliar para facilitar a colocação de uma nova peça no tabuleiro
    // Recebe a coluna, linha e a peça a ser posicionada
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        board.placeNewPiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private ChessPiece king(Color color) {
        List<Piece> list = piecesOnTheBoard.stream().
                filter(x -> ((ChessPiece) x).getColor() == color).collect(Collectors.toList());
        for (Piece p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    private boolean testCheck(Color color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Piece> opponnentPieces = piecesOnTheBoard.stream().
                filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece p : opponnentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    // Cria um setup inicial colocando as peças em suas respectivas posições no tabuleiro
    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 2, new Rook(board, Color.WHITE));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}