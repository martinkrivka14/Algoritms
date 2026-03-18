package models;

import rasterizers.Rasterizer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Poligons {


    private ArrayList<Poligon> poligons;
    private Poligon poligon;
    private Boolean isDotted;
    private Boolean isIntermittent;
    private ArrayList<Line> lines = new ArrayList<>();
    private Color color;
    private Integer lineWidth;



    public Poligons(ArrayList<Poligon> poligons) {
        this.poligons = poligons;
    }

    public void addPoligon(Poligon poligon)
    {
        this.poligons.add(poligon);
    }

    public void rasterizePoligons(Rasterizer rasterizer){



       this.lines.clear();

       for (Poligon poligon : poligons) {

           List<Point> points = poligon.getPoints();
           isDotted = poligon.getDotted();
           color = poligon.getColor();
           lineWidth = poligon.getWidth();
           isIntermittent = poligon.getIntermittent();


           if(points.size()>2 ) {

               for (int j = 0; j < points.size() - 1; j++) {
                   Line line = new Line(points.get(j), points.get(j + 1), color, isDotted, false, lineWidth, isIntermittent );
                   lines.add(line);
                   rasterizer.rasterize(line);
               }

               Line closing = new Line(points.get(points.size() - 1), points.get(0), color, isDotted, false,lineWidth, isIntermittent);
               lines.add(closing);
               rasterizer.rasterize(closing);

           }

       }




    }

    public void erasAllLines()
    {

            this.poligons.clear();
            this.lines.clear();
            System.out.println("heh");

    }

    public void ChangeDotted(Rasterizer rasterizer)
    {
        List<Line> lines = this.lines;

        for (Line line : lines) {
            line.setDotted(!line.isDotted());
            rasterizer.rasterize(line);
        }

    }
}
