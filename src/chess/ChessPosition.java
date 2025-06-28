package chess;

import boardgame.Position;

// Classe responsável por representar uma posição no xadrez usando a notação algébrica (ex: a1, h8)
public class ChessPosition {
    // Representa a coluna no formato de letra (a até h)
    private char column;
    // Representa a linha no formato numérico (1 até 8)
    private int row;

    // Construtor que valida se a posição está dentro dos limites do tabuleiro
    public ChessPosition(char column, int row) {
        // Verifica se a coluna está entre 'a' e 'h' e se a linha está entre 1 e 8
        if (column < 'a' || column > 'h' || row < 1 || row > 8)
            throw new ChessException("Error instantiating ChessPosition. Valid values are: a1 to h8.");
        this.column = column;
        this.row = row;
    }

    // Retorna a coluna da posição
    public char getColumn() {
        return column;
    }

    // Retorna a linha da posição
    public int getRow() {
        return row;
    }

    // Converte a posição do xadrez (ex: a8) para posição da matriz (ex: 0,0)
    protected Position toPosition() {
        // 8 - row: inverte a numeração da linha (8 vira 0, 7 vira 1, etc)
        // column - 'a': converte a letra para número (a vira 0, b vira 1, etc)
        return new Position(8 - row, column - 'a');
    }

    // Converte uma posição da matriz para posição do xadrez
    protected static ChessPosition fromPosition(Position position) {
        // Processo inverso do método toPosition
        return new ChessPosition(
            (char) ('a' + position.getColumn()), // Converte número para letra
            8 - position.getRow() // Inverte a numeração da linha
        );
    }

    // Retorna a posição no formato string (ex: "a1")
    @Override
    public String toString() {
        return "" + column + row;
    }
}