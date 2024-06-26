package src;

import java.awt.*;

public class Satellite {
    private String name;
    private double distanceFromPlanet;
    private double angle = 0;
    private double orbitSpeed;
    private double xLoc;
    private double yLoc;
    private int diameter;
    private Color color;
    int orbitDots[][] = new int[4000][2];
    int counter = 0;
    boolean visible = false;

    // Конструктор
    public Satellite(String n, double dist, double speed, int dia, Color col) {
        name = n;
        distanceFromPlanet = dist;
        orbitSpeed = speed;
        diameter = dia;
        color = col;
    }

    public double getXPosition() {
        return xLoc;
    }

    public double getYPosition() {
        return yLoc;
    }
    public boolean getOrbitVisible() {
        return visible;
    }
    public void setOrbitVisible(boolean b) {
        visible = b;
    }

    public boolean hitBody(int x, int y) {
        return (x > 600 + (getXPosition() - diameter - 600) && x < 600 + (getXPosition() + diameter - 600) &&
                y > 400 + (getYPosition() - diameter - 400) && y < 400 + (getYPosition() + diameter - 400));
    }

    public void update(double planetX, double planetY) {
        if (visible) {
            orbitDots[counter][0] = (int) (xLoc + .5);
            orbitDots[counter][1] = (int) (yLoc + .5);
            counter = (counter + 1) % 4000;
        } else {
            orbitDots = new int[4000][2];
            counter = 0;
        }
        angle += orbitSpeed;
        xLoc = planetX + distanceFromPlanet * Math.cos(Math.toRadians(angle));
        yLoc = planetY + distanceFromPlanet * Math.sin(Math.toRadians(angle));
    }

    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) (650 + (xLoc - diameter / 2 - 650) ), (int) (500 + (yLoc - diameter / 2 - 500)),
                (int) (diameter ), (int) (diameter ));
    }

    public void drawOrbit(Graphics g) {
        g.setColor(color);
        for (int[] orbit : orbitDots)
            g.drawLine(orbit[0], orbit[1], orbit[0], orbit[1]);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.MAGENTA);


        g.drawString(name,
                diameter + (int) (600 + (xLoc - diameter / 2 - 600) ), 16 + (int) (410 + (yLoc - diameter / 2 - 400)) + diameter);
    }

}

