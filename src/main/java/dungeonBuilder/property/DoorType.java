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
package dungeonBuilder.property;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Andrew Vitkus
 */
public enum DoorType {
    WOOD, STEEL, STONE, PORTICULLIS;
    
    private static final Map<DoorType, Double> weights;
    
    static {
        weights = new HashMap<>(4);
        weights.put(WOOD, 0.7);
        weights.put(STEEL, 0.15);
        weights.put(STONE, 0.05);
        weights.put(PORTICULLIS, 0.1);
    }
    
    public static void setWeight(DoorType type, double weight) {
        weights.put(type, weight);
    }
    
    public static double getWeight(DoorType type) {
        return weights.get(type);
    }
    
    public static DoorType getRandom() {
        double rand = Math.random();
        for(Entry<DoorType, Double> type : weights.entrySet()) {
            rand -= type.getValue();
            if (rand <= 0) {
                return type.getKey();
            }
        }
        return WOOD;
    }
}
