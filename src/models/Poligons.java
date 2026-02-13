package models;

import java.util.LinkedList;
import java.util.List;

public class Poligons {


    public LinkedList<Line> lines;

    public Poligons(LinkedList<Line> lines) {
        this.lines = lines;
    }

    public void addLine(Line line)
    {
        this.lines.add(line);
    }

    public void rasterizePoligon(){

    }
}
