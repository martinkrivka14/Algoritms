package rasterizers;

import models.Line;
import rasters.Raster;
import models.Point;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public class QuadRasterizer implements Rasterizer {


    private Color defaultColor;
    private Raster raster;

    public QuadRasterizer(Color defaultColor, Raster raster) {
        this.defaultColor = defaultColor;
        this.raster = raster;
    }

    @Override
    public void setColor(Color color) {

    }


    /*
    @Override
    public void rasterize(Line line){

        TrivialRasterizer tr = new TrivialRasterizer(line.getColor(), raster);
        tr.rasterize(line);
    }*/



    @Override
    public void rasterize(Line line) {

        int x2 = line.getP2().getX();
        int y2 = line.getP2().getY();

        int x1 = line.getP1().getX();
        int y1 = line.getP1().getY();

        int d;

        Color color = line.getColor();


        int difX = Math.abs(x2 - x1);
        int difY = Math.abs(y2 - y1);

        int width = line.getWidth();
        Boolean isIntermittent = line.isIntermittent();


        if(line.isToAlign()){


            int max = Math.max(difX, difY);
            difX = max;
            difY = max;

            if(y2 > y1){
                y2 = y1 + difY;
            }else{
                y2 = y1 - difY;
            }

            if(x2 > x1){
                x2 = x1 + difX;
            }else{
                x2 = x1 - difX;
            }
        }

        if(line.isDotted())
            d = 5;
        else
            d = 1;


        int startOffset = -(width / 2);

        for(int i = 0; i < width; i++){
            int currentOffset = startOffset + i;
            draw(x1, y1, x2, y2, difX, difY, d, color, currentOffset, isIntermittent);

        }
    }



    public void drawPixelSafe(int x, int y, Color color)
    {
        if (x >= 0 && x < raster.getWidth() && y >= 0 && y < raster.getHeight()) {
            raster.setPixel(x, y, color.getRGB());
        }
    }



    public void draw(int x1, int y1,  int x2, int y2, int difX, int difY, int d, Color color, int currentOffset,  boolean isIntermittent) {

        if(x2 > x1){
            for (int i = 0; i <= difX; i += d) {
                if(isIntermittent){
                    if(i%10 == 0)
                        d = 4;
                    else
                        d = 1;
                }

                for (int j = 0; j <= difY; j += d) {

                    if(isIntermittent){
                        if(j%10 == 0)
                            d = 4;
                        else
                            d = 1;
                    }

                    if(y2 > y1){
                        if(i == 0 ||  i + d > difX || j == 0 || j + d > difY){
                            drawPixelSafe(x1+i, y1+j, color);
                        }
                    }
                    if(y1 > y2){
                        if(i == 0 ||  i + d > difX || j == 0 || j + d > difY){
                            drawPixelSafe(x1 +i, y1-j,  color);
                        }
                    }
                }
            }
        }
        if(x1 > x2){
            for (int i = 0; i <= difX; i += d) {

                if(isIntermittent){
                    if(i%10 == 0)
                        d = 4;
                    else
                        d = 1;
                }

                for (int j = 0; j <= difY; j += d) {

                    if(isIntermittent){
                        if(j%10 == 0)
                            d = 4;
                        else
                            d = 1;
                    }

                    if(y2 > y1){
                        if(i == 0 || j == 0 ||   i + d > difX || j + d > difY){
                            drawPixelSafe(x1 - i , y1 +j , color);
                        }
                    }
                    if(y1 > y2){
                        if(i == 0 || j == 0 ||   i + d > difX || j + d > difY){
                            drawPixelSafe( x1 -i , y1 -j ,  color);
                        }
                    }
                }
            }
        }

    }



}
