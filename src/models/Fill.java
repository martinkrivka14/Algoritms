package models;

import java.awt.*;
import java.util.ArrayList;

public class Fill {
    private ArrayList<int[]> pixels; // uložené pixely [x, y, rgb]
    private Color color;

    public Fill(ArrayList<int[]> pixels, Color color) {
        this.pixels = pixels;
        this.color = color;
    }

    public ArrayList<int[]> getPixels() { return pixels; }
    public Color getColor() { return color; }

    public void move(int dx, int dy) {
        for (int[] pixel : pixels) {
            pixel[0] += dx;
            pixel[1] += dy;
        }
    }
}