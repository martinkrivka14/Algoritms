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

        int d = 1;
        double k = 0;

        if(line.isDotted()){
            d = 5;
        }

        if(x1-x2 != 0){
            k = (y2 - y1)/ (double)(x2 - x1);
        }

        if(line.isToAlign()){

            double toTan = (y2-y1)/(x1-x2);
            double angle = Math.atan(toTan)*180/Math.PI;
            System.out.println(angle);

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
            //line.getP2().setY(rY2);

            k = (newY2-y1)/(x1-x2);

        }


        double q = y1 - k * x1;

        if(Math.abs(k) < 1){
            if(x1< x2){

                for(int x = (int)x1; x <= x2; x = x + d) {

                    int y  = (int)Math.round( (k * x) + q);

                    drawPixelSafe(x,y);
                }
            }else if(x1 > x2){
                for(int x = (int)x2; x <= x1; x = x + d) {
                    int y  = (int)Math.round((k * x) + q);

                    drawPixelSafe(x,y);
                }
            }

        }
        else{
            if(y1 < y2){
                for(int y = (int)y1; y <= y2; y =  y + d) {
                    int x  = (int)Math.round((y - q) / k);

                    drawPixelSafe(x,y);
                }
            }else if(y1 > y2){
                for(int y = (int)y2; y <= y1; y = y + d) {

                    int x  = (int)Math.round((y - q) / k);
                    drawPixelSafe(x,y);
                }
            }
        }
    }

    public void drawPixelSafe(int x, int y)
    {
        if (x >= 0 && x < raster.getWidth() && y >= 0 && y < raster.getHeight()) {
            raster.setPixel(x, y, defaultColor.getRGB());
        }
    }


}
