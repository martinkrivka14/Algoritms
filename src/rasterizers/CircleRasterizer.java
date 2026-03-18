package rasterizers;

import models.Circle;
import models.Line;
import rasters.Raster;

import java.awt.*;

public class CircleRasterizer {

    private Raster raster;

    public CircleRasterizer(Raster raster) {
        this.raster = raster;
    }


    public void rasterize(Circle circle) {
        int cx = circle.getCenter().getX();
        int cy = circle.getCenter().getY();
        int radius = circle.getRadius();
        Color color = circle.getColor();
        int width = circle.getWidth();
        boolean dotted = circle.isDotted();
        boolean intermittent = circle.isIntermittent();


        int startOffset = -(width / 2);
        for (int i = 0; i < width; i++) {
            int currentRadius = radius + startOffset + i;
            if (currentRadius > 0) {
                drawBresenham(cx, cy, currentRadius, color, dotted, intermittent);
            }
        }
    }


    private void drawBresenham(int cx, int cy, int r, Color color, boolean dotted, boolean intermittent) {
        int x = 0;
        int y = r;
        int d = 3 - 2 * r;


        int pixelCount = 0;

        while (x <= y) {

            if (shouldDraw(pixelCount, dotted, intermittent)) {
                drawEightPoints(cx, cy, x, y, color);
            }
            pixelCount++;


            if (d < 0) {
                d += 4 * x + 6;
            } else {
                d += 4 * (x - y) + 10;
                y--;
            }
            x++;
        }
    }


    private boolean shouldDraw(int pixelCount, boolean dotted, boolean intermittent) {
        if (dotted) {

            return (pixelCount % 2) == 0;
        }
        if (intermittent) {

            return (pixelCount % 8) < 4;
        }
        return true;
    }


    private void drawEightPoints(int cx, int cy, int x, int y, Color color) {
        drawPixelSafe(cx + x, cy + y, color);
        drawPixelSafe(cx - x, cy + y, color);
        drawPixelSafe(cx + x, cy - y, color);
        drawPixelSafe(cx - x, cy - y, color);
        drawPixelSafe(cx + y, cy + x, color);
        drawPixelSafe(cx - y, cy + x, color);
        drawPixelSafe(cx + y, cy - x, color);
        drawPixelSafe(cx - y, cy - x, color);
    }


    private void drawPixelSafe(int x, int y, Color color) {
        if (x >= 0 && x < raster.getWidth() && y >= 0 && y < raster.getHeight()) {
            raster.setPixel(x, y, color.getRGB());
        }
    }
}
