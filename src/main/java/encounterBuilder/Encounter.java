package encounterBuilder;

import encounterBuilder.creature.ICreature;

public class Encounter implements IEncounter{

	private final ICreature[] creatures;
	Encounter(ICreature[] creatures) {
		this.creatures = creatures;
	}
	public ICreature[] getCreatures() {
		return creatures;
	}
}
