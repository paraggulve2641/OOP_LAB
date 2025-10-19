Name: Parag Harish Gulve
Roll No: CO2178

Q.Write a C/C++/Java program to draw the following pattern using (a) the DDA line drawing algorithm for both rectangles with Dotted, Thick line style and (b) Bresenham‘s line drawing algorithm for a diamond shape with Dashed, Solid line style.

import javax.swing.*;
import java.awt.*;

/**
 * Main application class and the entry point for the program.
 * Contains the main method and sets up the JFrame.
 */
public class GraphicsPattern {
    public static void main(String[] args) {
        // Ensure the Swing operations run on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Line Drawing Algorithms Pattern");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Add the custom drawing panel (which contains all the logic)
            frame.add(new DrawingPanel());

            frame.pack(); // Size the frame to fit the preferred size of the panel
            frame.setLocationRelativeTo(null); // Center the frame on the screen
            frame.setVisible(true);
        });
    }
}

/**
 * Custom JPanel where all the line drawing and styling logic is implemented.
 */
class DrawingPanel extends JPanel {
    private final int W = 400; // Width of the drawing area
    private final int H = 300; // Height of the drawing area

    public DrawingPanel() {
        setPreferredSize(new Dimension(W, H));
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Define Coordinates (based on W=400, H=300)
        // Outer Rectangle: (50, 50) to (350, 250)
        int rect1_x1 = 50, rect1_y1 = 50, rect1_x2 = 350, rect1_y2 = 250;
        // Inner Rectangle: (125, 100) to (275, 200)
        int rect2_x1 = 125, rect2_y1 = 100, rect2_x2 = 275, rect2_y2 = 200;
        // Diamond: Vertices (200, 50), (350, 150), (200, 250), (50, 150)
        int diamond_ax = 200, diamond_ay = 50;
        int diamond_bx = 350, diamond_by = 150;
        int diamond_cx = 200, diamond_cy = 250;
        int diamond_dx = 50, diamond_dy = 150;

        g2d.setColor(Color.BLUE);

        // (a) DDA Line Drawing for Rectangles with Dotted, Thick line style
        // Outer Rectangle
        drawDDA_Line(g2d, rect1_x1, rect1_y1, rect1_x2, rect1_y1, true, true); // Top
        drawDDA_Line(g2d, rect1_x2, rect1_y1, rect1_x2, rect1_y2, true, true); // Right
        drawDDA_Line(g2d, rect1_x2, rect1_y2, rect1_x1, rect1_y2, true, true); // Bottom
        drawDDA_Line(g2d, rect1_x1, rect1_y2, rect1_x1, rect1_y1, true, true); // Left

        // Inner Rectangle
        drawDDA_Line(g2d, rect2_x1, rect2_y1, rect2_x2, rect2_y1, true, true); // Top
        drawDDA_Line(g2d, rect2_x2, rect2_y1, rect2_x2, rect2_y2, true, true); // Right
        drawDDA_Line(g2d, rect2_x2, rect2_y2, rect2_x1, rect2_y2, true, true); // Bottom
        drawDDA_Line(g2d, rect2_x1, rect2_y2, rect2_x1, rect2_y1, true, true); // Left

        // (b) Bresenham's Line Drawing for Diamond with Dashed, Solid line style
        g2d.setColor(Color.RED);
        drawBresenham_Line(g2d, diamond_ax, diamond_ay, diamond_bx, diamond_by, true); // A to B
        drawBresenham_Line(g2d, diamond_bx, diamond_by, diamond_cx, diamond_cy, true); // B to C
        drawBresenham_Line(g2d, diamond_cx, diamond_cy, diamond_dx, diamond_dy, true); // C to D
        drawBresenham_Line(g2d, diamond_dx, diamond_dy, diamond_ax, diamond_ay, true); // D to A
    }

    /**
     * Plots a single pixel with optional thickness (3x3 neighborhood).
     */
    private void plotPixel(Graphics2D g2d, int x, int y, boolean thick) {
        if (thick) {
            // Thick line (3x3 neighborhood)
            g2d.fillRect(x - 1, y - 1, 3, 3);
        } else {
            // Thin line (single pixel)
            g2d.drawLine(x, y, x, y);
        }
    }

    /**
     * DDA Line Drawing Algorithm Implementation.
     */
    private void drawDDA_Line(Graphics2D g2d, int x1, int y1, int x2, int y2, boolean dotted, boolean thick) {
        double dx = x2 - x1;
        double dy = y2 - y1;

        double steps = Math.max(Math.abs(dx), Math.abs(dy));

        double x_inc = dx / steps;
        double y_inc = dy / steps;

        double x = x1;
        double y = y1;

        int dot_counter = 0;
        int DOT_SKIP = 6; // Plot 3, Skip 3 (for Dotted style)

        for (int i = 0; i <= steps; i++) {
            // Apply Dotted style: Plot only for the first half of the skip cycle
            if (!dotted || (dot_counter % DOT_SKIP < DOT_SKIP / 2)) {
                plotPixel(g2d, (int) Math.round(x), (int) Math.round(y), thick);
            }
            dot_counter++;

            x += x_inc;
            y += y_inc;
        }
    }

    /**
     * Bresenham's Line Drawing Algorithm Implementation (simplified octant handling).
     */
    private void drawBresenham_Line(Graphics2D g2d, int x1, int y1, int x2, int y2, boolean dashed) {
        // --- Setup for General Bresenham's ---
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        boolean is_steep = dy > dx; // Check if slope > 1

        if (is_steep) {
            // Swap x and y coordinates and signs if slope is steep
            int temp = dx; dx = dy; dy = temp;
            temp = x1; x1 = y1; y1 = temp;
            temp = x2; x2 = y2; y2 = temp;
            temp = sx; sx = sy; sy = temp;
        }

        // --- Core Algorithm ---
        int p = 2 * dy - dx;
        int twoDy = 2 * dy;
        int twoDyMinusTwoDx = 2 * (dy - dx);
        int x = x1, y = y1;

        // --- Dashed Style Logic ---
        int dash_counter = 0;
        int DASH_SKIP = 12; // Cycle length
        int dash_plot_length = 8; // Plot 8, Skip 4

        for (int i = 0; i <= dx; i++) {
            // Map the coordinates back if they were swapped (steep line)
            int plotX = is_steep ? y : x;
            int plotY = is_steep ? x : y;

            // Apply Dashed style: Plot only during the plotting phase
            if (!dashed || (dash_counter % DASH_SKIP < dash_plot_length)) {
                // Draws a single pixel (Solid line style requirement)
                g2d.drawLine(plotX, plotY, plotX, plotY);
            }
            dash_counter++;

            // Update decision parameter and coordinates
            if (p < 0) {
                p += twoDy;
            } else {
                p += twoDyMinusTwoDx;
                y += sy;
            }
            x += sx;
        }
    }
}




O/P:

 
