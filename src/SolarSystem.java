package src;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class SolarSystem extends JPanel {
    Planet[] solarSystemPlanets = new Planet[9];
    Satellite[] satellites = new Satellite[1];

    StarField starField = new StarField();

    ControlPanel[] controlPanels = new ControlPanel[9];
    int DELAY = 50;
    ControlPanelForInteger delayPanel = new ControlPanelForInteger(DELAY, "Задержка между кадрами");

    Model model;

    double size = 1;
    String[][] description;
    boolean stop = false;
    int clicked = -1;

    public SolarSystem() {
        int smeshenie = 300;

        solarSystemPlanets[0] = new Planet("Меркурий", 600+smeshenie, 450+smeshenie, -4.7, 0, 12, 8, new Color(197, 198, 196), 1000); // Меркурий
        solarSystemPlanets[1] = new Planet("Венера",752+smeshenie, 400+smeshenie, 0, 2.5, 15 , 12, new Color(207, 153, 52), 1000); // Венера
        solarSystemPlanets[2] = new Planet("Земля",600+smeshenie, 150+smeshenie, 1.8, 0, 15, 11, Color.BLUE, 2000); // Земля
        solarSystemPlanets[3] = new Planet("Марс",650+smeshenie, -50+smeshenie, 1.2, 0, 14, 7, Color.RED, 2000); // Марс
        solarSystemPlanets[4] = new Planet("Юпитер",600+smeshenie, -100+smeshenie, 1.2, 0, 14, 20, new Color(171, 150, 117), 1000); // Юпитер
        solarSystemPlanets[5] = new Planet("Сатурн",600+smeshenie, -150+smeshenie, 1.2, 0, 14, 15, new Color(112, 128, 144), 1000); // Сатурн
        solarSystemPlanets[6] = new Planet("Уран",600+smeshenie, -175+smeshenie, 1.2, 0, 14, 15, new Color(172, 169, 161), 1000); // Уран
        solarSystemPlanets[7] = new Planet("Нептун",0+smeshenie, 400+smeshenie, 0, -1.2, 12, 13, new Color(66, 98, 243), 1000);// Нептун
        solarSystemPlanets[8] = new Planet("Солнце",600+smeshenie, 400+smeshenie, .1, 0,    70 , 30, new Color(252, 97, 10), 0);//Солнышко1,
        satellites[0] = new Satellite("Луна", 20, 16, 6, Color.GRAY); // Пример параметров для создания объекта спутника, привязанного к Земле


        Panel modelAndControlsPanel = new Panel();
        modelAndControlsPanel.setLayout(new GridLayout(0, 2));
        model = new Model();
        model.setPreferredSize(new Dimension(1800, 1200));
        add(model, BorderLayout.CENTER);
        add(modelAndControlsPanel, BorderLayout.CENTER);
        for (int i = 0; i < 9; i++) {
            controlPanels[i] = new ControlPanel(solarSystemPlanets[i]);
            modelAndControlsPanel.add(controlPanels[i]);
        }

        modelAndControlsPanel.add(delayPanel);

        setBackground(new Color(8, 0, 28));
        Thread thread = new Thread(() -> gameLoop());

        DELAY = delayPanel.getXValue();

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
                }

                starField.twinklingStars();
//                controlPanel.drawComponent(g);
                DELAY = delayPanel.getXValue();
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
                body.draw(g);

            for (var body : satellites) {
                body.draw(g);
            }

            for (int i = 0; i < solarSystemPlanets.length; i++)
            {
                if (solarSystemPlanets[i].getOrbitVisible())
                    solarSystemPlanets[i].dispDesc(g);
            }

            for (int i = 0; i < satellites.length; i++)
            {
                if (satellites[i].getOrbitVisible())
                    satellites[i].drawOrbit(g);
            }

            starField.drawComponent(g);

        }

        public void keyTyped(KeyEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        // Действие с планетой, когда попадаем по ней курсором
        public void mouseReleased(MouseEvent e) {
            for (int i = 0; i < solarSystemPlanets.length; i++) {
                if (solarSystemPlanets[i].hitPlanet(e.getX(), e.getY())) {

                    solarSystemPlanets[i].setOrbitVisible(!solarSystemPlanets[i].getOrbitVisible());
                    if (solarSystemPlanets[i].getOrbitVisible()) {
                        clicked = i;
                    } else {
                        clicked = -1;
                    }
                }
            }
            for (int i = 0; i < satellites.length; i++) {
                if (satellites[i].hitBody(e.getX(), e.getY())) {

                    satellites[i].setOrbitVisible(!satellites[i].getOrbitVisible());
                    if (satellites[i].getOrbitVisible()) {
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

