package models;

import rasterizers.CircleRasterizer;
import rasterizers.Rasterizer;
import rasters.Raster;

import java.util.ArrayList;

public class Circles {


    private ArrayList<Circle> circles;
    public Circles(ArrayList<Circle> circles) {
        this.circles = circles;
    }

    public void RasterizeCircles(CircleRasterizer rasterizer) {
        for (Circle circle : circles) {
            rasterizer.rasterize(circle);
        }
    }

    public void eraseAllCircles() {
        this.circles.clear();
    }

    public void changeDotted(CircleRasterizer rasterizer) {
        for (Circle circle : circles) {
            circle.setDotted(!circle.isDotted());
            rasterizer.rasterize(circle);
        }
    }


}
