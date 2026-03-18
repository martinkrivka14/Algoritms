package rasterizers;

import models.*;
import java.util.ArrayList;

/**
 * Detekuje, zda kliknutí míří na "handle" (rohový/krajní bod) objektu.
 * Vrací speciální objekt popisující co a jak se má měnit.
 */
public class ResizeHandle {

    private static final int TOLERANCE = 8; // o něco větší než HitTester

    public enum HandleType { CIRCLE_EDGE, LINE_P1, LINE_P2, QUAD_CORNER, POLYGON_POINT }

    public HandleType type;
    public Object owner;   // Circle / Line / Quad / Poligon
    public int pointIndex; // pro polygon – index vrcholu

    private ResizeHandle(HandleType type, Object owner, int pointIndex) {
        this.type = type;
        this.owner = owner;
        this.pointIndex = pointIndex;
    }

    /** Zkusí najít handle v okolí kliknutí. Vrátí null pokud žádný není blízko. */
    public static ResizeHandle find(int mx, int my,
                                    ArrayList<Circle> circles,
                                    ArrayList<Line>   lines,
                                    ArrayList<Quad>   quads,
                                    ArrayList<Poligon> poligons) {

        for (Circle c : circles) {
            // handle = bod na obvodu (edgePoint)
            if (near(mx, my, c.getEdgePoint())) {
                return new ResizeHandle(HandleType.CIRCLE_EDGE, c, 0);
            }
        }

        for (Line l : lines) {
            if (near(mx, my, l.getP1())) return new ResizeHandle(HandleType.LINE_P1, l, 0);
            if (near(mx, my, l.getP2())) return new ResizeHandle(HandleType.LINE_P2, l, 0);
        }

        for (Quad q : quads) {
            // 4 rohy = p1 čar
            Line[] ls = {q.getL1(), q.getL2(), q.getL3(), q.getL4()};
            for (int i = 0; i < ls.length; i++) {
                if (near(mx, my, ls[i].getP1()))
                    return new ResizeHandle(HandleType.QUAD_CORNER, q, i);
            }
        }

        for (Poligon p : poligons) {
            ArrayList<Point> pts = p.getPoints();
            for (int i = 0; i < pts.size(); i++) {
                if (near(mx, my, pts.get(i)))
                    return new ResizeHandle(HandleType.POLYGON_POINT, p, i);
            }
        }

        return null; // žádný handle nenalezen → fallback na move
    }


    public void applyDrag(int newX, int newY) {
        switch (type) {
            case CIRCLE_EDGE -> ((Circle) owner).getEdgePoint().setX(newX);

        }
        switch (type) {
            case CIRCLE_EDGE -> {
                ((Circle) owner).getEdgePoint().setX(newX);
                ((Circle) owner).getEdgePoint().setY(newY);
            }
            case LINE_P1 -> {
                ((Line) owner).getP1().setX(newX);
                ((Line) owner).getP1().setY(newY);
            }
            case LINE_P2 -> {
                ((Line) owner).getP2().setX(newX);
                ((Line) owner).getP2().setY(newY);
            }
            case QUAD_CORNER -> resizeQuad((Quad) owner, pointIndex, newX, newY);
            case POLYGON_POINT -> {
                Poligon p = (Poligon) owner;
                p.getPoints().get(pointIndex).setX(newX);
                p.getPoints().get(pointIndex).setY(newY);
            }
        }
    }


    private void resizeQuad(Quad q, int corner, int nx, int ny) {

        Point tl = q.getL1().getP1(); // top-left
        Point bl = q.getL1().getP2(); // bottom-left
        Point br = q.getL2().getP2(); // bottom-right
        Point tr = q.getL3().getP2(); // top-right

        switch (corner) {
            case 0 -> {
                tl.setX(nx); tl.setY(ny);
                bl.setX(nx);
                tr.setY(ny);
            }
            case 1 -> {
                bl.setX(nx); bl.setY(ny);
                tl.setX(nx);
                br.setY(ny);
            }
            case 2 -> {
                br.setX(nx); br.setY(ny);
                tr.setX(nx);
                bl.setY(ny);
            }
            case 3 -> {
                tr.setX(nx); tr.setY(ny);
                br.setX(nx);
                tl.setY(ny);
            }
        }
    }

    private static boolean near(int mx, int my, Point p) {
        int dx = mx - p.getX(), dy = my - p.getY();
        return Math.sqrt(dx*dx + dy*dy) <= TOLERANCE;
    }
}
