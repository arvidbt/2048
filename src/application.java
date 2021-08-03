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

    public JLabel scoreLabelPoints = new JLabel("0");
    public JLabel highScoreLabelPoints = new JLabel("0");

    public JPanel scorePanel = new JPanel();
    public JPanel highScorePanel = new JPanel();

    public int highScoreINT = 0; // save to a textfile? And load from it?
    public int currentINT = 0; // update freq. and check if it exceeds highscore

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

        scorePanel.setBounds(725,90, 180, 65);
        scorePanel.setBackground(Color.decode("#bcafa0"));
        scoreLabelPoints.setFont(new Font("Verdana", Font.BOLD, 45));
        scoreLabelPoints.setForeground(Color.decode("#fbf8ef"));
        scorePanel.add(scoreLabelPoints);
        topBoard.add(scorePanel);

        highScorePanel.setBounds(505,90, 180, 65);
        highScorePanel.setBackground(Color.decode("#bcafa0"));
        highScoreLabelPoints.setFont(new Font("Verdana", Font.BOLD, 45));
        highScoreLabelPoints.setForeground(Color.decode("#fbf8ef"));
        highScorePanel.add(highScoreLabelPoints);
        topBoard.add(highScorePanel);

        info.setBackground(Color.decode("#fbf8ef"));
        info.setBounds(50, 200, 600, 100);
        info.add(infoLabel);
        infoLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        infoLabel.setForeground(Color.decode("#726b63"));
        topBoard.add(info);

        topBoard.add(newGameButton);
        newGameButton.setBackground(Color.decode("#8e7862"));
        newGameButton.add(newGameButtonLabel);
        newGameButton.setFocusable(false);
        newGameButton.setBounds(715, 185, 200,75);
        newGameButton.addActionListener(e->newGame());
        newGameButtonLabel.setFont(new Font("Verdana", Font.BOLD, 27));
        newGameButtonLabel.setForeground(Color.decode("#fbf8ef"));

        topBoard.add(score);
        score.setBackground(Color.decode("#bcafa0"));
        score.setBounds(715,50, 200,125);
        score.add(scoreLabel);
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        scoreLabel.setForeground(Color.decode("#fbf8ef"));


        topBoard.add(highScore);
        highScore.setBackground(Color.decode("#bcafa0"));
        highScore.setBounds(495, 50, 200,125);
        highScore.add(highScoreLabel);
        highScoreLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        highScoreLabel.setForeground(Color.decode("#fbf8ef"));

        title.setBackground(Color.decode("#fbf8ef"));
        title.setBounds(-40, 40, 500, 200);
        titleLabel.setFont(new Font("Verdana",Font.BOLD,120));
        titleLabel.setForeground(Color.decode("#726b63"));
        title.add(titleLabel);
        topBoard.add(title);

        this.add(topBoard);
    }

    private void rotateBoard90Degree() {
        int[]temp = new int[16];
        int index = 0;

        for(int row = 0; row < 4; row++)
        {
            for(int col = 3; col >= 0; col--)
            {
                temp[index] = valBoard[row][col];
                index++;
            }
        }
        index = 0;
        for(int row = 0; row < 4; row++) {
            for(int col = 0; col < 4; col++) {
                valBoard[row][col] = temp[index];
                index++;
            }
        }
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

    private void newGame() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                setBrickColor(0,i,j);
                valBoard[i][j] = 0;
            }
        }
        resetScore();
        removeKeyListener(this);
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
        if(isGameOver()) {
            if (key == KeyEvent.VK_RIGHT) {
                moveRight();
            } else if (key == KeyEvent.VK_LEFT) {
                moveLeft();
            } else if (key == KeyEvent.VK_UP) {
                moveUp();
            } else if (key == KeyEvent.VK_DOWN) {
                moveDown();
            }
        }
    }

    public void moveUp() {
        insertStartValues();
        setScore(2);
        setHighScore();
    }

    public void moveDown() {
        insertStartValues();
        setScore(2);
        setHighScore();
    }

    public void moveRight() {
        insertStartValues();
        setScore(2);
        setHighScore();
    }

    public void moveLeft() {
        insertStartValues();
        setScore(2);
        setHighScore();
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}

    private boolean isGameOver() {
        int fullBoard = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                if(valBoard[i][j] != 0) {
                    fullBoard++;
                }
            }
        }
        return fullBoard != 16;
    }

    private void resetScore() {
        this.currentINT = 0;
        String scoreStr = Integer.toString(currentINT);
        scoreLabelPoints.setText(scoreStr);
    }

    private void setScore(int score) {
        this.currentINT += score;
        String scoreStr = Integer.toString(currentINT);
        scoreLabelPoints.setText(scoreStr);
    }

    private void setHighScore() {
        if(this.currentINT > highScoreINT) {
            highScoreINT = currentINT;
            String scoreStr = Integer.toString(highScoreINT);
            highScoreLabelPoints.setText(scoreStr);
        }
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
                changeBlock(i,j,"128", 110, "#fff9f7", "#efcd72");
                break;
            case 256:
                changeBlock(i,j,"256", 110, "#fff9f7", "#eccc64");
                break;
            case 512:
                changeBlock(i,j,"512", 110, "#fff9f7", "#ecc850");
                break;
            case 1024:
                changeBlock(i,j,"1024", 110, "#fff9f7", "#edc53f");
                break;
            case 2048:
                changeBlock(i,j,"2048", 110, "#fff9f7", "#ecc135");
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
