import models.Line;
import models.Lines;
import models.Point;
import rasterizers.PoligonRasterizer;
import rasterizers.Rasterizer;
import rasterizers.TrivialRasterizer;
import rasters.Raster;
import rasters.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App {

    private final JPanel panel;
    private final Raster raster;
    private Rasterizer rasterizer;
    private PoligonRasterizer poligonRasterizer;
    private MouseAdapter mouseAdapterClassic;
    private MouseAdapter mouseAdapterPoligon;
    private MouseAdapter mouseAdapteraForClassic;
    private MouseAdapter mouseAdapteraForPoligon;
    private MouseMotionAdapter mouseMotionAdapter;
    private KeyAdapter keyAdapterClassic;
    private Lines lines;
    private boolean dotted;
    private boolean toAlign;
    private Point pPomocny;
    private Boolean classic;
    private Boolean poligon;
    private Button classicButton;
    private Button poligonButton;
    private TextField text;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App(800, 600).start());
    }

    public void clear(int color) {
        raster.setClearColor(color);
        raster.clear();
    }

    public void present(Graphics graphics) {
        raster.repaint(graphics);
    }

    public void start() {
        clear(0xaaaaaa);
        panel.repaint();
    }

    public App(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());

        frame.setTitle("Delta : " + this.getClass().getName());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        raster = new RasterBufferedImage(width, height);

        panel = new JPanel() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        panel.requestFocus();
        panel.requestFocusInWindow();

        rasterizer = new TrivialRasterizer(Color.CYAN,raster);
        poligonRasterizer = new PoligonRasterizer(Color.ORANGE, raster);



                createAdapters();
        panel.addMouseListener(mouseAdapteraForClassic);
        panel.addMouseMotionListener(mouseAdapteraForClassic);
        panel.addKeyListener(keyAdapterClassic);


        classicButton = new Button("Classic");
        poligonButton = new Button("Poligon");

        panel.add(classicButton, BorderLayout.NORTH);
        panel.add(poligonButton, BorderLayout.SOUTH);


        text = new TextField("Click on button to enable mode, default mode is classic");
        text.setEditable(false);

        panel.add(text,BorderLayout.CENTER);

        classicButton.addMouseListener(mouseAdapterClassic);
        poligonButton.addMouseListener(mouseAdapterPoligon);

        classicButton.setForeground(classic ? Color.GREEN : Color.RED);
        poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);





    }

    private void createAdapters() {

        classic = true;
        poligon = false;


        mouseAdapterClassic = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                classic = true;
                poligon = false;
                //poligonButton.setEnabled(!poligonButton.isEnabled());
                classicButton.setForeground(classic ? Color.GREEN : Color.RED);
                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);


                panel.addMouseListener(mouseAdapteraForClassic);
                panel.addMouseMotionListener(mouseAdapteraForClassic);
                panel.addKeyListener(keyAdapterClassic);

                panel.removeMouseListener(mouseAdapterPoligon);
                panel.removeMouseMotionListener(mouseAdapterPoligon);
            }
        };


        mouseAdapterPoligon = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                poligon = true;
                classic = false;
                //classicButton.setEnabled(!classicButton.isEnabled());
                classicButton.setForeground(classic ? Color.GREEN : Color.RED);
                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);

                panel.addMouseListener(mouseAdapterPoligon);
                panel.addMouseMotionListener(mouseAdapterPoligon);

                panel.removeMouseListener(mouseAdapteraForClassic);
                panel.removeMouseMotionListener(mouseAdapteraForClassic);
                panel.removeKeyListener(keyAdapterClassic);
            }
        };

        List<Line> listOfLines = new ArrayList<>();
        lines = new Lines(listOfLines);

            mouseAdapteraForClassic = new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    pPomocny = new Point(e.getX(), e.getY());
                }

                @Override
                public void mouseDragged(MouseEvent e) {

                    Point pPomocny2 = new Point(e.getX(), e.getY());

                    Line line = new Line(pPomocny, pPomocny2, Color.WHITE, dotted, toAlign);

                    raster.clear();

                    lines.RasterizeLines(rasterizer);
                    rasterizer.rasterize(line);

                    panel.repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                    Point pPomocny2 = new Point(e.getX(), e.getY());

                    Line line = new Line(pPomocny, pPomocny2, Color.WHITE, dotted, toAlign);

                    lines.addLine(line);
                    panel.repaint();
                }

            };

        keyAdapterClassic = new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_C) {
                        raster.clear();
                        lines.eraseAllLines();
                        panel.repaint();
                    }

                    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {//cari jsou teckovany
                        dotted = true;
                    }
                    if (e.getKeyCode() == KeyEvent.VK_R) { //zmena dotted pro vsechny cary
                        raster.clear();
                        lines.ChangeDotted(rasterizer);
                        panel.repaint();
                    }

                    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                        toAlign = true;
                    }
                }



                @Override
                public void keyReleased(KeyEvent e) { //cary nejsou teckovany

                    if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                        dotted = false;

                    }

                    if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                        toAlign = false;
                    }
                }
            };
    }





}
