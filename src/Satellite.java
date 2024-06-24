package src;

import java.awt.Color;
import java.awt.Graphics;

public class Satellite {
    private String name;
    private double velX = 0;
    private double velY = 0;
    private double distanceFromPlanet;
    private double xLoc;
    private double yLoc;
    private int diameter;
    private Color color;
    private double orbitSpeed;

    // Конструктор
    public Satellite(String n, double xVelocity, double yVelocity, double dist, int bodyDiameter, Color col, double speed) {
        name = n;
        velX = xVelocity;
        velY = yVelocity;
        distanceFromPlanet = dist;
        diameter = bodyDiameter;
        color = col;
        orbitSpeed = speed;
    }

    public void update(double planetX, double planetY) {
        angle += orbitSpeed;
        xLoc = planetX + distanceFromPlanet;
        yLoc = planetY + distanceFromPlanet;
    }

    public void draw(Graphics g, double scale) {
        g.setColor(color);
        g.fillOval((int) (650 + (xLoc - diameter / 2 - 650) * scale), (int) (500 + (yLoc - diameter / 2 - 500) * scale),
                (int) (diameter * scale), (int) (diameter * scale));
    }

    public void drawOrbit(Graphics g, double planetX, double planetY, double scale) {
        g.setColor(color);
        double xOrbit, yOrbit;
        for (int i = 0; i < 360; i++) {
            xOrbit = planetX + distanceFromPlanet * Math.cos(Math.toRadians(i));
            yOrbit = planetY + distanceFromPlanet * Math.sin(Math.toRadians(i));
            g.drawLine((int) (650 + (xOrbit - 650) * scale), (int) (500 + (yOrbit - 500) * scale),
                    (int) (650 + (planetX - 650) * scale), (int) (500 + (planetY - 500) * scale));
        }
    }
}

