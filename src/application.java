import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

public class application extends JFrame implements KeyListener{

    public JPanel board = new JPanel();
    public JPanel topBoard = new JPanel();
    public JPanel score = new JPanel();
    public JPanel highScore = new JPanel();
    public JButton newGameButton = new JButton();
    public JPanel[][] boardPanels = new JPanel[4][4];
    public JLabel[][] boardNumbers = new JLabel[4][4];
    public int highScoreINT = 0;
    public int currentINT = 0;

    private int[][] valBoard = new int[4][4];

    private boolean gameOver = false;
    private final Border border = new LineBorder(Color.decode("#bbada0"), 12, false);

    application() {
        this.setSize(1000,1360);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("2048");
        this.setResizable(false);
        this.setLayout(null);
        this.setBackground(Color.decode("#fbf8ef"));

        board.setLayout(new GridLayout(4,4));
        board.setBounds(55,340,880,880);
        topBoard.setBounds(10,20,980, 310);

        startGame();

        this.setVisible(true);
    }


    private void startGame() {
        initScoreBoard();
        initBoard();
    }

    private void initScoreBoard() {

        JPanel title = new JPanel();
        JPanel info = new JPanel();
        JLabel titleLabel = new JLabel("2048");
        JLabel scoreLabel = new JLabel("Score:");
        JLabel highScoreLabel = new JLabel("Best:");
        JLabel newGameButtonLabel = new JLabel("New Game");
        JLabel infoLabel = new JLabel("Join the numbers and get to the 2048 tile!");

        topBoard.setLayout(null);
        topBoard.setBackground(Color.decode("#fbf8ef"));

        info.setBackground(Color.decode("#fbf8ef"));
        info.setBounds(50, 200, 600, 100);
        info.add(infoLabel);
        infoLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        infoLabel.setForeground(Color.decode("#726b63"));
        topBoard.add(info);

        title.setBackground(Color.decode("#fbf8ef"));
        title.setBounds(-40, 40, 500, 200);
        titleLabel.setFont(new Font("Verdana",Font.BOLD,120));
        titleLabel.setForeground(Color.decode("#726b63"));
        title.add(titleLabel);
        topBoard.add(title);

        topBoard.add(newGameButton);
        newGameButton.setBackground(Color.decode("#8e7862"));
        newGameButton.add(newGameButtonLabel);
        newGameButton.setFocusable(false);
        newGameButton.setBounds(780, 175, 200,75);
        newGameButton.addActionListener(e->newGame());
        newGameButtonLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        newGameButtonLabel.setForeground(Color.white);

        topBoard.add(score);
        score.setBackground(Color.decode("#bcafa0"));
        score.setBounds(780,40, 200,125);
        score.add(scoreLabel);
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        scoreLabel.setForeground(Color.white);


        topBoard.add(highScore);
        highScore.setBackground(Color.decode("#bcafa0"));
        highScore.setBounds(560, 40, 200,125);
        highScore.add(highScoreLabel);
        highScoreLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        highScoreLabel.setForeground(Color.white);

        this.add(topBoard);
    }

    private int spawnValues() {
        var test = Math.random();
        if(test < 0.2) {
            return 4;
        }
        else {
            return 2;
        }
    }

    private void insertStartValues() {
        boolean inserted = false;
        while(!inserted) {
            int row = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            int col = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            if(valBoard[row][col] == 0) {
                int val = spawnValues();
                valBoard[row][col] = val;
                setBrickColor( val, row, col);
                inserted = true;
            }

        }
    }

    private void initBoard() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                boardNumbers[i][j] = new JLabel();
                boardPanels[i][j] = new JPanel();
                boardPanels[i][j].setBackground(Color.decode("#cdc1b3"));
                boardPanels[i][j].setBorder(border);
                board.add(boardPanels[i][j]);
                valBoard[i][j] = 0;
            }
        }
        insertStartValues();
        insertStartValues();
        board.setBackground(Color.decode("#fbf8ef"));
        this.add(board);
        runGame();
    }

    private void printBoard() {

    }

    private void newGame() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                setBrickColor(0,i,j);
                valBoard[i][j] = 0;

            }
        }
        insertStartValues();
        insertStartValues();
        runGame();
    }

    private void runGame() {
        addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_RIGHT) {
            insertStartValues();
        }
        else if(key == KeyEvent.VK_LEFT) {
            insertStartValues();
        }
        else if(key == KeyEvent.VK_UP) {
            insertStartValues();
        }
        else if(key == KeyEvent.VK_DOWN) {
            insertStartValues();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    private boolean isGameOver() {
        boolean isGameOver = false;
        int fullBoard = 0;
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(valBoard[i][j] != 0) {
                    fullBoard++;
                }

                if(valBoard[i][j] == 2048) {
                    isGameOver = true;
                }
            }
            if(fullBoard == 16 && noMovesPossible()) {
                isGameOver = true;
            }
        }
        return isGameOver;
    }

    private boolean noMovesPossible() {
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

    public void setBrickColor(int value, int i, int j) {
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
                changeBlock(i, j, "4", 110, "#7a7169", "#ece0c8");
                break;

            case 8:
                changeBlock(i,j,"8", 110, "#7a7169", "#f2b179");
                break;
            case 16:
                changeBlock(i,j, "16", 110, "#7a7169", "#f59563");
                break;
            case 32:
                changeBlock(i,j,"32", 110, "#7a7169", "#f47d60");
                break;
            case 64:
                changeBlock(i,j,"64", 110, "#7a7169", "#f55d3c");
                break;
            case 128:
                changeBlock(i,j,"128", 110, "#7a7169", "#efcd72");
                break;
            case 256:
                changeBlock(i,j,"256", 110, "#7a7169", "#eccc64");
                break;
            case 512:
                changeBlock(i,j,"512", 110, "#7a7169", "#ecc850");
                break;
            case 1024:
                changeBlock(i,j,"1024", 110, "#7a7169", "#edc53f");
                break;
            case 2048:
                changeBlock(i,j,"2048", 110, "#7a7169", "#ecc135");
                break;
        }
    }

    public void changeBlock(int i, int j, String number, int fontSize, String fontColor, String panelColor) {
        boardNumbers[i][j].setText(number);
        boardPanels[i][j].add(boardNumbers[i][j]);
        boardNumbers[i][j].setFont(new Font("Verdana", Font.BOLD, fontSize));
        boardNumbers[i][j].setForeground(Color.decode(fontColor));
        boardPanels[i][j].setBackground(Color.decode(panelColor));
    }
}
