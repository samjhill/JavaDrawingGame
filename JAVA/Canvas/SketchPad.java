 

import java.awt.*;
import java.awt.RenderingHints;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class SketchPad {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        final DrawPad drawPad = new DrawPad();
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                drawPad.clear();
            }
        });

        JPanel jpWest = new JPanel();
        jpWest.setLayout(new BorderLayout(0,100));
        jpWest.setBackground(Color.WHITE);
            JPanel jpWestNorthItems = new JPanel();
            jpWestNorthItems.setLayout(new GridLayout(0,2));
            /////////////////////////
            JButton redColor = new JButton();
            redColor.setBackground(Color.RED);
            redColor.setToolTipText("Red");
            redColor.setPreferredSize(new Dimension(50, 25));
            redColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("red");
                }
            });
            jpWestNorthItems.add(redColor);
            /////////////////////////
            JButton blueColor = new JButton();
            blueColor.setBackground(Color.BLUE);
            blueColor.setToolTipText("Blue");
            blueColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("blue");
                }
            });
            jpWestNorthItems.add(blueColor);
            ////////////////////////
            JButton blackColor = new JButton();
            blackColor.setBackground(Color.BLACK);
            blackColor.setToolTipText("Black");
            blackColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("black");
                }
            });
            jpWestNorthItems.add(blackColor);
            ////////////////////////
            JButton darkgreyColor = new JButton();
            darkgreyColor.setBackground(Color.DARK_GRAY);
            darkgreyColor.setToolTipText("Dark Gray");
            darkgreyColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("dark_gray");
                }
            });
            jpWestNorthItems.add(darkgreyColor);
            ////////////////////////
            JButton cyanColor = new JButton();
            cyanColor.setBackground(Color.CYAN);
            cyanColor.setToolTipText("Cyan");
            cyanColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("cyan");
                }
            });
            jpWestNorthItems.add(cyanColor);
            ////////////////////////
            JButton grayColor = new JButton();
            grayColor.setBackground(Color.GRAY);
            grayColor.setToolTipText("Gray");
            grayColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("gray");
                }
            });
            jpWestNorthItems.add(grayColor);
            ////////////////////////
            JButton greenColor = new JButton();
            greenColor.setBackground(Color.GREEN);
            greenColor.setToolTipText("Green");
            greenColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("green");
                }
            });
            jpWestNorthItems.add(greenColor);
            ////////////////////////
            JButton lightgrayColor = new JButton();
            lightgrayColor.setBackground(Color.LIGHT_GRAY);
            lightgrayColor.setToolTipText("Light Gray");
            lightgrayColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("light_gray");
                }
            });
            jpWestNorthItems.add(lightgrayColor);
            ////////////////////////
            JButton magentaColor = new JButton();
            magentaColor.setBackground(Color.MAGENTA);
            magentaColor.setToolTipText("Magenta");
            magentaColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("magenta");
                }
            });
            jpWestNorthItems.add(magentaColor);
            ////////////////////////
            JButton orangeColor = new JButton();
            orangeColor.setBackground(Color.ORANGE);
            orangeColor.setToolTipText("Orange");
            orangeColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("orange");
                }
            });
            jpWestNorthItems.add(orangeColor);
            ////////////////////////
            JButton yellowColor = new JButton();
            yellowColor.setBackground(Color.YELLOW);
            yellowColor.setToolTipText("Yellow");
            yellowColor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.changeColor("yellow");
                }
            });
            jpWestNorthItems.add(yellowColor);
            ////////////////////////
        jpWest.add(jpWestNorthItems, BorderLayout.NORTH);
        
            JPanel jpWestSouthItems = new JPanel();
            jpWestSouthItems.setLayout(new GridLayout(0,2));
            ///////////////////////
            JButton ovalDraw = new JButton("Oval");
            ovalDraw.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.setDrawMethod("oval");
                }
            });
            jpWestSouthItems.add(ovalDraw);
            ///////////////////////
            JButton eraseDraw = new JButton("Erase");
            eraseDraw.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    drawPad.setDrawMethod("erase");
                }
            });
            jpWestSouthItems.add(eraseDraw);
            ///////////////////////
        jpWest.add(jpWestSouthItems, BorderLayout.SOUTH);
        
        frame.add(jpWest, BorderLayout.WEST);
        frame.add(clearButton, BorderLayout.SOUTH);
        frame.add(drawPad, BorderLayout.CENTER);
        
        Cursor cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
        frame.setCursor(cursor);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

