package boardgame;

// Classe que representa exceções específicas relacionadas ao tabuleiro do jogo
// Herda de RuntimeException para ser uma exceção não verificada
public class BoardException extends RuntimeException {

    // Identificador de versão para serialização da classe
    private static final long serialVersionUID = 1L;

    // Construtor que recebe uma mensagem de erro e a repassa para a superclasse
    public BoardException(String message) {
        super(message);
    }
}
