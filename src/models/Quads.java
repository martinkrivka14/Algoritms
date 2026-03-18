package models;



import rasterizers.QuadRasterizer;
import rasterizers.Rasterizer;
import rasterizers.TrivialRasterizer;

import java.util.ArrayList;

public class Quads {
    private ArrayList<Quad> quads;

    public Quads(ArrayList<Quad> quads) {
        this.quads = quads;
    }

    public void addQuad(Quad quad) {
        quads.add(quad);
    }

    public ArrayList<Quad> getQuads() { return quads; }

    public void eraseAll() { quads.clear(); }

    public void rasterizeQuads(Rasterizer rasterizer) {

        for (Quad q : quads) {
            rasterizer.rasterize(q.getL1());
            rasterizer.rasterize(q.getL2());
            rasterizer.rasterize(q.getL3());
            rasterizer.rasterize(q.getL4());
        }
    }
}
