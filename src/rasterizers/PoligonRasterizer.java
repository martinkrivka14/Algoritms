package rasterizers;

import models.Line;
import rasters.Raster;

import java.awt.*;

public class PoligonRasterizer implements Rasterizer {

    public Raster raster;
    public Color defaultColor = Color.BLACK;

    public PoligonRasterizer(Color defaultColor, Raster raster) {
        this.defaultColor = defaultColor;
        this.raster = raster;
    }


    @Override
    public void setColor(Color color) {

    }

    @Override
    public void rasterize(Line line) {


    }


    public void Ahoj(){
        System.out.println("Ahoj");
    }

    public void drawPixelSafe(int x, int y)
    {
        if (x >= 0 && x < raster.getWidth() && y >= 0 && y < raster.getHeight()) {

            raster.setPixel(x, y, defaultColor.getRGB());
        }
    }

}
