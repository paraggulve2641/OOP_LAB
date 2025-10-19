
Name : Parag Harish Gulve
Roll No.: CO2178

Qs. Write a menu driven program in C/C++/Java to draw circle using DDA, Bresenham’s, Midpoint circle drawing algorithm with different styles as solid, dotted and dashed circles


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CircleDrawingMenu extends JFrame implements ActionListener {
    private JComboBox<String> algoBox, styleBox;
    private DrawingPanel drawingPanel;

    public CircleDrawingMenu() {
        setTitle("Circle Drawing Algorithms");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top control panel
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Algorithm:"));
        algoBox = new JComboBox<>(new String[]{"DDA Circle", "Bresenham Circle", "Midpoint Circle"});
        topPanel.add(algoBox);

        topPanel.add(new JLabel("Style:"));
        styleBox = new JComboBox<>(new String[]{"Solid", "Dashed"});
        topPanel.add(styleBox);

        JButton drawBtn = new JButton("Draw");
        drawBtn.addActionListener(this);
        topPanel.add(drawBtn);

        add(topPanel, BorderLayout.NORTH);

        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String algo = (String) algoBox.getSelectedItem();
        String style = (String) styleBox.getSelectedItem();
        drawingPanel.setOptions(algo, style);
        drawingPanel.repaint();
    }

    // Inner class for drawing
    class DrawingPanel extends JPanel {
        private String algorithm = "DDA Circle";
        private String style = "Solid";

        public void setOptions(String algo, String style) {
            this.algorithm = algo;
            this.style = style;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.BLACK);
            g.setColor(Color.CYAN);

            int xc = getWidth() / 2;
            int yc = getHeight() / 2;
            int r = 150;

            switch (algorithm) {
                case "DDA Circle":
                    drawDDACircle(g, xc, yc, r);
                    break;
                case "Bresenham Circle":
                    drawBresenhamCircle(g, xc, yc, r);
                    break;
                case "Midpoint Circle":
                    drawMidpointCircle(g, xc, yc, r);
                    break;
            }
        }

        private void putPixel(Graphics g, int x, int y, int xc, int yc, int count) {
            if (style.equals("Dashed") && (count % 10 > 5)) return;
            g.fillRect(x + xc, y + yc, 2, 2);
            g.fillRect(y + xc, x + yc, 2, 2);
            g.fillRect(y + xc, -x + yc, 2, 2);
            g.fillRect(x + xc, -y + yc, 2, 2);
            g.fillRect(-x + xc, -y + yc, 2, 2);
            g.fillRect(-y + xc, -x + yc, 2, 2);
            g.fillRect(-y + xc, x + yc, 2, 2);
            g.fillRect(-x + xc, y + yc, 2, 2);
        }

        private void drawDDACircle(Graphics g, int xc, int yc, int r) {
            double x = 0, y = r;
            double step = 1.0 / r;
            int count = 0;
            for (double t = 0; t <= 2 * Math.PI; t += step) {
                x = r * Math.cos(t);
                y = r * Math.sin(t);
                putPixel(g, (int) x, (int) y, xc, yc, count++);
            }
        }

        private void drawBresenhamCircle(Graphics g, int xc, int yc, int r) {
            int x = 0, y = r;
            int d = 3 - 2 * r;
            int count = 0;
            while (x <= y) {
                putPixel(g, x, y, xc, yc, count++);
                if (d < 0)
                    d = d + 4 * x + 6;
                else {
                    d = d + 4 * (x - y) + 10;
                    y--;
                }
                x++;
            }
        }

        private void drawMidpointCircle(Graphics g, int xc, int yc, int r) {
            int x = 0, y = r;
            int p = 1 - r;
            int count = 0;

            while (x <= y) {
                putPixel(g, x, y, xc, yc, count++);
                x++;
                if (p < 0)
                    p += 2 * x + 1;
                else {
                    y--;
                    p += 2 * (x - y) + 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CircleDrawingMenu().setVisible(true));
    }
}

o/p:
 


