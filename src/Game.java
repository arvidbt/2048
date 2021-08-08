import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends JFrame implements KeyListener{

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

    public int[][] valBoard = new int[4][4];

    private final Border border = new LineBorder(Color.decode("#bbada0"), 12, false);

    public Game() throws FileNotFoundException {
        this.setSize(1000,1360);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("2048");
        this.setResizable(false);
        this.setLayout(null);
        this.setBackground(Color.decode("#fbf8ef"));

        board.setLayout(new GridLayout(4,4));
        board.setBounds(55,340,880,880);
        topBoard.setBounds(10,20,980, 310);
        new Board(valBoard, boardPanels, boardNumbers);
        new Score(highScoreLabelPoints, scoreLabelPoints);
        new Application();
        startGame();

        this.setVisible(true);
    }

    private void startGame() throws FileNotFoundException {
        Score.loadHighScore();
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
        info.setBounds(50, 210, 600, 100);
        info.add(infoLabel);
        infoLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        infoLabel.setForeground(Color.decode("#726b63"));
        topBoard.add(info);

        topBoard.add(newGameButton);
        newGameButton.setBackground(Color.decode("#8e7862"));
        newGameButton.add(newGameButtonLabel);
        newGameButton.setFocusable(false);
        newGameButton.setBounds(715, 190, 200,75);
        newGameButton.addActionListener(e-> {
            try {
                newGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
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

    private int spawnValues() {
        var test = Math.random();
        if(test < 0.2) {
            return 4;
        }
        else {
            return 2;
        }
    }

    public void insertStartValues() {
        boolean inserted = false;
        while(!inserted) {
            int row = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            int col = ThreadLocalRandom.current().nextInt(0, 3 + 1);
            if(valBoard[row][col] == 0) {
                int val = spawnValues();
                valBoard[row][col] = val;
                Board.setBrickColor(val, row, col);
                System.out.println("spawn at ("+ row + "," + col + ") " + val);
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

    private void newGame() throws IOException {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                Board.setBrickColor(0,i,j);
                valBoard[i][j] = 0;
            }
        }
        Score.setHighScore();
        Score.resetScore();
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
        if(Application.isGameOver()) {
            if (key == KeyEvent.VK_RIGHT) {
                Application.moveRight();
            } else if (key == KeyEvent.VK_LEFT) {
                Application.moveLeft();
            } else if (key == KeyEvent.VK_UP) {
                Application.moveUp();
            } else if (key == KeyEvent.VK_DOWN) {
                Application.moveDown();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {}
}
