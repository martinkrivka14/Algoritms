package rasterizers;

import rasters.Raster;
import java.awt.*;
import java.util.ArrayList;

public class FloodFill {

    private Raster raster;

    public FloodFill(Raster raster) {
        this.raster = raster;
    }


    public void fill(int x, int y, Color fillColor) {

        if (x < 0 || x >= raster.getWidth() || y < 0 || y >= raster.getHeight()) return;

        int targetColor = raster.getPixel(x, y);
        int fill = fillColor.getRGB();


        if (targetColor == fill) return;

        floodFill(x, y, targetColor, fill);
    }


    private void floodFill(int x, int y, int targetColor, int fillColor) {

        if (x < 0 || x >= raster.getWidth() || y < 0 || y >= raster.getHeight()) return;


        if (raster.getPixel(x, y) != targetColor) return;

        raster.setPixel(x, y, fillColor);

        floodFill(x + 1, y, targetColor, fillColor);
        floodFill(x - 1, y, targetColor, fillColor);
        floodFill(x, y + 1, targetColor, fillColor);
        floodFill(x, y - 1, targetColor, fillColor);
    }

    public ArrayList<int[]> fillAndCollect(int x, int y, Color fillColor) {
        ArrayList<int[]> collectedPixels = new ArrayList<>();

        if (x < 0 || x >= raster.getWidth() || y < 0 || y >= raster.getHeight())
            return collectedPixels;

        int targetColor = raster.getPixel(x, y);
        int fill = fillColor.getRGB();

        if (targetColor == fill) return collectedPixels;

        floodFillCollect(x, y, targetColor, fill, collectedPixels);
        return collectedPixels;
    }

    private void floodFillCollect(int x, int y, int targetColor, int fillColor, ArrayList<int[]> collected) {
        if (x < 0 || x >= raster.getWidth() || y < 0 || y >= raster.getHeight()) return;
        if (raster.getPixel(x, y) != targetColor) return;

        raster.setPixel(x, y, fillColor);
        collected.add(new int[]{x, y, fillColor});

        floodFillCollect(x + 1, y, targetColor, fillColor, collected);
        floodFillCollect(x - 1, y, targetColor, fillColor, collected);
        floodFillCollect(x, y + 1, targetColor, fillColor, collected);
        floodFillCollect(x, y - 1, targetColor, fillColor, collected);
    }
}
