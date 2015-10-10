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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import encounterBuilder.creature.property.CreatureSize;
import encounterBuilder.creature.property.CreatureType;
import encounterBuilder.creature.property.GEAlignment;
import encounterBuilder.creature.property.LCAlignment;
import encounterBuilder.property.EnvironmentType;

/**
 *
 * @author Skye Frame
 */
public class CreatureGeneratorFactory {
    public static List<ICreatureGenerator> buildCreatureGeneratorList(Path creatureDictionary) throws IOException {
        List<ICreatureGenerator> creatures = new ArrayList<>();
        Files.lines(creatureDictionary).forEach((line) -> creatures.add(buildGenerator(line)));
        return creatures;
    }
    
    private static ICreatureGenerator buildGenerator(String creatureEntry) {
        String[] creatureData = creatureEntry.split(",");
    	return new AbstractCreatureGenerator() {

			@Override
			public String getRace() {
				return creatureData[0];
			}
			public int getCR(){
				return Integer.parseInt(creatureData[9]);
			}

			@Override
			public EnvironmentType[] getEnvironments() {
				String[] Environments = creatureData[21].split(";");
				EnvironmentType[] environments = Arrays.stream(Environments).map((str)->EnvironmentType.get(str)).toArray(EnvironmentType[]::new);
				return environments;
			}

			@Override
			public ICreature getNamedCreature(String name) {
				return new ICreature() {

					@Override
					public String getRace() {
						return creatureData[0];
					}

					@Override
					public String getName() {
						return name;
					}

					@Override
					public CreatureSize getSize() {
						return CreatureSize.get(creatureData[1]);
					}
					
					@Override
					public int getSpeed() {
						return Integer.parseInt(creatureData[2]);
					}
					
					@Override
					public String[] getDamageResistance() {
						String[] DR = creatureData[3].split(";");
						return DR;
					}

					@Override
					public GEAlignment getGEAlignment() {
						return GEAlignment.get(creatureData[4]);
					}

					@Override
					public LCAlignment getLCAlignment() {
						return LCAlignment.get(creatureData[5]);
					}

					@Override
					public CreatureType getType() {
						return CreatureType.get(creatureData[6]);
					}

					@Override
					public String[] getTypeDescriptors() {
						String[] TD = creatureData[7].split(";");
						return TD;
					}

					@Override
					public String getCreatureDescription() {
						return creatureData[8];
					}

					@Override
					public int getCR() {
						return Integer.parseInt(creatureData[9]);
					}

					@Override
					public int getHD() {
						return Integer.parseInt(creatureData[10]);
					}

					@Override
					public int getCP() {
						return Integer.parseInt(creatureData[11]);
					}

					@Override
					public TreasureType[] getTreasureType() {
						String[] Treasure = creatureData[12].split(";");
						TreasureType[] treasures = Arrays.stream(Treasure).map((str)->TreasureType.get(str)).toArray(TreasureType[]::new);
						return treasures;
					}

					@Override
					public int[][] getGroupSizeRanges() {
						String[] Size = creatureData[13].split(";");
						String[][] SizeRanges = new String[Size.length][];
						int[][] ints = new int[SizeRanges.length][2];
						for (int i = 0; i < Size.length; i++) {
							String[] Ranges = Size[i].split("-");
							SizeRanges[i]=Ranges;
						}
						for (int i = 0; i < SizeRanges.length; i++) {
							ints[i][0] = Integer.parseInt(SizeRanges[i][0]);
							ints[i][1] = Integer.parseInt(SizeRanges[i][1]);
						}
						return ints;
						
					}

					@Override
					public int getLevel() {
						return Integer.parseInt(creatureData[14]);
					}

					@Override
					public int getHP() {
						return Integer.parseInt(creatureData[15]);
					}

					@Override
					public int[] getAbilities() {
						String[] Abil = creatureData[16].split(";");
						int[] Abilities = new int[Abil.length];
						for (int i = 0; i < Abil.length; i++) {
							Abilities[i] = Integer.parseInt(Abil[i]);
						}
						return Abilities;
					}

					@Override
					public int[] getSavingThrows() {
						String[] Saves = creatureData[17].split(";");
						int[] SavingThrows = new int[Saves.length];
						for (int i = 0; i < Saves.length; i++) {
							SavingThrows[i] = Integer.parseInt(Saves[i]);
						}
						return SavingThrows;
					}

					@Override
					public String[] getAttack() {
						String[] Attack = creatureData[18].split(";");
						return Attack;
					}

					@Override
					public String[] getFullAttack() {
						String[] FullAttack = creatureData[19].split(";");
						return FullAttack;
					}

					@Override
					public String[] getSpecialAttack() {
						String[] SpecialAttack = creatureData[20].split(";");
						return SpecialAttack;
					}

					@Override
					public String[] getSpecialQualities() {
						String[] Special = creatureData[21].split(";");
						return Special;
					}

					@Override
					public String getSourceBook() {
						return creatureData[22];
					}

					@Override
					public int getPage() {
						return Integer.parseInt(creatureData[23]);
					}

					@Override
					public EnvironmentType[] getEnvironments() {
						String[] Environments = creatureData[21].split(";");
						EnvironmentType[] environments = Arrays.stream(Environments).map((str)->EnvironmentType.get(str)).toArray(EnvironmentType[]::new);
						return environments;
					}

					
				};
			}
    	};
    }
}
