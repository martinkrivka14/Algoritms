package models;

import rasterizers.FloodFill;
import rasters.Raster;

import java.util.ArrayList;

public class Fills {
    private ArrayList<Fill> fills;

    public Fills(ArrayList<Fill> fills) {
        this.fills = fills;
    }

    public void addFill(Fill fill) {
        fills.add(fill);
    }



    public void eraseAllFills() {
        fills.clear();
    }

    public void rasterizeFills(Raster raster) {
        for (Fill fill : fills) {
            for (int[] pixel : fill.getPixels()) {
                // pixel[0] = x, pixel[1] = y, pixel[2] = rgb
                if (pixel[0] >= 0 && pixel[0] < raster.getWidth() &&
                        pixel[1] >= 0 && pixel[1] < raster.getHeight()) {
                    raster.setPixel(pixel[0], pixel[1], pixel[2]);
                }
            }
        }
    }
}
