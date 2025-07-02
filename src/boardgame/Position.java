package boardgame;

// Classe que representa uma posição no tabuleiro através de linha e coluna
public class Position {
    // Atributos para armazenar a linha e coluna da posição
    private int row;
    private int column;

    // Construtor vazio
    public Position(){
    }

    // Construtor que recebe linha e coluna como parâmetros
    public Position(int row, int column){
        this.row = row;
        this.column = column;
    }

    // Métodos getters e setters para acessar e modificar a linha
    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    // Métodos getters e setters para acessar e modificar a coluna
    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    // Método para atualizar os valores de linha e coluna de uma vez
    public void setValues(int row, int column){
        this.row = row;
        this.column = column;
    }

    // Sobrescrita do método toString para representação textual da posição
    @Override
    public String toString() {
        return row + "," + column;
    }
}