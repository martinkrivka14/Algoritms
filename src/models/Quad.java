package models;

import java.awt.*;

public class Quad {
    private Line l1, l2, l3, l4;

    public Quad(Line l1, Line l2, Line l3, Line l4) {
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.l4 = l4;
    }

    public Line getL1() { return l1; }
    public Line getL2() { return l2; }
    public Line getL3() { return l3; }
    public Line getL4() { return l4; }

    public void move(int dx, int dy) {

        l1.getP1().setX(l1.getP1().getX() + dx);
        l1.getP1().setY(l1.getP1().getY() + dy);

        l1.getP2().setX(l1.getP2().getX() + dx);
        l1.getP2().setY(l1.getP2().getY() + dy);

        l2.getP2().setX(l2.getP2().getX() + dx);
        l2.getP2().setY(l2.getP2().getY() + dy);

        l3.getP2().setX(l3.getP2().getX() + dx);
        l3.getP2().setY(l3.getP2().getY() + dy);
    }
}
