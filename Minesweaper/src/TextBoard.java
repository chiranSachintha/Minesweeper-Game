public class TextBoard extends Board {

    // Maximum length of string to hold column/row numbers
    private int colLength, rowLength;
    // Printable versions of column and row numbers
    private String[] colNums, rowNums;
    // Spacing for column-header lines
    private String spacer;

    public TextBoard(int width, int height, int numMines){
        super(width, height, numMines);

        colLength = Integer.toString(width-1).length();
        rowLength = Integer.toString(height-1).length();
        colNums = new String[width];
        rowNums = new String[height];

        for (int i = 0; i < width; i++) {
            StringBuffer col = new StringBuffer(Integer.toString(i));
            while (col.length() < colLength) {
                col.insert(0, ' ');
            }
            colNums[i] = col.toString();
        }
        StringBuffer spaces = new StringBuffer();
        for (int i = 0; i < rowLength + 2; i++) {
            spaces.append(' ');
        }
        spacer = spaces.toString();

        for (int i = 0; i < height; i++) {
            StringBuffer row = new StringBuffer(Integer.toString(i));
            while (row.length() <= rowLength) {
                row.insert(0, ' ');
            }
            row.append(' ');
            rowNums[i] = row.toString();
        }


    }
    public void draw(){
        System.out.println();

        for (int i = 0; i < colLength; i++) {
            System.out.print(spacer);
            for (int j = 0; j < width; j++) {
                System.out.print(colNums[j].charAt(i));
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < height; i++) {
            System.out.print(rowNums[i]);
            // Print something appropriate for the cell
            for (int j = 0; j < width; j++) {
                switch (board[j][i]) {
                    case UNKNOWN:
                        System.out.print("#");
                        break;
                    case MARKED:
                        System.out.print("X");
                        break;
                    case MINE:
                        System.out.print("*");
                        break;
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                            System.out.print(board[j][i]);
                            break;
                        case 0:
                            System.out.print(".");
                            break;
                }
            }
            System.out.println(rowNums[i]);
        }
        System.out.println();
        for (int i = 0; i < colLength; i++) {
            System.out.print(spacer);
            for (int j = 0; j < width; j++) {
                System.out.print(colNums[j].charAt(i));
            }
            System.out.println();
        }


        System.out.println();
        System.out.println("Mines remaining: " + (getMines() - getMarked()));
        System.out.println();

    }
}