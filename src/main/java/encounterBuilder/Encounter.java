package encounterBuilder;

import encounterBuilder.creature.ICreature;
import treasureBuilder.ITreasure;

public class Encounter implements IEncounter{

	private final ICreature[] creatures;
        private ITreasure[] treasure;
        
	Encounter(ICreature[] creatures) {
		this.creatures = creatures;
                treasure = new ITreasure[0];
	}
        @Override
	public ICreature[] getCreatures() {
		return creatures;
	}
        @Override
        public void setTreasure(ITreasure[] treasure) {
            this.treasure = treasure;
        }
        @Override
        public ITreasure[] getTreasure() {
            return treasure;
        }
}
