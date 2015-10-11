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

import dungeonBuilder.property.Brightness;
import dungeonBuilder.space.strucure.IStructure;

/**
 *
 * @author Andrew Vitkus
 */
public class Room extends AbstractSpace implements IRoom {
    private boolean main, boss, first;
    private IStructure[] structs;
    
    public Room(int width, int height, int tlx, int tly, Brightness brightness) {
        this(width, height, new Point(tlx, tly), new Point(tlx + (width/2.), tly + (height/2.)), brightness);
    } 

    public Room(int width, int height, IPoint topLeft, IPoint center, Brightness brightness) {
        super(width, height, topLeft, center, brightness);
        main = false;
        boss = false;
        first = false;
        structs = new IStructure[0];
    } 

    @Override
    public boolean isMain() {
        return main;
    }

    @Override
    public void setMain(boolean main) {
        this.main = main;
    }

    @Override
    public boolean isBoss() {
        return boss;
    }

    @Override
    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    @Override
    public boolean isFirst() {
        return first;
    }

    @Override
    public void setFirst(boolean first) {
        this.first = first;
    }

    @Override
    public IStructure[] getStructures() {
        return structs;
    }

    @Override
    public void setStructures(IStructure[] structs) {
        this.structs = structs;
    }
   
    
}
