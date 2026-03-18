package rasterizers;

import models.Line;
import rasters.Raster;

import java.awt.*;

public class TrivialRasterizer implements Rasterizer {

    private Color defaultColor;
    private Raster raster;

    public TrivialRasterizer(Color defaultColor,Raster raster) {
        this.defaultColor = defaultColor;
        this.raster = raster;
    }
    @Override
    public void setColor(Color color) {
        defaultColor = color;
    }


    @Override
    public void rasterize(Line line) {



        double x1 = line.getP1().getX();
        double y1 = line.getP1().getY();
        double x2 = line.getP2().getX();
        double y2 = line.getP2().getY();

        double q;

        int d;

        if(line.isDotted())
            d = 5;
        else
            d = 1;


        double k = 0;

        Integer width = line.getWidth();
        Color color = line.getColor();

        Boolean isIntermittent = line.isIntermittent();



        if(x1-x2 != 0){
            k = (y2 - y1)/ (double)(x2 - x1);
        }

        if(line.isToAlign()){

            double toTan = (y2-y1)/(x1-x2);
            double angle = Math.atan(toTan)*180/Math.PI;

            double[] toAlign = {-90.0,-45.0,0.0,45.0,90.0};

            double tmp = Double.MAX_VALUE;
            int index = 0;

            for (int i = 0; i < toAlign.length; i++) {
                double now = Math.abs(toAlign[i]-angle);

                if(now < tmp ){
                    tmp = now;
                    index = i;
                }
            }
            double newAngle = toAlign[index];

            double newY2 = y1 + (x2-x1) * Math.tan(newAngle* Math.PI / 180);

            int rY2 = (int)newY2;

            k = (newY2-y1)/(x1-x2);

        }

        q = y1 - k * x1;


        int startOffset = -(width / 2);


        if(width > 1){
            for(int i = 0; i < width; i++){
                int currentOffset = startOffset + i;
                Draw(
                        k,
                        x1,
                        y1,
                        x2,
                        y2,
                        color,
                        q,
                        d,
                        currentOffset,
                        isIntermittent
                );
            }
        }
        else
            Draw(k, x1, y1, x2, y2, color, q, d, 0, isIntermittent);



    }

    public void drawPixelSafe(int x, int y, Color color)
    {
        if (x >= 0 && x < raster.getWidth() && y >= 0 && y < raster.getHeight()) {
            raster.setPixel(x, y, color.getRGB());
        }
    }

    public void Draw (double k, double x1, double y1, double x2, double y2, Color color, double q, int d, int currentOffset, Boolean isIntermittent) {

        if (x1 == x2) {
            if (y1 > y2) { double tmp = y1; y1 = y2; y2 = tmp; }
            for (int y = (int)y1; y <= y2; y += d) {

                if(isIntermittent) {
                    if (y % 10 == 0)
                        d = 4;
                    else
                        d = 1;
                }

                drawPixelSafe((int)x1 + currentOffset, y, color);
            }
            return;
        }

        if(Math.abs(k) < 1){
            if(x1< x2){

                for(int x = (int)x1; x <= x2; x += d) {

                    if(isIntermittent) {
                        if (x % 10 == 0)
                            d = 4;
                        else
                            d = 1;
                    }


                    int y  = (int)Math.round( (k * x) + q);
                    drawPixelSafe(x,y + currentOffset, color);
                }
            }else if(x1 > x2){
                for(int x = (int)x2; x <= x1; x += d) {

                    if(isIntermittent) {
                        if (x % 10 == 0)
                            d = 4;
                        else
                            d = 1;
                    }

                    int y  = (int)Math.round((k * x) + q);
                    drawPixelSafe(x,y + currentOffset, color);
                }
            }

        }
        else{
            if(y1 < y2){
                for(int y = (int)y1; y <= y2; y += d) {

                    if(isIntermittent) {
                        if (y % 10 == 0)
                            d = 4;
                        else
                            d = 1;
                    }

                    int x  = (int)Math.round((y - q) / k);
                    drawPixelSafe(x + currentOffset,y, color);
                }
            }else if(y1 > y2){
                for(int y = (int)y2; y <= y1; y += d) {

                    if(isIntermittent) {
                        if (y % 10 == 0)
                            d = 4;
                        else
                            d = 1;
                    }

                    int x  = (int)Math.round((y - q) / k);
                    drawPixelSafe(x + currentOffset,y, color);
                }
            }
        }

    }
}
