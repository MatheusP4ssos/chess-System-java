package chess;

import boardgame.Board;
import boardgame.Piece;

// Classe que representa uma peça de xadrez, estendendo a classe genérica Piece
public class ChessPiece extends Piece {
    // Atributo que armazena a cor da peça (PRETO ou BRANCO)
    private Color color;

    // Construtor que recebe o tabuleiro e a cor da peça
    public ChessPiece(Board board, Color color) {
        // Chama o construtor da superclasse (Piece) passando o tabuleiro
        super(board);
        // Inicializa a cor da peça
        this.color = color;
    }

    // Método getter para acessar a cor da peça (sem setter para tornar imutável)
    public Color getColor() {
        return color;
    }
}