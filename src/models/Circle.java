package models;


import java.awt.*;

public class Circle {

    private Point center;
    private Point edgePoint; // bod na kružnici (druhý klik myší)
    private Color color;
    private int width;
    private boolean dotted;
    private boolean intermittent;

    public Circle(Point center, Point edgePoint, Color color, int width, boolean dotted, boolean intermittent) {
        this.center = center;
        this.edgePoint = edgePoint;
        this.color = color;
        this.width = width;
        this.dotted = dotted;
        this.intermittent = intermittent;
    }

    public int getRadius() {
        int dx = (int)(edgePoint.getX() - center.getX());
        int dy = (int)(edgePoint.getY() - center.getY());
        return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
    }

    public void move(int dx, int dy) {
        center.setX(center.getX() + dx);
        center.setY(center.getY() + dy);
        edgePoint.setX(edgePoint.getX() + dx);
        edgePoint.setY(edgePoint.getY() + dy);
    }

    public Point getCenter() { return center; }
    public Point getEdgePoint() { return edgePoint; }
    public Color getColor() { return color; }
    public int getWidth() { return width; }
    public boolean isDotted() { return dotted; }
    public boolean isIntermittent() { return intermittent; }

    public void setEdgePoint(Point edgePoint) { this.edgePoint = edgePoint; }
    public void setColor(Color color) { this.color = color; }
    public void setWidth(int width) { this.width = width; }
    public void setDotted(boolean dotted) { this.dotted = dotted; }
    public void setIntermittent(boolean intermittent) { this.intermittent = intermittent; }
}
