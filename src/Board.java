import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;

public class Board {
    public static int[][] valBoard;
    //public int[][] valBoard;
    static JPanel[][] boardPanels;
    static JLabel[][] boardNumbers;

    public Board(int[][] valBoard, JPanel[][] boardPanels, JLabel[][] boardNumbers) throws FileNotFoundException {
        Board.valBoard = valBoard;
        Board.boardNumbers = boardNumbers;
        Board.boardPanels = boardPanels;
    }

    private static int spawnValues() {
        var test = Math.random();
        if(test < 0.2) {
            return 4;
        }
        else {
            return 2;
        }
    }

    public static void insertStartValues() {
        boolean inserted = false;
        while(!inserted) {
            int row = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            int col = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            if(Application.valBoard[row][col] == 0) {
                int val = spawnValues();
                valBoard[row][col] = val;
                setBrickColor(val, row, col);
                System.out.println("spawn at ("+ row + "," + col + ") " + val);
                inserted = true;
            }
        }
    }

    public static void setBrickColor(int value, int i, int j) {
        switch (value) {
            case 0:
                boardNumbers[i][j].setText(" ");
                boardPanels[i][j].add(boardNumbers[i][j]);
                boardPanels[i][j].setBackground(Color.decode("#cdc1b3"));
                break;

            case 2:
                changeBlock(i, j, "2", 110, "#7b6f60", "#eee4da");
                break;
            case 4:
                changeBlock(i, j, "4", 110, "#7b6f60", "#ece0c8");
                break;
            case 8:
                changeBlock(i,j,"8", 110, "#fff9f7", "#f2b179");
                break;
            case 16:
                changeBlock(i,j, "16", 110, "#fff9f7", "#f59563");
                break;
            case 32:
                changeBlock(i,j,"32", 110, "#fff9f7", "#f47d60");
                break;
            case 64:
                changeBlock(i,j,"64", 110, "#fff9f7", "#f55d3c");
                break;
            case 128:
                changeBlock(i,j,"128", 90, "#fff9f7", "#efcd72");
                break;
            case 256:
                changeBlock(i,j,"256", 90, "#fff9f7", "#eccc64");
                break;
            case 512:
                changeBlock(i,j,"512", 90, "#fff9f7", "#ecc850");
                break;
            case 1024:
                changeBlock(i,j,"1024", 70, "#fff9f7", "#edc53f");
                break;
            case 2048:
                changeBlock(i,j,"2048", 70, "#fff9f7", "#ecc135");
                break;
        }
    }

    //TODO
    //Fix number center
    public static void changeBlock(int i, int j, String number, int fontSize, String fontColor, String panelColor) {
        boardNumbers[i][j].setText(number);
        boardPanels[i][j].add(boardNumbers[i][j]);
        boardNumbers[i][j].setFont(new Font("Verdana", Font.BOLD, fontSize));
        boardNumbers[i][j].setForeground(Color.decode(fontColor));
        boardPanels[i][j].setBackground(Color.decode(panelColor));
    }
}
