package models;

import java.awt.*;

public class Line {

    private Point p1, p2;
    private Color color;
    private boolean dotted = false;
    private boolean toAlign =  false;
    private Integer width = 1;
    private boolean intermittent = false;


    public Line(Point p1, Point p2, Color color, boolean dotted,  boolean toAlign,  Integer width, boolean intermittent) {
        this.p1 = p1;
        this.p2 = p2;
        this.color = color;
        this.dotted = dotted;
        this.toAlign = toAlign;
        this.width = width;
        this.intermittent = intermittent;

    }

    public void lineInfo(){
        System.out.println("Line Constructor");
        System.out.println("p1: x: " + p1.getX() + " y: " + p1.getY());
        System.out.println("p2: x " + p2.getX() + " y: " + p2.getY());
        System.out.println("color: " + color.getRGB());
        System.out.println("dotted: " + dotted);
        System.out.println("toAlign: " + toAlign);
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDotted() {
        return dotted;
    }

    public void setDotted(boolean dotted) {
        this.dotted = dotted;
    }

    public boolean isToAlign() {
        return toAlign;
    }

    public void setToAlign(boolean toAlign) {
        this.toAlign = toAlign;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public boolean isIntermittent() {
        return intermittent;
    }

    public void setIntermittent(boolean intermittent) {
        this.intermittent = intermittent;
    }

    public void move(int dx, int dy) {
        p1.setX(p1.getX() + dx);
        p1.setY(p1.getY() + dy);
        p2.setX(p2.getX() + dx);
        p2.setY(p2.getY() + dy);
    }
}
