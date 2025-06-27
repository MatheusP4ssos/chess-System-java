package boardgame;

// Classe que representa uma peça genérica do jogo
public class Piece {

    // Posição da peça no tabuleiro (protected para acesso nas subclasses)
    protected Position position;
    // Referência ao tabuleiro onde a peça está
    private Board board;

    // Construtor que recebe o tabuleiro como parâmetro
    public Piece(Board board) {
        this.board = board;
        // Inicialmente a peça não tem posição (null)
        position = null;
    }

    // Método protegido para acessar o tabuleiro (apenas classes do mesmo pacote e subclasses)
    protected Board getBoard() {
        return board;
    }
}