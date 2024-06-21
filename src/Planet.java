package src;//
// Класс планеты 
// 

import javax.swing.*;
import java.awt.*;

public class Planet {
    private int mass = 0;
    private int diameter = 0;
    private double xLoc = 0;
    private double yLoc = 0;
    private double velX = 0;
    private double velY = 0;
    private double speed = 0;
    Color color;
    private double acceleration = 0;
    private double dirX = 0;
    private double dirY = 0;
    private double distance = 0;
    private double initial = 1000;
    private double max = 0;
    boolean visible;
    int orbitDots[][] = new int[1000][2];
    int counter = 0;

    // конструктор
    public Planet(double x, double y, double xVelocity, double yVelocity, int bodyMass, int bodyDiameter, Color bodyColor, double bodySpeed) {
        xLoc = x;
        yLoc = y;
        velX = xVelocity;
        velY = yVelocity;
        mass = bodyMass;
        diameter = bodyDiameter;
        color = bodyColor;
        speed = bodySpeed;
    }

    public double getXPosition() {
        return xLoc;
    }

    public double getYPosition() {
        return yLoc;
    }

    public int getMass() {
        return mass;
    }

    public int getDiameter() {
        return diameter;
    }

    public boolean getDescVisible() {
        return visible;
    }

    public void setDescVisible(boolean b) {
        visible = b;
    }

    // проверка попадания по планете
    public boolean hitPlanet(int x, int y, double scale) {
        return (x > 600 + (getXPosition() - diameter - 600) * scale && x < 600 + (getXPosition() + diameter - 600) * scale &&
                y > 400 + (getYPosition() - diameter - 400) * scale && y < 400 + (getYPosition() + diameter - 400) * scale);
    }

    // передвижение из Location на Velocity 
    public void move() {
        xLoc += velX;
        yLoc += velY;
    }

    public void update(double StarX, double StarY, int StarMass) {
        if (visible) {
            orbitDots[counter][0] = (int) (xLoc + .5);
            orbitDots[counter][1] = (int) (yLoc + .5);
            counter = (counter + 1) % 1000;
        } else {
            orbitDots = new int[1000][2];
            counter = 0;
        }
        distance = Math.sqrt((StarX - xLoc) * (StarX - xLoc) + (StarY - yLoc) * (StarY - yLoc));
        initial = Math.min(distance, initial);
        max = Math.max(distance, max);

        acceleration = StarMass / distance / distance;

        dirX = (StarX - xLoc) / distance;
        dirY = (StarY - yLoc) / distance;

        velX += dirX * acceleration;
        velY += dirY * acceleration;
        move();

    }

    public void draw(Graphics g, double size) {
        g.setColor(color);
        g.fillOval((int) (650 + (xLoc - diameter / 2 - 650) * size), (int) (500 + (yLoc - diameter / 2 - 500) * size),
                (int) (diameter * size), (int) (diameter * size));
    }

// *****************************
}


