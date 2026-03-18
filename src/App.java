import models.*;
import models.Point;
import rasterizers.*;
import rasters.Raster;
import rasters.RasterBufferedImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serial;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class App
{

    private final JPanel panel;
    private final Raster raster;

    private Poligons poligons;



    private Rasterizer rasterizer;
    private Rasterizer poligonRasterizer;
    private Rasterizer quadRasterizer;
    private Rasterizer quadRasterizerDoneQuads;
    private CircleRasterizer circleRasterizer;
    private FloodFill floodFill;


    private MouseAdapter mouseAdapterClassic;
    private MouseAdapter mouseAdapterPoligon;
    private MouseAdapter mouseAdapterQuad;
    private MouseAdapter mouseAdapterCircle;
    private MouseAdapter mouseAdapterColor;
    private MouseAdapter mouseAdapterWidth;
    private MouseAdapter mouseAdapterFill;
    private MouseAdapter mouseAdapterEraser;


    private MouseAdapter mouseAdapterForClassic;
    private MouseAdapter mouseAdapterForPoligon;
    private MouseAdapter mouseAdapterForQuad;
    private MouseAdapter mouseAdapterForCircle;
    private MouseAdapter mouseAdapterForFill;
    private MouseAdapter mouseAdapterForEraser;


    private MouseMotionAdapter mouseMotionAdapter;
    private KeyAdapter keyAdapter;

    private Lines lines;
    private Circles circles;
    private Fills fills;
    private Quads quads;


    private ResizeHandle currentHandle;

    private boolean dotted;
    private boolean intermittent;

    private boolean toAlign;

    private Integer width = 1;
    private JSpinner jSpinner;

    private Point pPomocny;
    private Point pPomocny2;
    private Circle currentCircle;

    private Boolean classic;
    private Boolean poligon;
    private Boolean quad;
    private Boolean circle;
    private Boolean fill;
    private Boolean eraser;



    private Button classicButton;
    private Button poligonButton;
    private Button quadButton;
    private Button chooseColor;
    private Button chooseWidth;
    private Button circleButton;
    private Button fillButton;
    private Button eraserButton;



    private TextField text;

    private ArrayList<Point> listOfPoints;
    private Poligon currentPoligon;
    private ArrayList<Poligon> listOfPoligons;
    private ArrayList<Circle> listOfCircles;
    private ArrayList<Fill> listOfFills;
    private ArrayList<Quad> listOfQuads;


    private MouseAdapter mouseAdapterMove;
    private MouseAdapter mouseAdapterForMove;
    private Button moveButton;
    private boolean move;

    private Object selectedObject;
    private int lastMoveX, lastMoveY;




    private Color color = Color.WHITE;


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

        rasterizer = new TrivialRasterizer(color, raster);
        poligonRasterizer = new TrivialRasterizer(color, raster);
        quadRasterizer = new QuadRasterizer(color, raster);
        circleRasterizer = new CircleRasterizer(raster);
        quadRasterizer = new QuadRasterizer(color, raster);
        quadRasterizerDoneQuads = new TrivialRasterizer(color, raster);
        floodFill = new FloodFill(raster);

        createAdapters();

        panel.addMouseListener(mouseAdapterForClassic);
        panel.addMouseMotionListener(mouseAdapterForClassic);
        panel.addKeyListener(keyAdapter);

        classicButton = new Button("Classic");
        poligonButton = new Button("Poligon ");
        quadButton = new Button("Quad");
        circleButton = new Button("Circle");
        chooseColor = new Button("Choose Color");
        chooseWidth = new Button("Choose Width");
        fillButton = new Button("Fill");
        moveButton = new Button("Move");
        eraserButton = new Button("Delete");




        panel.add(classicButton, BorderLayout.NORTH);
        panel.add(poligonButton, BorderLayout.SOUTH);
        panel.add(quadButton, BorderLayout.EAST);
        panel.add(circleButton, BorderLayout.WEST);
        panel.add(chooseColor, BorderLayout.WEST);
        panel.add(chooseWidth, BorderLayout.NORTH);
        panel.add(fillButton, BorderLayout.WEST);
        panel.add(moveButton, BorderLayout.WEST);
        panel.add(eraserButton, BorderLayout.EAST);




        text = new TextField("Click on button to enable mode, default mode is classic");
        text.setEditable(false);

        panel.add(text, BorderLayout.CENTER);

        classicButton.addMouseListener(mouseAdapterClassic);
        poligonButton.addMouseListener(mouseAdapterPoligon);
        quadButton.addMouseListener(mouseAdapterQuad);
        circleButton.addMouseListener(mouseAdapterCircle);
        chooseColor.addMouseListener(mouseAdapterColor);
        chooseWidth.addMouseListener(mouseAdapterWidth);
        fillButton.addMouseListener(mouseAdapterFill);
        moveButton.addMouseListener(mouseAdapterMove);
        eraserButton.addMouseListener(mouseAdapterEraser);




        classicButton.setForeground(classic ? Color.GREEN : Color.RED);
        poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);
        quadButton.setForeground(quad ? Color.GREEN : Color.RED);
        circleButton.setForeground(circle ? Color.GREEN : Color.RED);
        fillButton.setForeground(fill ? Color.GREEN : Color.RED);
        moveButton.setForeground(move ? Color.GREEN : Color.RED);
        eraserButton.setForeground(eraser ? Color.GREEN : Color.RED);

    }

    private void createAdapters() {

        classic = true;
        poligon = false;
        quad = false;
        circle = false;
        fill = false;
        move = false;
        eraser = false;


        ArrayList<Line> listOfLines = new ArrayList<>();
        lines = new Lines(listOfLines);


        listOfPoints = new ArrayList<>();
        listOfPoligons = new ArrayList<>();

        listOfCircles = new ArrayList<>();
        circles = new Circles(listOfCircles);


        currentPoligon = new Poligon(listOfPoints, dotted, color, width, intermittent);
        listOfPoligons.add(currentPoligon);
        poligons = new Poligons(listOfPoligons);

        listOfFills = new ArrayList<>();
        fills = new Fills(listOfFills);

        listOfQuads = new ArrayList<>();
        quads = new Quads(listOfQuads);


        mouseAdapterClassic = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (classic) return;
                classic = true;
                poligon = false;
                quad = false;
                circle = false;
                fill = false;
                move = false;
                eraser = false;




                classicButton.setForeground(classic ? Color.GREEN : Color.RED);
                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);
                quadButton.setForeground(quad ? Color.GREEN : Color.RED);
                circleButton.setForeground(circle ? Color.GREEN : Color.RED);
                fillButton.setForeground(fill ? Color.GREEN : Color.RED);
                moveButton.setForeground(move ? Color.GREEN : Color.RED);
                eraserButton.setForeground(eraser ? Color.GREEN : Color.RED);



                panel.addMouseListener(mouseAdapterForClassic);
                panel.addMouseMotionListener(mouseAdapterForClassic);

                panel.removeMouseListener(mouseAdapterForPoligon);
                panel.removeMouseMotionListener(mouseAdapterForPoligon);

                panel.removeMouseListener(mouseAdapterForQuad);
                panel.removeMouseMotionListener(mouseAdapterForQuad);

                panel.removeMouseListener(mouseAdapterForCircle);
                panel.removeMouseMotionListener(mouseAdapterForCircle);

                panel.removeMouseListener(mouseAdapterForFill);
                panel.removeMouseMotionListener(mouseAdapterForFill);

                panel.removeMouseListener(mouseAdapterForMove);
                panel.removeMouseMotionListener(mouseAdapterForMove);

                panel.removeMouseListener(mouseAdapterForEraser);
                panel.removeMouseMotionListener(mouseAdapterForEraser);


                panel.requestFocusInWindow();
            }
        };


        mouseAdapterPoligon = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (poligon) return;
                poligon = true;
                classic = false;
                quad = false;
                circle = false;
                fill = false;
                move = false;
                eraser = false;

                classicButton.setForeground(classic ? Color.GREEN : Color.RED);
                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);
                quadButton.setForeground(quad ? Color.GREEN : Color.RED);
                circleButton.setForeground(circle ? Color.GREEN : Color.RED);
                fillButton.setForeground(fill ? Color.GREEN : Color.RED);
                moveButton.setForeground(move ? Color.GREEN : Color.RED);
                eraserButton.setForeground(eraser ? Color.GREEN : Color.RED);




                panel.addMouseListener(mouseAdapterForPoligon);
                panel.addMouseMotionListener(mouseAdapterForPoligon);

                panel.removeMouseListener(mouseAdapterForClassic);
                panel.removeMouseMotionListener(mouseAdapterForClassic);

                panel.removeMouseListener(mouseAdapterForQuad);
                panel.removeMouseMotionListener(mouseAdapterForQuad);

                panel.removeMouseListener(mouseAdapterForCircle);
                panel.removeMouseMotionListener(mouseAdapterForCircle);

                panel.removeMouseListener(mouseAdapterForFill);
                panel.removeMouseMotionListener(mouseAdapterForFill);

                panel.removeMouseListener(mouseAdapterForMove);
                panel.removeMouseMotionListener(mouseAdapterForMove);

                panel.removeMouseListener(mouseAdapterForEraser);
                panel.removeMouseMotionListener(mouseAdapterForEraser);

                panel.requestFocusInWindow();
            }
        };


        mouseAdapterQuad = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (quad) return;
                quad = true;
                classic = false;
                poligon = false;
                circle = false;
                fill = false;
                move = false;
                eraser = false;


                quadButton.setForeground(quad ? Color.GREEN : Color.RED);
                classicButton.setForeground(classic ? Color.GREEN : Color.RED);
                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);
                circleButton.setForeground(circle ? Color.GREEN : Color.RED);
                fillButton.setForeground(fill ? Color.GREEN : Color.RED);
                moveButton.setForeground(move ? Color.GREEN : Color.RED);
                eraserButton.setForeground(eraser ? Color.GREEN : Color.RED);



                panel.addMouseListener(mouseAdapterForQuad);
                panel.addMouseMotionListener(mouseAdapterForQuad);

                panel.removeMouseListener(mouseAdapterForClassic);
                panel.removeMouseMotionListener(mouseAdapterForClassic);

                panel.removeMouseListener(mouseAdapterForPoligon);
                panel.removeMouseMotionListener(mouseAdapterForPoligon);

                panel.removeMouseListener(mouseAdapterForCircle);
                panel.removeMouseMotionListener(mouseAdapterForCircle);

                panel.removeMouseListener(mouseAdapterForFill);
                panel.removeMouseMotionListener(mouseAdapterForFill);

                panel.removeMouseListener(mouseAdapterForMove);
                panel.removeMouseMotionListener(mouseAdapterForMove);

                panel.removeMouseListener(mouseAdapterForEraser);
                panel.removeMouseMotionListener(mouseAdapterForEraser);

                panel.requestFocusInWindow();

            }
        };

        mouseAdapterCircle = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (circle) return;
                circle = true;
                poligon = false;
                classic = false;
                quad = false;
                fill = false;
                move = false;
                eraser = false;


                circleButton.setForeground(circle ? Color.GREEN : Color.RED);
                classicButton.setForeground(classic ? Color.GREEN : Color.RED);
                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);
                quadButton.setForeground(quad ? Color.GREEN : Color.RED);
                fillButton.setForeground(fill ? Color.GREEN : Color.RED);
                moveButton.setForeground(move ? Color.GREEN : Color.RED);
                eraserButton.setForeground(eraser ? Color.GREEN : Color.RED);



                panel.addMouseListener(mouseAdapterForCircle);
                panel.addMouseMotionListener(mouseAdapterForCircle);

                panel.removeMouseListener(mouseAdapterForClassic);
                panel.removeMouseMotionListener(mouseAdapterForClassic);

                panel.removeMouseListener(mouseAdapterForPoligon);
                panel.removeMouseMotionListener(mouseAdapterForPoligon);

                panel.removeMouseListener(mouseAdapterForQuad);
                panel.removeMouseMotionListener(mouseAdapterForQuad);

                panel.removeMouseListener(mouseAdapterForFill);
                panel.removeMouseMotionListener(mouseAdapterForFill);

                panel.removeMouseListener(mouseAdapterForMove);
                panel.removeMouseMotionListener(mouseAdapterForMove);

                panel.removeMouseListener(mouseAdapterForEraser);
                panel.removeMouseMotionListener(mouseAdapterForEraser);



                panel.requestFocusInWindow();


            }

        };


        mouseAdapterColor = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color newColor = JColorChooser.showDialog(null, "Choose color", color);
                if (newColor != null) {
                    color = newColor;
                }
            }
        };

        mouseAdapterWidth = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 10, 1);
                JSpinner jSpinner = new JSpinner(model);


                jSpinner.setSize(jSpinner.getPreferredSize());
                jSpinner.setLocation(e.getX(), e.getY());


                jSpinner.addChangeListener(ce -> {
                    int newWidth = (int) jSpinner.getValue();
                    width = newWidth;
                });


                panel.add(jSpinner);
                panel.revalidate();
                panel.repaint();


            }
        };

        mouseAdapterFill = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (fill) return;
                fill = true;
                classic = false;
                poligon = false;
                quad = false;
                circle = false;
                move = false;
                eraser = false;

                fillButton.setForeground(Color.GREEN);
                classicButton.setForeground(Color.RED);
                poligonButton.setForeground(Color.RED);
                quadButton.setForeground(Color.RED);
                circleButton.setForeground(Color.RED);
                moveButton.setForeground(Color.RED);

                panel.addMouseListener(mouseAdapterForFill);
                panel.addMouseMotionListener(mouseAdapterForFill);

                panel.removeMouseListener(mouseAdapterForClassic);
                panel.removeMouseMotionListener(mouseAdapterForClassic);

                panel.removeMouseListener(mouseAdapterForPoligon);
                panel.removeMouseMotionListener(mouseAdapterForPoligon);

                panel.removeMouseListener(mouseAdapterForQuad);
                panel.removeMouseMotionListener(mouseAdapterForQuad);

                panel.removeMouseListener(mouseAdapterForCircle);
                panel.removeMouseMotionListener(mouseAdapterForCircle);

                panel.removeMouseListener(mouseAdapterForMove);
                panel.removeMouseMotionListener(mouseAdapterForMove);

                panel.removeMouseListener(mouseAdapterForEraser);
                panel.removeMouseMotionListener(mouseAdapterForEraser);

                panel.requestFocusInWindow();
            }
        };

        mouseAdapterMove = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (move) return;
                move = true;
                classic = false;
                poligon = false;
                quad = false;
                circle = false;
                fill = false;
                eraser = false;

                moveButton.setForeground(Color.GREEN);
                classicButton.setForeground(Color.RED);
                poligonButton.setForeground(Color.RED);
                quadButton.setForeground(Color.RED);
                circleButton.setForeground(Color.RED);
                fillButton.setForeground(Color.RED);
                eraserButton.setForeground(Color.RED);

                panel.addMouseListener(mouseAdapterForMove);
                panel.addMouseMotionListener(mouseAdapterForMove);

                panel.removeMouseListener(mouseAdapterForClassic);
                panel.removeMouseMotionListener(mouseAdapterForClassic);

                panel.removeMouseListener(mouseAdapterForPoligon);
                panel.removeMouseMotionListener(mouseAdapterForPoligon);

                panel.removeMouseListener(mouseAdapterForQuad);
                panel.removeMouseMotionListener(mouseAdapterForQuad);

                panel.removeMouseListener(mouseAdapterForCircle);
                panel.removeMouseMotionListener(mouseAdapterForCircle);

                panel.removeMouseListener(mouseAdapterForFill);
                panel.removeMouseMotionListener(mouseAdapterForFill);

                panel.removeMouseListener(mouseAdapterForEraser);
                panel.removeMouseMotionListener(mouseAdapterForEraser);

                panel.requestFocusInWindow();
            }
        };

        mouseAdapterEraser = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (eraser) return;
                eraser = true;
                classic = false; poligon = false; quad = false;
                circle = false; fill = false; move = false;

                eraserButton.setForeground(Color.GREEN);
                classicButton.setForeground(Color.RED);
                poligonButton.setForeground(Color.RED);
                quadButton.setForeground(Color.RED);
                circleButton.setForeground(Color.RED);
                fillButton.setForeground(Color.RED);
                moveButton.setForeground(Color.RED);

                panel.addMouseListener(mouseAdapterForEraser);
                panel.addMouseMotionListener(mouseAdapterForEraser);
                panel.removeMouseListener(mouseAdapterForClassic);
                panel.removeMouseMotionListener(mouseAdapterForClassic);
                panel.removeMouseListener(mouseAdapterForPoligon);
                panel.removeMouseMotionListener(mouseAdapterForPoligon);
                panel.removeMouseListener(mouseAdapterForQuad);
                panel.removeMouseMotionListener(mouseAdapterForQuad);
                panel.removeMouseListener(mouseAdapterForCircle);
                panel.removeMouseMotionListener(mouseAdapterForCircle);
                panel.removeMouseListener(mouseAdapterForFill);
                panel.removeMouseMotionListener(mouseAdapterForFill);
                panel.removeMouseListener(mouseAdapterForMove);
                panel.removeMouseMotionListener(mouseAdapterForMove);
                panel.requestFocusInWindow();
            }
        };




        mouseAdapterForClassic = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                pPomocny = new Point(e.getX(), e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                pPomocny2 = new Point(e.getX(), e.getY());

                Line line = new Line(pPomocny, pPomocny2, color, dotted, toAlign, width, intermittent);


                redraw();

                rasterizer.rasterize(line);

                panel.repaint();


            }

            @Override
            public void mouseReleased(MouseEvent e) {

                Point pPomocny2 = new Point(e.getX(), e.getY());

                Line line = new Line(pPomocny, pPomocny2, color, dotted, toAlign, width, intermittent);

                lines.addLine(line);
                panel.repaint();
            }

        };

        keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_C) {


                    raster.clear();
                    poligons.erasAllLines();
                    lines.eraseAllLines();
                    circles.eraseAllCircles();
                    fills.eraseAllFills();
                    quads.eraseAll();


                    listOfPoints = new ArrayList<>();
                    currentPoligon = new Poligon(listOfPoints, dotted, color,width, intermittent);
                    listOfPoligons.add(currentPoligon);

                    panel.repaint();
                }

                if (e.getKeyCode() == KeyEvent.VK_R) { //zmena dotted pro vsechny cary
                    raster.clear();
                    poligons.ChangeDotted(poligonRasterizer);
                    lines.ChangeDotted(rasterizer);
                    circles.changeDotted(circleRasterizer);


                    panel.repaint();
                }


                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {//cari jsou teckovany
                    dotted = true;
                }

                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    toAlign = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_Q){
                    intermittent = true;
                }

                if(e.getKeyCode() == KeyEvent.VK_SPACE){

                    listOfPoints = new ArrayList<>();
                    currentPoligon = new Poligon(listOfPoints, dotted, color,width, intermittent);

                    listOfPoligons.add(currentPoligon);
                    panel.repaint();
                    poligonButton.setForeground(color);

                }

            }


            @Override
            public void keyReleased(KeyEvent e) { //cary nejsou teckovany

                if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                    dotted = false;
                }

                if(e.getKeyCode() == KeyEvent.VK_Q){
                    intermittent = false;
                }

                if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                    toAlign = false;
                }
            }
        };

        mouseAdapterForPoligon = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                raster.clear();
                pPomocny = new Point(e.getX(), e.getY());
                listOfPoints.add(pPomocny);
                currentPoligon.setDotted(dotted);


                redraw();

                poligonButton.setForeground(poligon ? Color.GREEN : Color.RED);

            }
        };

        mouseAdapterForQuad = new MouseAdapter() {


            @Override
            public void mousePressed(MouseEvent e) {
                pPomocny =  new Point(e.getX(), e.getY());

            }
            @Override
            public void mouseReleased(MouseEvent e) {



                pPomocny2 = new Point(e.getX(), e.getY());

                int x1 = pPomocny.getX();
                int y1 = pPomocny.getY();

                int x2 = pPomocny2.getX();
                int y2 = pPomocny2.getY();


                int difX = Math.abs(x2 - x1);
                int difY = Math.abs(y2 - y1);

                if(toAlign){

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
                    pPomocny2 = new Point(x2, y2);
                }


                Point point1 = new Point(pPomocny.getX(), pPomocny.getY());
                Point point2 = new Point(pPomocny.getX(), pPomocny2.getY());
                Point point3 = new Point(pPomocny2.getX(), pPomocny2.getY());
                Point point4 = new Point(pPomocny2.getX(), pPomocny.getY());


                Line line1 = new Line(point1, point2, color, dotted, false, width, intermittent);
                Line line2 = new Line(point2, point3, color,dotted, false, width, intermittent);
                Line line3 = new Line(point3, point4, color, dotted, false, width, intermittent);
                Line line4 = new Line(point4, point1, color, dotted, false, width, intermittent);



                quads.addQuad(new Quad(line1, line2, line3, line4));

                redraw();


            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (pPomocny == null) return; // ochrana

                pPomocny2 = new Point(e.getX(), e.getY());


                Line line = new Line(pPomocny, pPomocny2, color, dotted, toAlign, width, intermittent);


                redraw();


                quadRasterizer.rasterize(line);
                panel.repaint();

            }
        };

        mouseAdapterForCircle = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                pPomocny = new Point(e.getX(), e.getY());
            }
            public void mouseReleased(MouseEvent e) {
                pPomocny2 = new Point(e.getX(), e.getY());

                listOfCircles.add(currentCircle);
                currentCircle = null;


                redraw();


            }

            @Override
            public void mouseDragged(MouseEvent e) {




                pPomocny2 = new Point(e.getX(), e.getY());

                currentCircle = new Circle(pPomocny, pPomocny2, color, width, dotted, intermittent);
                redraw();
                circleRasterizer.rasterize(currentCircle);
                panel.repaint();

            }
        };

        mouseAdapterForFill = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                redraw();

                ArrayList<int[]> pixels = floodFill.fillAndCollect(e.getX(), e.getY(), color);
                Fill fill = new Fill(pixels, color);
                fills.addFill(fill);

            }
        };


        mouseAdapterForMove = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                int mx = e.getX(), my = e.getY();
                lastMoveX = mx;
                lastMoveY = my;
                selectedObject = null;
                currentHandle = null;

                // 1) Zkus nejdřív resize handle
                currentHandle = ResizeHandle.find(mx, my,
                        listOfCircles, (ArrayList<Line>) lines.lines,
                        listOfQuads, listOfPoligons);

                if (currentHandle != null) return; // resize má přednost

                // 2) Jinak fallback na celý move (stejný kód jako dřív)
                for (Circle c : listOfCircles) {
                    if (HitTester.hitCircle(c, mx, my)) { selectedObject = c; return; }
                }
                for (Poligon p : listOfPoligons) {
                    if (HitTester.hitPoligon(p, mx, my)) { selectedObject = p; return; }
                }
                for (Fill f : listOfFills) {
                    if (HitTester.hitFill(f, mx, my)) { selectedObject = f; return; }
                }
                for (Line l : (ArrayList<Line>) lines.lines) {
                    if (HitTester.hitLine(l, mx, my)) { selectedObject = l; return; }
                }
                for (Quad q : listOfQuads) {
                    if (HitTester.hitQuad(q.getL1(), q.getL2(),
                            q.getL3(), q.getL4(), mx, my)) {
                        selectedObject = q; return;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - lastMoveX;
                int dy = e.getY() - lastMoveY;
                lastMoveX = e.getX();
                lastMoveY = e.getY();

                if (currentHandle != null) {

                    currentHandle.applyDrag(e.getX(), e.getY());
                } else if (selectedObject != null) {

                    if (selectedObject instanceof Line)    ((Line)    selectedObject).move(dx, dy);
                    if (selectedObject instanceof Circle)  ((Circle)  selectedObject).move(dx, dy);
                    if (selectedObject instanceof Poligon) ((Poligon) selectedObject).move(dx, dy);
                    if (selectedObject instanceof Fill)    ((Fill)    selectedObject).move(dx, dy);
                    if (selectedObject instanceof Quad)    ((Quad)    selectedObject).move(dx, dy);
                } else {
                    return;
                }

                redraw();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedObject = null;
                currentHandle = null;
            }
        };

        mouseAdapterForEraser = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mx = e.getX(), my = e.getY();

                // Kružnice
                for (int i = listOfCircles.size() - 1; i >= 0; i--) {
                    if (HitTester.hitCircle(listOfCircles.get(i), mx, my)) {
                        listOfCircles.remove(i);
                        redraw();
                        return;
                    }
                }

                // Polygony
                for (int i = listOfPoligons.size() - 1; i >= 0; i--) {
                    if (HitTester.hitPoligon(listOfPoligons.get(i), mx, my)) {
                        listOfPoligons.remove(i);
                        redraw();
                        return;
                    }
                }

                // Čáry
                for (int i = lines.lines.size() - 1; i >= 0; i--) {
                    if (HitTester.hitLine(lines.lines.get(i), mx, my)) {
                        lines.lines.remove(i);
                        redraw();
                        return;
                    }
                }

                // Quady
                for (int i = listOfQuads.size() - 1; i >= 0; i--) {
                    Quad q = listOfQuads.get(i);
                    if (HitTester.hitLine(q.getL1(), mx, my) ||
                            HitTester.hitLine(q.getL2(), mx, my) ||
                            HitTester.hitLine(q.getL3(), mx, my) ||
                            HitTester.hitLine(q.getL4(), mx, my)) {
                        listOfQuads.remove(i);
                        redraw();
                        return;
                    }
                }

                // Výplně
                for (int i = listOfFills.size() - 1; i >= 0; i--) {
                    if (HitTester.hitFill(listOfFills.get(i), mx, my)) {
                        listOfFills.remove(i);
                        redraw();
                        return;
                    }
                }
            }
        };
    }

    private void redraw() {
        raster.clear();
        fills.rasterizeFills(raster);
        quads.rasterizeQuads(quadRasterizerDoneQuads);
        lines.RasterizeLines(rasterizer);
        poligons.rasterizePoligons(poligonRasterizer);
        circles.RasterizeCircles(circleRasterizer);
        panel.repaint();
    }


}


