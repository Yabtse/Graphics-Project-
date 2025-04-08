/**
 * @author Yabtse Amente
 * @version Jan 16th 
 */

import java.awt.Graphics;
import java.awt.Color;

/**
 * This program creates a graphical display divided into four quadrants,
 * each displaying a different star pattern effect. The patterns vary
 * based on parameters such as twist factor and coloring mode.
 *
 * Author: Yabtse Amente
 */
public class GraphicsProject {
    /** Width and height of the drawing panel in pixels */
    public static final int PANEL_SIZE = 600;

    /** Size of each quadrant (half of the panel size) */
    public static final int QUADRANT_SIZE = PANEL_SIZE / 2;

    /** Number of lines used in star drawing */
    public static final int NUM_LINES = 30;

    /** Delay between drawing frames (not used in current version) */
    public static final int DELAY = 10;

    /**
     * Entry point of the program. Sets up the drawing panel and draws four star
     * patterns in different quadrants using various modes and twist factors.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(PANEL_SIZE, PANEL_SIZE);
        panel.setBackground(Color.BLACK);
        Graphics g = panel.getGraphics();

        drawStarInQuadrant(g, 0, 0, NUM_LINES, 0, "classic");
        drawStarInQuadrant(g, QUADRANT_SIZE, 0, NUM_LINES, 5, "swirl");
        drawStarInQuadrant(g, 0, QUADRANT_SIZE, NUM_LINES * 2, 2, "dense");
        drawStarInQuadrant(g, QUADRANT_SIZE, QUADRANT_SIZE, NUM_LINES, 0, "rainbow");

        g.setColor(Color.WHITE);
        g.drawString("Yabtse Amente", PANEL_SIZE - 120, PANEL_SIZE - 20);
    }

    /**
     * Draws a star pattern in a specified quadrant of the panel.
     *
     * @param g           the Graphics context
     * @param offsetX     horizontal offset for the quadrant
     * @param offsetY     vertical offset for the quadrant
     * @param lines       number of lines to draw
     * @param twistFactor value controlling twisting of lines
     * @param mode        determines coloring and style of the star
     */
    public static void drawStarInQuadrant(Graphics g, int offsetX, int offsetY, int lines, int twistFactor, String mode) {
        int center = QUADRANT_SIZE / 2;
        int increment = center / lines;
        int diagStep = (int) (increment / Math.sqrt(2));

        for (int i = 0; i <= lines; i++) {
            int yStart = i * increment;
            int yStartRef = QUADRANT_SIZE - yStart;

            int xDiag = center + (i * diagStep);
            int yDiag = center - (i * diagStep);
            int yDiagRef = center + (i * diagStep);

            int twist = (mode.equals("swirl")) ? (i * twistFactor / 5) : (i / (twistFactor == 0 ? 1 : twistFactor));
            int centerX = center + twist;
            int centerY = center - twist;

            Color color;
            if (mode.equals("rainbow")) {
                float hue = (float) i / lines;
                color = Color.getHSBColor(hue, 1.0f, 1.0f);
            } else if (mode.equals("dense")) {
                int r = 150 + (i * 3 % 100);
                int b = 255 - (i * 2 % 100);
                color = new Color(r, 100, b);
            } else {
                color = Color.MAGENTA;
            }

            g.setColor(color);
            drawAllLines(g, yStart, yStartRef, xDiag, yDiag, yDiagRef, centerX, centerY, offsetX, offsetY, center);
        }
    }

    /**
     * Draws all eight symmetrical lines for a star pattern based on center coordinates.
     *
     * @param g          the Graphics context
     * @param yStart     starting Y position
     * @param yStartRef  reflected starting Y position
     * @param xDiag      diagonal X coordinate
     * @param yDiag      diagonal Y coordinate
     * @param yDiagRef   reflected diagonal Y coordinate
     * @param offsetX    X offset for the quadrant
     * @param offsetY    Y offset for the quadrant
     * @param quadX      center X coordinate of the quadrant
     * @param quadY      center Y coordinate of the quadrant
     * @param center     center point value for symmetry
     */
    public static void drawAllLines(Graphics g, int yStart, int yStartRef, int xDiag, int yDiag, int yDiagRef,
                                    int offsetX, int offsetY, int quadX, int quadY, int center) {
        g.drawLine(quadX + offsetX, quadY + yStart, quadX + xDiag, quadY + yDiag);
        g.drawLine(quadX + yStartRef, quadY + offsetY, quadX + xDiag, quadY + yDiag);
        g.drawLine(quadX + yStartRef, quadY + offsetY, quadX + yDiagRef, quadY + yDiagRef);
        g.drawLine(quadX + offsetX, quadY + yStart, quadX + (center - (xDiag - center)), quadY + yDiag);
        g.drawLine(quadX + offsetX, quadY + yStartRef, quadX + xDiag, quadY + yDiagRef);
        g.drawLine(quadX + offsetX, quadY + yStartRef, quadX + yDiag, quadY + xDiag);
        g.drawLine(quadX + (center - (yStartRef - center)), quadY + offsetY,
                quadX + (center - (yDiagRef - center)), quadY + yDiagRef);
        g.drawLine(quadX + (center - (yStartRef - center)), quadY + offsetY,
                quadX + (center - (xDiag - center)), quadY + yDiag);
    }
}

