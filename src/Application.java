import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Application {
    public static int[][] valBoard;

    public Application() {
        Application.valBoard = Board.valBoard;
    }

    public static void moveUp() {
        boolean added = false;
        moverUp();
        for(int col=0;col<4;col++) {
            for(int row=0;row<4-1;row++) {
                if(valBoard[row][col]== valBoard[row+1][col]) {
                    valBoard[row][col] += valBoard[row + 1][col];
                    valBoard[row+1][col]=0;
                    added = true;
                    Board.setBrickColor(valBoard[row][col], row, col);
                    Board.setBrickColor(0, row+1, col);
                    Score.setScore(valBoard[row][col]);
                    break;
                }
            }
        }
        moverUp();
        if(added) {
            Board.insertStartValues();
        }
    }

    public static void moverUp() {
        for(int col=0;col<4;col++) {
            int count=0;
            for(int row=0;row<4;row++) {
                if(valBoard[row][col]!=0) {
                    valBoard[count][col]= valBoard[row][col];
                    Board.setBrickColor(valBoard[count][col], count, col);
                    if(count !=row) {
                        valBoard[row][col]=0;
                        Board.setBrickColor(0, row, col);
                    }
                    count++;
                }
            }
        }
    }

    public static void moveDown() {
        boolean added = false;
        moverDown();
        for(int col=0;col<4;col++) {
            for(int row=4-1;row>0;row--) {
                if(valBoard[row][col]== valBoard[row-1][col]) {
                    valBoard[row][col]+= valBoard[row-1][col];
                    valBoard[row-1][col]=0;
                    added = true;
                    Board.setBrickColor(valBoard[row][col], row, col);
                    Board.setBrickColor(0, row-1, col);
                    Score.setScore(valBoard[row][col]);
                    break;
                }
            }
        }
        moverDown();
        if(added) {
            Board.insertStartValues();
        }
    }

    public static void moverDown() {
        for(int col=0;col<4;col++) {
            int count=0;
            for(int row=4-1;row>=0;row--) {
                if(valBoard[row][col]!=0) {
                    valBoard[4-1-count][col]= valBoard[row][col];
                    Board.setBrickColor(valBoard[4-1-count][col], 4-1-count, col);
                    if(4-1-count!=row) {
                        valBoard[row][col]=0;
                        Board.setBrickColor(0, row, col);
                    }
                    count++;
                }
            }
        }
    }

    public static void moveLeft() {
        boolean added = false;
        moverLeft();
        for(int row=0;row<4;row++) {
            for(int col=0;col<4-1;col++) {
                if(valBoard[row][col]== valBoard[row][col+1]) {
                    valBoard[row][col]+= valBoard[row][col+1];
                    valBoard[row][col+1]=0;
                    added = true;
                    Score.setScore(valBoard[row][col]);
                    Board.setBrickColor(valBoard[row][col], row, col);
                    Board.setBrickColor(0, row, col+1);
                    break;
                }
            }
        }
        moverLeft();
        if(added) {
            Board.insertStartValues();
        }
    }

    public static void moverLeft() {
        for(int row=0;row<4;row++) {
            int count=0;
            for(int col=0;col<4;col++) {
                if(valBoard[row][col]!=0) {
                    valBoard[row][count]= valBoard[row][col];
                    Board.setBrickColor(valBoard[row][count], row, count);
                    if(count !=col) {
                        valBoard[row][col]=0;
                        Board.setBrickColor(0, row, col);
                    }
                    count++;
                }
            }
        }
    }

    public static void moveRight() {
        boolean added = false;
        moverRight();
        for(int row=0;row<4;row++) {
            for(int col=4-1;col>0;col--) {
                if(valBoard[row][col]== valBoard[row][col-1]) {
                    valBoard[row][col]+= valBoard[row][col-1];
                    valBoard[row][col-1]=0;
                    added = true;
                    Board.setBrickColor(valBoard[row][col], row, col);
                    Board.setBrickColor(0, row, col);
                    Score.setScore(valBoard[row][col]);
                    break;
                }
            }
        }
        moverRight();
        if(added) {
            Board.insertStartValues();
        }
    }

    public static void moverRight() {
        for(int row=0;row<4;row++) {
            int count=0;
            for(int col=4-1;col>=0;col--) {
                if(valBoard[row][col]!=0) {
                    valBoard[row][4-1-count]= valBoard[row][col];
                    Board.setBrickColor(valBoard[row][4-1-count], row, 4-1-count);
                    if(4-1-count!=col) {
                        Board.setBrickColor(0, row, col);
                        valBoard[row][col]=0;
                    }
                    count++;
                }
            }
        }
    }

    public static boolean boardIsFull() {
        int fullBoard = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                if(valBoard[i][j] != 0) {
                    fullBoard++;
                }
            }
        }
        return fullBoard == 16;
    }

    public boolean noMovesPossible() {
        boolean moveIsNotPossible = true;
        for(int row = 0; row < 4; row++) {
            for (int col = 3; col >= 0; col--) {
                if (row < 3) {
                    if (valBoard[row + 1][col] == valBoard[row][col]) {
                        moveIsNotPossible = false;
                    }
                }
                if (col < 3) {
                    if (valBoard[row][col + 1] == valBoard[row][col]) {
                        moveIsNotPossible = false;
                    }
                }
            }
        }
        return moveIsNotPossible;
    }
}
