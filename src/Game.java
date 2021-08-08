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

    private final Border border = new LineBorder(Color.decode("#bbada0"), 8, false);

    public Icon restart = new ImageIcon("src/icons/restartfigma.jpg");
    public ImageIcon frameIcon = new ImageIcon("src/icons/2048-icon.png");

    public Game() throws FileNotFoundException {
        this.setSize(480,853);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("2048 by Arvid Bergman");
        this.setResizable(false);
        this.setLayout(null);
        this.setIconImage(frameIcon.getImage());
        this.setBackground(Color.decode("#fbf8ef"));

        board.setLayout(new GridLayout(4,4));
        board.setBounds(10,340,460,460);
        topBoard.setBounds(10,20,460, 310);
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
        runGame();
    }

    private void initScoreBoard() {
        JPanel title = new JPanel();
        JLabel titleLabel = new JLabel("2048");
        JLabel scoreLabel = new JLabel("Score:");
        JLabel highScoreLabel = new JLabel("Best:");

        topBoard.setLayout(null);
        topBoard.setBackground(Color.decode("#fbf8ef"));

        scorePanel.setBounds(175,200, 150, 55);
        scorePanel.setBackground(Color.decode("#bcafa0"));
        scoreLabelPoints.setFont(new Font("Verdana", Font.BOLD, 25));
        scoreLabelPoints.setForeground(Color.decode("#fbf8ef"));
        scorePanel.add(scoreLabelPoints);
        topBoard.add(scorePanel);

        highScorePanel.setBounds(10,200, 150, 55);
        highScorePanel.setBackground(Color.decode("#bcafa0"));
        highScoreLabelPoints.setFont(new Font("Verdana", Font.BOLD, 25));
        highScoreLabelPoints.setForeground(Color.decode("#fbf8ef"));
        highScorePanel.add(highScoreLabelPoints);
        topBoard.add(highScorePanel);

        topBoard.add(newGameButton);

        newGameButton.setFocusable(false);
        newGameButton.setBounds(340, 160, 100,100);
        newGameButton.addActionListener(e-> {
            try {
                newGame();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        newGameButton.setIcon(restart);

        topBoard.add(score);
        score.setBackground(Color.decode("#bcafa0"));
        score.setBounds(175,160, 150,100);
        score.add(scoreLabel);
        scoreLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        scoreLabel.setForeground(Color.decode("#fbf8ef"));


        topBoard.add(highScore);
        highScore.setBackground(Color.decode("#bcafa0"));
        highScore.setBounds(10, 160, 150,100);
        highScore.add(highScoreLabel);
        highScoreLabel.setFont(new Font("Verdana", Font.BOLD, 25));
        highScoreLabel.setForeground(Color.decode("#fbf8ef"));

        title.setBackground(Color.decode("#fbf8ef"));
        title.setBounds(-25, -10, 500, 200);
        titleLabel.setFont(new Font("Verdana",Font.BOLD,150));
        titleLabel.setForeground(Color.decode("#726b63"));
        title.add(titleLabel);
        topBoard.add(title);

        this.add(topBoard);
    }

    private int spawnValues() {
        var test = Math.random();
        if(test < 0.1) {
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
