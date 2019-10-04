import java.util.Random;

public abstract class Board {

    //size of the board
    protected int width;
    protected int height;

    //number of mines on the board
    protected int numMines;

    //number of cells marked
    protected int numMarked;

    //number of cells to find
    protected int numUnknown;

    // places of the mines are hidden
    protected boolean[][] mines;

    //present state of the board
    protected int[][] board;
    // constant to identify the cell contents
    public static final int UNKNOWN = -1;
    public static final int MARKED = -2;
    public static final int MINE = -3;

    // method to get width of the board
    public int getWidth(){
        return width;
    }
    // method to get height of the board
    public int getHeight() {
        return height;
    }
    // method to get number of mines in the board
    public int getMines(){
        return numMines;
    }
    // get number of marked cells
    public int getMarked(){
        return numMarked;
    }
    // get number of unmarked cells
    public int getUnknown(){
        return numUnknown;
    }

    //constructor
    public Board(int width, int height, int numMines){
        this.width = width;
        this.height = height;
        this.numMines = numMines;
        this.numMarked =0;
        this.numUnknown = width*height;

        mines = new boolean[width][height];
        board = new int[width][height];

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height;j++){
                mines[i][j] = false;
                board[i][j] = UNKNOWN;
            }
        }
        int cells = width*height;
        int temp = 0;
        Random rand = new Random();

        while(temp < numMines){
            int cell = rand.nextInt();
            //cell = (cell < 0 ? -cell : cell)%cells;
            if (cell < 0){
                cell = (-cell) % cells;
            }
            else{
                cell = cell % cells;
            }
            if(!mines[cell%width][cell%height]){
                mines[cell%width][cell%height] = true;
                temp++;
            }
            for(int index1 = 0; index1 < cells; index1++){
                int index2 = index1 +rand.nextInt(cells -  index1);
                if(index1 != index2){
                    int row1 = index1 / width;
                    int col1 = index1 % width;
                    boolean valueCell1 = mines[row1][col1];

                    int row2 = index2 / width;
                    int col2 = index2 % width;
                    boolean valueCell2 = mines[row2][col2];
                    mines[row1][col1] = valueCell2;
                    mines[row2][col2] = valueCell1;
                }
            }

        }


    }
    public abstract void draw();
    public int reveal(int x, int y){
        switch (board[x][y]){
            case MARKED:
                numMarked--;
            case UNKNOWN:
                numUnknown--;
                if(mines[x][y]){
                    board[x][y] = MINE;
                }
                else {
                    board[x][y] = closeMines(x,y);
                }
                break;
        }
        return board[x][y];
    }
    public void revealMore(int x, int y){
        int minx;
        int miny;
        int maxx;
        int maxy;
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (!mines[i][j] && board[i][j] == UNKNOWN) {
                    reveal(i, j);
                    if (board[i][j] == 0) {
                        // Call ourself recursively
                        revealMore(i, j);
                    }
                }
            }
        }

    }
    public boolean mark(int x, int y){
        if ((numMines - numMarked)>0 && board[x][y] == UNKNOWN){
            board[x][y] = MARKED;
            numMarked++;
            return true;
        }
        else{
            return false;
        }
    }
    public boolean unmark(int x, int y){
        if(board[x][y] == MARKED){
            board[x][y] = UNKNOWN;
            numMarked--;
            return true;
        }
        else{
            return false;
        }

    }

    private int closeMines(int x, int y){
        int minx;
        int miny;
        int maxx;
        int maxy;
        int result = 0;
        minx = (x <= 0 ? 0 : x - 1);
        miny = (y <= 0 ? 0 : y - 1);
        maxx = (x >= width - 1 ? width : x + 2);
        maxy = (y >= height - 1 ? height : y + 2);
        for (int i = minx; i < maxx; i++) {
            for (int j = miny; j < maxy; j++) {
                if (mines[i][j]) {
                    result++;
                }
            }
        }
        return result;
    }
}
