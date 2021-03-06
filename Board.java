import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Board {

    private int nRows;
    private int nColumns;
    private int nBombs;
    private Cell[][] cells;
    private Cell[] bombs;
    private int numOfUnexplosedRemaining;

    public Board(int r, int c, int b){
        this.nRows = r;
        this.nColumns = c;
        this.nBombs = b;

        initializeBoard();
        shuffleBoard();
        setNumInCells();

        numOfUnexplosedRemaining = nRows*nColumns - nBombs;
    }

    public void initializeBoard(){

        cells = new Cell[nRows][nColumns];
        bombs = new Cell[nBombs];

        for(int i = 0; i < nRows; i++){
            for (int j =0; j<nColumns; j++){
                cells[i][j] = new Cell(i,j);
            }
        }

        for(int i = 0; i <nBombs; i++){
            int r = i / nColumns;
            int c = i % nColumns;
            bombs[i] = cells[r][c];
            bombs[i].setBomb(true);

        }
    }

    public void shuffleBoard(){
        int nCells = nRows * nColumns;
        Random random = new Random();

        for(int index1 = 0; index1 < nCells; index1++){
            int index2 = index1 + random.nextInt(nCells-index1);

            if(index1 != index2){
                int row1 = index1 / nColumns;
                int col1 = index1 % nColumns;
                Cell cell1 = cells[row1][col1];

                int row2 = index2 / nColumns;
                int col2 = index2 % nColumns;
                Cell cell2 = cells[row2][col2];

                cells[row1][col1] = cell2;
                cell2.setRowAndColumn(row1,col1);
                cells[row2][col2] = cell1;
                cell1.setRowAndColumn(row2,col2);
            }
        }
    }

    private boolean inBound(int row, int column){
        return 0 <= row && row < nRows && 0 <= column && column <nColumns;

    }
    private void setNumInCells(){

        int[][] deltas = {{-1,-1},{-1,0},{-1,1},
                        {0,-1},{0,1},
                        {1,-1},{1,0},{1,1}
        };
        for(Cell bomb:bombs){
            int row = bomb.getRow();
            int col = bomb.getColumn();

            for(int[] delta : deltas ){
                int r = row + delta[0];
                int c = col + delta[1];
                if (inBound(r,c)){
                    cells[r][c].incrementNumber();
                }
            }
        }

    }

    public Cell[][] getCells() {
        return cells;
    }

    public boolean flipCell(Cell cell){
        if(!cell.isBomb() && !cell.isGuess()){
            cell.flip();
            numOfUnexplosedRemaining--;
            return true;
        }
        return false;
    }
    public void expandBlank(Cell cell){
        int[][] deltas = {{-1,-1},{-1,0},{-1,1},
                {0,-1},{0,1},
                {1,-1},{1,0},{1,1}
        };

        Queue<Cell> toExplore = new LinkedList<Cell>();
        toExplore.add(cell);
        while (!toExplore.isEmpty()){
            Cell currentCell = toExplore.remove();
            for(int[] delta : deltas){
                int r = cell.getRow() + delta[0];
                int c =  cell.getColumn() + delta[1];

                if(inBound(r,c)){
                    Cell neighbour = cells[r][c];
                    if(neighbour.isBlank() && flipCell(neighbour) ){
                        toExplore.add(neighbour);

                    }
                }
            }
        }


    }
    public int getNumOfUnexplosedRemaining(){
        return numOfUnexplosedRemaining;
    }
}
