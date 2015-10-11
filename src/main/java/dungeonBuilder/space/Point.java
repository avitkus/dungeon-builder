/*
 * The MIT License
 *
 * Copyright 2015 Andrew Vitkus, Skye Frame.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package dungeonBuilder.space;

/**
 *
 * @author Andrew Vitkus
 */
public class Point implements IPoint {
    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public double distanceTo(IPoint p) {
        return Math.sqrt(Math.pow(p.getX() - x, 2) + Math.pow(p.getY() - y, 2));
    }

    @Override
    public double angleTo(IPoint p) {
        double dy = y - p.getY();
        double dx = x - p.getX();
        
        return Math.atan2(dy, dx);
    }
    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Point) {
            Point p = (Point)o;
            return p.x == x && p.y == y;
        }
        return false;
    }
}
