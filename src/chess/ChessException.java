package chess;

import boardgame.BoardException;

// Classe que representa uma exceção específica para erros relacionados ao xadrez
// Herda de BoardException para manter a hierarquia de exceções do jogo
public class ChessException extends BoardException {
    // Número de série para serialização
    private static final long serialVersionUID = 1L;

    // Construtor que recebe uma mensagem de erro e a repassa para a classe pai
    public ChessException(String message) {
        super(message);
    }
}
