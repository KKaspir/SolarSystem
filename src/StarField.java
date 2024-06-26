package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class StarField extends JPanel {

    private ArrayList<Point> stars;
    private Random random;

//    своя функция рандома
    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

//    инициализация всех звезд
    public StarField(int starCount) {
        stars = new ArrayList<>();
        random = new Random();

        for (int i = 0; i < starCount; i++) {
            int x = getRandomNumber(0, 1800);
            int y = getRandomNumber(0, 1200);
            stars.add(new Point(x, y));
        }
    }

    public void drawComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        for (Point star : stars) {
            g.fillRect(star.x, star.y, 1, 1);
        }
    }

    public void twinklingStars() {
        for (Point star : stars) {
            if (random.nextInt(600) < 1) { // частота мерцания
                int x = getRandomNumber(0, 1800);
                int y = getRandomNumber(0, 1200);
                star.setLocation(x, y);
            }
        }
        repaint();
    }
}

