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
package treasureBuilder;

import dungeonBuilder.space.IRoom;
import dungeonBuilder.space.strucure.IStructure;
import dungeonBuilder.space.strucure.ITreasureChest;
import dungeonBuilder.space.strucure.TreasureChest;
import encounterBuilder.IEncounter;
import encounterBuilder.creature.ICreature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Andrew Vitkus
 */
public class TreasureBuilderFactory {
    public static ITreasureBuilder getTreasureBuilder(int cpTarget) {
        return new ITreasureBuilder() {

            @Override
            public void allocateTreasure(IRoom[] rooms, IEncounter[] encounters) {
                int cpRemaining = cpTarget;
                for(IEncounter encounter : encounters) {
                    List<ITreasure> encounterTreasure = new ArrayList<>();
                    for(ICreature creature : encounter.getCreatures()) {
                        ITreasure[] creatureTreasure = buildCreatureTreasure(creature);
                        encounterTreasure.addAll(Arrays.asList(creatureTreasure));
                    }
                    cpRemaining -= encounterTreasure.stream().mapToInt((treasure)->treasure.getCPValue()).sum();
                    encounter.setTreasure(encounterTreasure.toArray(new ITreasure[encounterTreasure.size()]));
                }
                if (cpRemaining > 0) {
                    IRoom[] treasureRooms = Arrays.stream(rooms)
                            .filter((room)->(room.isBoss() || (room.isMain() && Math.random() > 0.5)))
                            .toArray(IRoom[]::new);
                    int bossCP = 0;
                    double expectedRoomPercent = 1;
                    if (treasureRooms.length == 0) {
                        return;
                    } else if (treasureRooms.length == 1) {
                        bossCP = cpRemaining;
                    } else {
                        bossCP = (int)Math.ceil(cpRemaining * 0.4);
                        expectedRoomPercent = 1/(treasureRooms.length-1);
                        cpRemaining -= bossCP;
                    }
                    Random rand = new Random();
                    for(IRoom room : treasureRooms) {
                        ITreasureChest chest;
                        if (room.isBoss()) {
                            chest = new TreasureChest(buildRoomTreasure(bossCP));
                        } else {
                            double treasurePercent = rand.nextGaussian() * .25 + expectedRoomPercent;
                            treasurePercent = Math.max(treasurePercent, expectedRoomPercent * .25);
                            treasurePercent = Math.min(treasurePercent, expectedRoomPercent * 1.75);
                            int roomCP = (int)Math.ceil(cpRemaining * treasurePercent);
                            cpRemaining -= roomCP;
                            
                            chest = new TreasureChest(buildRoomTreasure(roomCP));
                        }
                        IStructure[] structs = room.getStructures();
                        int structsLength = structs.length;
                        structs = Arrays.copyOf(structs, structsLength + 1); // make it 1 bigger
                        structs[structsLength] = chest; // structs.length was 1 past old length
                        room.setStructures(structs);
                    }
                }
            }
            
            private ITreasure[] buildRoomTreasure(int cp) {
                return null;
            }
            
            private ITreasure[] buildCreatureTreasure(ICreature creature) {
                for(TreasureType type : creature.getTreasureType()) {
                    switch(type) {
                        case STANDARD_ITEMS:
                        case MAGIC_ITEMS:
                        case GOODS:
                        case ART:
                        case JEWELS:
                        case NONE:
                            break;
                    }
                }
                return null;
            }
            
        };
    }
}
