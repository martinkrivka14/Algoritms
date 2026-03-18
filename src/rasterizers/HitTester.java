package rasterizers;

import models.*;
import java.util.ArrayList;

public class HitTester {

    private static final int TOLERANCE = 5; // px tolerance pro kliknutí

    public static boolean hitLine(Line line, int mx, int my) {
        int x1 = line.getP1().getX(), y1 = line.getP1().getY();
        int x2 = line.getP2().getX(), y2 = line.getP2().getY();

        double len2 = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1);
        if (len2 == 0) return dist(mx, my, x1, y1) <= TOLERANCE;

        double t = ((mx-x1)*(x2-x1) + (my-y1)*(y2-y1)) / len2;
        t = Math.max(0, Math.min(1, t));

        double nearX = x1 + t*(x2-x1);
        double nearY = y1 + t*(y2-y1);

        return Math.sqrt((mx-nearX)*(mx-nearX) + (my-nearY)*(my-nearY)) <= TOLERANCE;
    }

    public static boolean hitCircle(Circle circle, int mx, int my) {
        int cx = circle.getCenter().getX();
        int cy = circle.getCenter().getY();
        int r  = circle.getRadius();

        double dist = Math.sqrt((mx-cx)*(mx-cx) + (my-cy)*(my-cy));
        return Math.abs(dist - r) <= TOLERANCE;
    }

    public static boolean hitQuad(Line l1, Line l2, Line l3, Line l4, int mx, int my) {
        return hitLine(l1, mx, my) || hitLine(l2, mx, my)
                || hitLine(l3, mx, my) || hitLine(l4, mx, my);
    }

    public static boolean hitPoligon(Poligon poligon, int mx, int my) {
        ArrayList<Point> pts = poligon.getPoints();
        if (pts.size() < 2) return false;

        for (int i = 0; i < pts.size(); i++) {
            Point a = pts.get(i);
            Point b = pts.get((i + 1) % pts.size());
            Line seg = new Line(a, b, poligon.getColor(), false, false, 1, false);
            if (hitLine(seg, mx, my)) return true;
        }
        return false;
    }


    public static boolean hitFill(Fill fill, int mx, int my) {
        for (int[] pixel : fill.getPixels()) {
            if (pixel[0] == mx && pixel[1] == my) return true;
        }
        return false;
    }

    private static double dist(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
    }
}
