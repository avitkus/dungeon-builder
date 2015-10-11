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
package dungeonBuilder.visualization;

import dungeonBuilder.property.Brightness;
import dungeonBuilder.space.CompositeSpace;
import dungeonBuilder.space.ICompositeSpace;
import dungeonBuilder.space.IPoint;
import dungeonBuilder.space.ISpace;
import dungeonBuilder.space.Room;
import dungeonBuilder.util.SpaceDeclutterer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Andrew Vitkus
 */
public class SpaceVisualizer extends JFrame implements Runnable{
    private ISpace[] spaces;
    
    public SpaceVisualizer() {
        super("Space visualizer");
        spaces = new ISpace[]{
                new Room(100, 50, 50, 50, Brightness.NORMAL),
                new Room(50, 100, 50, 50, Brightness.NORMAL),
                new Room(500, 230, 25, 250, Brightness.NORMAL),
                new CompositeSpace(new Room(75, 25, 200, 200, Brightness.NORMAL),
                                   new Room(25, 75, 200, 200, Brightness.NORMAL))
            };
        SpaceDeclutterer.process(spaces);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new SpaceVisualizer());
    }
    
    @Override
    public void run() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new SpacePanel(spaces));
        setSize(600,800);
        setLocationByPlatform(true);
        setVisible(true);
    }
    
    
    class SpacePanel extends JPanel {
        private final ISpace[] spaces;
        
        SpacePanel(ISpace[] spaces) {
            this.spaces = spaces;
            setBackground(Color.white);
        }
    
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            addSpaces(g, spaces);
        }
        
        private void addSpaces(Graphics g, ISpace[] spaces) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(10));
            for(ISpace space : spaces) {
                g2.setColor(Color.black);
                if (space instanceof ICompositeSpace) {
                    for(ISpace subSpace: ((ICompositeSpace)space).getSpaces()) {
                        IPoint topLeft = subSpace.getTopLeft();
                        g2.drawRect((int)topLeft.getX(), (int)topLeft.getY(), subSpace.getWidth(), subSpace.getHeight());
                    }
                    g2.setColor(Color.gray);
                    for(ISpace subSpace: ((ICompositeSpace)space).getSpaces()) {
                        IPoint topLeft = subSpace.getTopLeft();
                        g2.fillRect((int)topLeft.getX(), (int)topLeft.getY(), subSpace.getWidth(), subSpace.getHeight());
                    }
                } else {
                    IPoint topLeft = space.getTopLeft();
                    g2.drawRect((int)topLeft.getX(), (int)topLeft.getY(), space.getWidth(), space.getHeight());
                    g2.setColor(Color.gray);
                    g2.fillRect((int)topLeft.getX(), (int)topLeft.getY(), space.getWidth(), space.getHeight());
                }
            }
        }
    }
}
