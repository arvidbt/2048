import javax.swing.*;
import java.io.*;
import java.util.Scanner;

public class Score {
    public static int currentINT = 0;
    public static int highScoreINT = 0;

    public static File highScoreFile = new File("src/highscore.txt");

    public static JLabel highScoreLabelPoints;
    public static JLabel scoreLabelPoints;


    public Score(JLabel highScoreLabelPoints, JLabel scoreLabelPoints) {
        Score.highScoreLabelPoints = highScoreLabelPoints;
        Score.scoreLabelPoints = scoreLabelPoints;
    }

    public static void setScore(int score) {
        currentINT += score;
        String scoreStr = Integer.toString(currentINT);
        scoreLabelPoints.setText(scoreStr);
    }

    public static void resetScore() {
        currentINT = 0;
        String scoreStr = Integer.toString(currentINT);
        scoreLabelPoints.setText(scoreStr);
    }

    public static void setHighScore() throws IOException {
        if(currentINT > highScoreINT) {
            highScoreINT = currentINT;
            String scoreStr = Integer.toString(highScoreINT);
            highScoreLabelPoints.setText(scoreStr);
            saveHighScore();
        }
    }

    public static void loadHighScore() throws FileNotFoundException {
        Scanner sc = new Scanner(highScoreFile);
        if(sc.hasNextInt()) {
            highScoreINT = sc.nextInt();
            String scoreStr = Integer.toString(highScoreINT);
            highScoreLabelPoints.setText(scoreStr);
        }
    }

    public static void saveHighScore() throws IOException {
        try (Writer writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            writer.write(Integer.toString(highScoreINT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
