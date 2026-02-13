package rasterizers;

import models.Line;
import rasters.Raster;

import java.awt.*;

public interface Rasterizer {


    void setColor(Color color);

    void rasterize(Line line);

}
