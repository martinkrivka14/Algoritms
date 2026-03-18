package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Poligon {



    private ArrayList<Point> points;
    private Boolean isDotted;
    private Boolean isIntermittent;
    private Color color;
    private Integer width;





    public  Poligon(ArrayList<Point> points, Boolean isDotted, Color color,  Integer width,  Boolean isIntermittent)
    {
        this.points = points;
        this.isDotted = isDotted;
        this.color = color;
        this.width = width;
        this.isIntermittent = isIntermittent;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public Boolean getDotted() {
        return isDotted;
    }

    public void setDotted(Boolean dotted) {
        isDotted = dotted;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }


    public Boolean getIntermittent() {
        return isIntermittent;
    }

    public void setIntermittent(Boolean intermittent) {
        isIntermittent = intermittent;
    }

    public void move(int dx, int dy) {
        for (Point p : points) {
            p.setX(p.getX() + dx);
            p.setY(p.getY() + dy);
        }
    }
}
