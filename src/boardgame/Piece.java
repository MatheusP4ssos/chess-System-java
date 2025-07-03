package boardgame;

// Classe que representa uma peça genérica do jogo
public abstract class Piece {

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

    // Método abstrato que retorna os movimentos possíveis da peça
    public abstract boolean[][] possibleMoves();

    // Verifica se é possível mover a peça para uma posição específica
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    // Verifica se existe algum movimento possível para a peça
    public boolean isThereAnyPossibleMove() {
        boolean[][] mat = possibleMoves();
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat.length; j++) {
                if (mat[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}
