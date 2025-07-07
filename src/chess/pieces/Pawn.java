package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {
    public Pawn(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        if (getColor() == Color.WHITE) {
            // Movimento de 1 casa para frente
            p.setValues(position.getRow() - 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Movimento de 2 casas para frente, se for o primeiro movimento
            Position p2 = new Position(position.getRow() - 2, position.getColumn());
            if (getBoard().positionExists(p2) &&
                    !getBoard().thereIsAPiece(p2) &&
                    getBoard().positionExists(p) &&
                    !getBoard().thereIsAPiece(p) &&
                    getMoveCount() == 0) {
                mat[p2.getRow()][p2.getColumn()] = true;
            }

            // Captura à esquerda
            p.setValues(position.getRow() - 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Captura à direita
            p.setValues(position.getRow() - 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        } else {
            // Movimento de 1 casa para frente
            p.setValues(position.getRow() + 1, position.getColumn());
            if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Movimento de 2 casas para frente, se for o primeiro movimento
            Position p2 = new Position(position.getRow() + 2, position.getColumn());
            if (getBoard().positionExists(p2) &&
                    !getBoard().thereIsAPiece(p2) &&
                    getBoard().positionExists(p) &&
                    !getBoard().thereIsAPiece(p) &&
                    getMoveCount() == 0) {
                mat[p2.getRow()][p2.getColumn()] = true;
            }

            // Captura à esquerda
            p.setValues(position.getRow() + 1, position.getColumn() - 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }

            // Captura à direita
            p.setValues(position.getRow() + 1, position.getColumn() + 1);
            if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
