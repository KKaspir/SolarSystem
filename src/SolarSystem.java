package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SolarSystem extends JPanel {
    Planet[] solarSystemPlanets = new Planet[9];
    Satellite[] satellites = new Satellite[1];
    Model model;
    final static int DELAY = 100;
    double size = 1;
    String[][] description;
    boolean stop = false;
    int clicked = -1;

    public SolarSystem() {
        model = new Model();
        model.setPreferredSize(new Dimension(1800, 1200));
        add(model);
        int smeshenie = 300;

        solarSystemPlanets[0] = new Planet("Меркурий", 600+smeshenie, 450+smeshenie, -4.7, 0, 12, 8, new Color(197, 198, 196), 1000); // Меркурий
        solarSystemPlanets[1] = new Planet("Венера",752+smeshenie, 400+smeshenie, 0, 2.5, 15 , 12, new Color(207, 153, 52), 1000); // Венера
        solarSystemPlanets[2] = new Planet("Земля",600+smeshenie, 150+smeshenie, 1.8, 0, 15, 11, Color.BLUE, 2000); // Земля
        solarSystemPlanets[3] = new Planet("Марс",650+smeshenie, -50+smeshenie, 1.2, 0, 14, 7, Color.RED, 2000); // Марс
        solarSystemPlanets[4] = new Planet("Юпитер",600+smeshenie, -100+smeshenie, 1.2, 0, 14, 20, new Color(171, 150, 117), 1000); // Юпитер
        solarSystemPlanets[5] = new Planet("Сатурн",600+smeshenie, -150+smeshenie, 1.2, 0, 14, 15, new Color(112, 128, 144), 1000); // Сатурн
        solarSystemPlanets[6] = new Planet("Уран",600+smeshenie, -175+smeshenie, 1.2, 0, 14, 15, new Color(172, 169, 161), 1000); // Уран
        solarSystemPlanets[7] = new Planet("Нептун",0+smeshenie, 400+smeshenie, 0, -1.2, 12, 13, new Color(66, 98, 243), 1000);// Нептун

        solarSystemPlanets[8] = new Planet("Солнце",600+smeshenie, 400+smeshenie, .1, 0,    70 , 30, new Color(252, 97, 10), 0);//Солнышко1,9885⋅10 30

        satellites[0] = new Satellite("Луна", 1, 1, 20, 5, Color.GRAY, 2); // Пример параметров для создания объекта спутника, привязанного к Земле

        setBackground(new Color(8, 0, 28));


        Thread thread = new Thread(() -> gameLoop());

        thread.start();
    }


    //  Обновление кадров
    private void gameLoop() {
        while (true) {
            if (!stop) {
                for (int i = 0; i < solarSystemPlanets.length - 1; i++) {
                    solarSystemPlanets[i].update(solarSystemPlanets[8].getXPosition(), solarSystemPlanets[8].getYPosition(), solarSystemPlanets[8].getMass(), solarSystemPlanets[i].getMass());

                }
                for (int j = 0; j < satellites.length; j++) {
                    satellites[j].update(solarSystemPlanets[2].getXPosition(), solarSystemPlanets[2].getYPosition());
                    System.out.println("Рисую спутник");
                }

            }
            repaint();

            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
            }
        }
    }

    class Model extends JPanel implements KeyListener, MouseListener {
        public Model() {
            setFocusable(true); // Это позволяет компоненту Model получать
            requestFocus(); // фокус клавиатуры и таким образом обрабатывать события клавиш.
            addKeyListener(this);
            addMouseListener(this);
        }

        // отрисовка всех объектов
        public void paintComponent(Graphics g) {
            for (var body : solarSystemPlanets) // пока только планетки
                body.draw(g, size);

            for (var body : satellites)
                body.draw(g, size);


            for (int i = 0; i < solarSystemPlanets.length; i++)
            {
                if (solarSystemPlanets[i].getDescVisible())
                    solarSystemPlanets[i].dispDesc(g,size);
            }
        }

        public void keyTyped(KeyEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        // Действие с планетой, когда попадаем по ней курсором
        public void mouseReleased(MouseEvent e) {
            for (int i = 0; i < solarSystemPlanets.length; i++) {
                if (solarSystemPlanets[i].hitPlanet(e.getX(), e.getY(), size)) {

                    solarSystemPlanets[i].setDescVisible(!solarSystemPlanets[i].getDescVisible());
                    if (solarSystemPlanets[i].getDescVisible()) {
                        clicked = i;
                    } else {
                        clicked = -1;
                    }
                }
            }
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void keyPressed(KeyEvent e) {
        }

        // Установка горячих клавишь
        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                stop = !stop;
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }

        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                JFrame frame = new JFrame("Pravdin A, PI22");
                frame.setContentPane(new SolarSystem());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }
}

