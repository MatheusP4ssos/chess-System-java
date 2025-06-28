package boardgame;

// Classe que representa o tabuleiro do jogo
public class Board {
    // Atributos para número de linhas e colunas do tabuleiro
    private int rows;
    private int columns;

    // Matriz de peças que representa o tabuleiro
    private Piece[][] pieces;

    // Construtor que inicializa o tabuleiro com dimensões específicas
    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        // Inicializa a matriz de peças com as dimensões fornecidas
        pieces = new Piece[rows][columns];
    }

    // Métodos getters e setters para linhas
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    // Métodos getters e setters para colunas
    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    // Retorna a peça em uma posição específica usando linha e coluna
    public Piece piece(int row, int column) {
        return pieces[row][column];
    }

    // Sobrecarga do método piece que aceita um objeto Position
    public Piece piece(Position position){
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placePiece(Piece piece, Position position){
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
}