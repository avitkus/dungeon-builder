package encounterBuilder;

import encounterBuilder.creature.ICreature;
import treasureBuilder.ITreasure;

public interface IEncounter {
	public ICreature[] getCreatures();
        
        public void setTreasure(ITreasure[] treasure);
        public ITreasure[] getTreasure();
}
