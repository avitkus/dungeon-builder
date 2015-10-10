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
package encounterBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import encounterBuilder.creature.CreatureGeneratorFactory;
import encounterBuilder.creature.ICreature;
import encounterBuilder.creature.ICreatureGenerator;
import encounterBuilder.property.EnvironmentType;

/**
 *
 * @author Andrew Vitkus
 */
public class EncounterBuilderFactory {
	private static final Path creatureDictionary = Paths.get("creatures.dic");
	
    public static IEncounterBuilder buildEncoutnerBuilder(int cr, EnvironmentType[] environments) throws IOException {
    	List<ICreatureGenerator> creatures = CreatureGeneratorFactory.buildCreatureGeneratorList(creatureDictionary);
		List<ICreatureGenerator> envCreatures = creatures.stream()
				.filter((gen)->gen.getCR() <= cr)
				.filter((gen)->arrayOverlap(gen.getEnvironments(), environments))
				.collect(Collectors.toList());
	
    	return new IEncounterBuilder() {
        	
        	@Override
			public IEncounter getEncounter() {
        		int ncr = cr;
        		List<ICreature> mobs = new ArrayList<>();
        		int len = envCreatures.size();
        		Random random = new Random();
        		List<ICreatureGenerator> crLimitCreatures = envCreatures;
        		while (ncr > 0 && !crLimitCreatures.isEmpty()) {
					int rand = random.nextInt(len);
					ICreatureGenerator generator = crLimitCreatures.get(rand);
					mobs.add(generator.getCreature());
					ncr = ncr - generator.getCR();
	        		final double compCr = ncr;
	        		crLimitCreatures = envCreatures.stream()
	        				.filter((gen)->gen.getCR() <= compCr).collect(Collectors.toList());
					
				}
				return new Encounter(mobs.toArray(new ICreature[mobs.size()]));
			}
        	
        };
    }
    
    private static boolean arrayOverlap(Object[] a, Object b[]) {
    	for(Object oa :  a) {
    		for(Object ob : b) {
    			if (oa.equals(ob)) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
}
