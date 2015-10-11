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
package encounterBuilder.creature;

import treasureBuilder.TreasureType;
import encounterBuilder.creature.property.CreatureType;
import encounterBuilder.creature.property.LCAlignment;
import encounterBuilder.creature.property.GEAlignment;
import encounterBuilder.creature.property.CreatureSize;
import encounterBuilder.property.EnvironmentType;

/**
 *
 * @author Andrew Vitkus
 */
public interface ICreature {
    public String getRace();
    public String getName();
    public CreatureSize getSize();
    public int getSpeed();
    public String[] getDamageResistance();
    public GEAlignment getGEAlignment();
    public LCAlignment getLCAlignment();
    public CreatureType getType();
    public String[] getTypeDescriptors();
    public String getCreatureDescription();
    
    public int getCR();
    public int getHD();

    public int getCP();
    public TreasureType[] getTreasureType();
    
    public int[][] getGroupSizeRanges();
    
    public int getLevel();
    public int getHP();
    public int[] getAbilities();
    public int[] getSavingThrows();
    public String[] getAttack();
    public String[] getFullAttack();
    public String[] getSpecialAttack();
    public String[] getSpecialQualities();
    
    public String getSourceBook();
    public int getPage();
    
    public EnvironmentType[] getEnvironments();
}
