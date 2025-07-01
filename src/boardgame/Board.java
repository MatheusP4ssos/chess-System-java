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
        if (rows <= 0 || columns <= 0) {
            //Especifica que o tabuleiro criado deve ter pelo menos uma linha e uma coluna
            throw new BoardException("Error creating board: there must be at least 1 row and 1 column");
        }
        this.rows = rows;
        this.columns = columns;
        // Inicializa a matriz de peças com as dimensões fornecidas
        pieces = new Piece[rows][columns];
    }

    // Métodos getters e setters para linhas
    public int getRows() {
        return rows;
    }


    // Métodos getters e setters para colunas
    public int getColumns() {
        return columns;
    }


    // Retorna a peça em uma posição específica usando linha e coluna
    public Piece piece(int row, int column) {
        //Checa se a linha e coluna informadas existem no tabuleiro
        if (!positionExists(row, column)) {
            throw new BoardException("Error getting piece: position does not exist");
        }
        return pieces[row][column];
    }

    // Sobrecarga do método piece que aceita um objeto Position
    public Piece piece(Position position) {
        //Checa se a posição informada existe no tabuleiro
        if (!positionExists(position)) {
            throw new BoardException("Error getting piece: position does not exist");
        }
        return pieces[position.getRow()][position.getColumn()];
    }

    public void placeNewPiece(Piece piece, Position position) {
        if (thereIsAPiece(position))
            throw new BoardException("There is already a piece in that position " + position);
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    public Piece removePiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        if (piece(position) == null) {
            return null;
        }
        Piece aux = piece(position);
        pieces[position.getRow()][position.getColumn()] = null;
        aux.position = null;
        return aux;
    }


    //Método para verificar se a posição existe no tabuleiro
    private boolean positionExists(int row, int column) {
        // linha solicitada precisa ser menor que a quantidade de linhas do tabuleiro e maior ou igual a 0
        // coluna solicitada precisa ser menor que a quantidade de colunas do tabuleiro e maior ou igual a 0
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    // Caso as condições acimas sejam cumpridas ele retorna uma linha e coluna dentro do tabuleiro
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    //Caso a posição solicitada seja diferente de null ele retorna true
    public boolean thereIsAPiece(Position position) {
        if (!positionExists(position)) {
            throw new BoardException("Position not on the board");
        }
        return piece(position) != null;
    }
}