Assignment No: 9
Name: Parag Harish Gulve
Roll No:CO2178
Title of the Assignment: Write a C / C++ / Java program to implement the Cohen-Sutherland line clipping algorithm
CODE:
import java.awt.*;
import javax.swing.*;

public class CohenSutherlandClipping extends JFrame {
    private int xmin = 100, ymin = 100, xmax = 300, ymax = 300; // Clipping window
    private int[][] lines = {
        {50, 50, 350, 350},
        {150, 50, 150, 350},
        {50, 200, 350, 200},
        {200, 50, 200, 350}
    };

    public CohenSutherlandClipping() {
        setTitle("Cohen-Sutherland Line Clipping");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new DrawPanel());
    }

    class DrawPanel extends JPanel {
        private final int INSIDE = 0; // 0000
        private final int LEFT = 1;   // 0001
        private final int RIGHT = 2;  // 0010
        private final int BOTTOM = 4; // 0100
        private final int TOP = 8;    // 1000

        private int computeOutCode(int x, int y) {
            int code = INSIDE;

            if (x < xmin) code |= LEFT;
            else if (x > xmax) code |= RIGHT;
            if (y < ymin) code |= BOTTOM;
            else if (y > ymax) code |= TOP;

            return code;
        }

        private void cohenSutherlandClipAndDraw(Graphics g, int x0, int y0, int x1, int y1) {
            int outcode0 = computeOutCode(x0, y0);
            int outcode1 = computeOutCode(x1, y1);
            boolean accept = false;

            while (true) {
                if ((outcode0 | outcode1) == 0) { // Both inside
                    accept = true;
                    break;
                } else if ((outcode0 & outcode1) != 0) { // Both outside
                    break;
                } else {
                    int x = 0, y = 0;
                    int outcodeOut = (outcode0 != 0) ? outcode0 : outcode1;

                    if ((outcodeOut & TOP) != 0) {
                        x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
                        y = ymax;
                    } else if ((outcodeOut & BOTTOM) != 0) {
                        x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
                        y = ymin;
                    } else if ((outcodeOut & RIGHT) != 0) {
                        y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
                        x = xmax;
                    } else if ((outcodeOut & LEFT) != 0) {
                        y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
                        x = xmin;
                    }

                    if (outcodeOut == outcode0) {
                        x0 = x;
                        y0 = y;
                        outcode0 = computeOutCode(x0, y0);
                    } else {
                        x1 = x;
                        y1 = y;
                        outcode1 = computeOutCode(x1, y1);
                    }
                }
            }

            if (accept) {
                g.setColor(Color.RED);
                g.drawLine(x0, y0, x1, y1);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw clipping rectangle
            g.setColor(Color.BLACK);
            g.drawRect(xmin, ymin, xmax - xmin, ymax - ymin);

            // Draw original lines in blue
            g.setColor(Color.BLUE);
            for (int[] line : lines) {
                g.drawLine(line[0], line[1], line[2], line[3]);
            }

            // Draw clipped lines in red
            for (int[] line : lines) {
                cohenSutherlandClipAndDraw(g, line[0], line[1], line[2], line[3]);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CohenSutherlandClipping().setVisible(true);
        });
    }
}








OUTPUT:
 


