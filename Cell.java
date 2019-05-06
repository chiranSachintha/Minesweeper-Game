public class Cell {

    private int row;
    private int column;
    private boolean isBomb;
    private int number;
    private boolean isExplosed = false;
    private boolean isGuess = false;

    public Cell(int row, int column){
        this.row = row;
        this.column = column;
        isBomb = false;
        number = 0;
    }

    public void setRowAndColumn(int r, int c){
        this.row = r;
        this.column = c;

    }
    public void setBomb(boolean bomb){
        this.isBomb = bomb;
        number = -1;
    }

    public void incrementNumber(){
        number++;
    }

    public boolean isBomb(){
        return isBomb;
    }

    public boolean isBlank(){
        return number == 0;
    }

    public boolean isExplosed() {
        return isExplosed;
    }
    public int getRow(){
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getNumber(){
        return number;
    }

    public boolean flip(){
        isGuess = true;
        return !isBomb;
    }
    public  boolean isGuess(){
        return isGuess;
    }
}
