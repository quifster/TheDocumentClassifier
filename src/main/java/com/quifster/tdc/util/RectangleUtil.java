package com.quifster.tdc.util;

import org.springframework.stereotype.Component;

import java.awt.geom.Rectangle2D;

/**
 * Utility class for various {@link Rectangle2D} helper methods.
 */
@Component
public class RectangleUtil {

    public double percentageOfRectangleOverlap(Rectangle2D r1, Rectangle2D r2){
        Rectangle2D r = new Rectangle2D.Double();
        Rectangle2D.intersect(r1, r2, r);

        double fr1 = r1.getWidth() * r1.getHeight();                // area of "r1"
        double f   = r.getWidth() * r.getHeight();                  // area of "r" - overlap
        return (fr1 == 0 || f <= 0) ? 0 : (f / fr1) * 100;          // overlap percentage
    }

    public static void main(String[] args) {
        Rectangle2D r1 = new Rectangle2D.Double(0, 0, 100, 100);
        Rectangle2D r2 = new Rectangle2D.Double(0, 00, 50, 100);

        RectangleUtil util = new RectangleUtil();

        System.out.println(util.percentageOfRectangleOverlap(r1, r2));
    }
}