class DrawPad extends JComponent {
    Image image;
    Graphics2D graphics2D;
    int currentX, currentY, oldX, oldY;
    Point selectedPoint;
    Point dragPoint;
    boolean makeEllipse;
    Color color = Color.BLACK;
    String drawMethod;
    String fill;
    Boolean eraser = false;
    public DrawPad() {
        setDoubleBuffered(false);
        addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent e) {
            oldX = e.getX();
            oldY = e.getY();
            graphics2D.setPaint(color);
            onMousePressed(e);
          }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
            currentX = e.getX();
            currentY = e.getY();
            onMouseDragged(e);
            if (graphics2D != null)
                graphics2D.drawLine(oldX, oldY, currentX, currentY);
            dragPoint = e.getPoint();
            repaint();
            oldX = currentX;
            oldY = currentY;
          }
        });
    }

    public void paintComponent(Graphics g) {
        if (image == null) {
          image = createImage(getSize().width, getSize().height);
          graphics2D = (Graphics2D) image.getGraphics();
          graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
              RenderingHints.VALUE_ANTIALIAS_ON);
          clear();
        }
        g.drawImage(image, 0, 0, null);
        if(eraser == true){
            graphics2D.setColor(Color.WHITE);
            graphics2D.fillRect(currentX-15,currentY-15,30,30);
        }//end of eraser
        if ((selectedPoint != null) && (dragPoint != null) && drawMethod=="oval") {
            makeEllipse = true;
			graphics2D.draw(new Ellipse2D.Float(currentX,currentY,oldX,oldY));
		}
    }

    public void clear() {
        graphics2D.setPaint(Color.white);
        graphics2D.fillRect(0, 0, getSize().width, getSize().height);
        graphics2D.setPaint(Color.black);
        selectedPoint = null;
		dragPoint = null;
        repaint();
    }
    
    public void setDrawMethod(String _draw) {
        if( _draw.equals("oval")) {
            drawMethod = "oval";
            //draw(graphics2D);
        }
        if( _draw.equals("erase")) {
            eraser = true;
        }
    }
    
    public void onMousePressed(MouseEvent e) {
			selectedPoint = e.getPoint();
	}
	
	public void onMouseReleased(MouseEvent e) {
		if (selectedPoint != null) {
			//Shape s = makeShape(selectedPoint, e.getPoint());
			makeEllipse = !makeEllipse;
			//shapes.add(s);
			selectedPoint = null;
			dragPoint = null;
			makeEllipse = false;
		}
		repaint();
	}
	
	public void onMouseDragged(MouseEvent e) {
		dragPoint = e.getPoint();
		repaint();
	}
	
	private Shape makeShape(Point p1, Point p2) {
		double x1 = validX(p1.getX());
		double x2 = validX(p2.getX());
		double x = x1;
		double w = x2 - x1;
		if (w < 0) {
			x = x2;
			w = -w;
		}
		double y1 = validY(p1.getY());
		double y2 = validY(p2.getY());
		double y = y1;
		double h = y2 - y;
		if (h < 0) {
			y = y2;
			h = -h;
		}
		if (makeEllipse)
			return new Ellipse2D.Double(x, y, w, h);
		else
			return new Rectangle2D.Double(x, y, w, h);
	}
	
	private double validX(double x) {
		Insets insets = getInsets();
		int minX = insets.left;
		int maxX = getWidth() - insets.right - 1;
		if (x < minX)
			return (double) minX;
		else if (x > maxX)
			return (double) maxX;
		else
			return x;
	}
	
	private double validY(double y) {
		Insets insets = getInsets();
		int minY = insets.top;
		int maxY = getHeight() - insets.bottom - 1;
		if (y < minY)
			return (double) minY;
		else if (y > maxY)
			return (double) maxY;
		else
			return y;
	}
    
    public void changeColor(String _color) {
        if( _color.equals("red")) {
            color = Color.RED;
            eraser = false;
        }
        if( _color.equals("blue")) {
            color = Color.BLUE;
            eraser = false;
        }
        if( _color.equals("black")) {
            color = Color.BLACK;
            eraser = false;
        }
        if( _color.equals("cyan")) {
            color = Color.CYAN;
            eraser = false;
        }
        if( _color.equals("dark_gray")) {
            color = Color.DARK_GRAY;
            eraser = false;
        }
        if( _color.equals("gray")) {
            color = Color.GRAY;
            eraser = false;
        }
        if( _color.equals("green")) {
            color = Color.GREEN;
            eraser = false;
        }
        if( _color.equals("light_gray")) {
            color = Color.LIGHT_GRAY;
            eraser = false;
        }
        if( _color.equals("magenta")) {
            color = Color.MAGENTA;
            eraser = false;
        }
        if( _color.equals("orange")) {
            color = Color.ORANGE;
            eraser = false;
        }
        if( _color.equals("yellow")) {
            color = Color.YELLOW;
            eraser = false;
        }
    }
}