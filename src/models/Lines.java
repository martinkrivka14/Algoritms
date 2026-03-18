package models;

import rasterizers.Rasterizer;

import java.util.List;

public class Lines {

    public List<Line> lines;


    public Lines(List<Line> lines) {
        this.lines = lines;
    }


    public void addLine(Line line) {
        this.lines.add(line);
    }

    public void eraseAllLines() {
        if(this.lines != null){
            this.lines.clear();
        }
    }


    public void RasterizeLines(Rasterizer rasterizer) {
        List<Line> lines = this.lines;
        for (Line line : lines) {
            rasterizer.rasterize(line);
        }
    }

    public void ChangeDotted(Rasterizer rasterizer) {
        List<Line> lines = this.lines;
        for (Line line : lines) {
            line.setDotted(!line.isDotted());
            rasterizer.rasterize(line);
        }
    }
}
