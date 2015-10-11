package dungeonBuilder.util.graph;

import dungeonBuilder.space.ISpace;

public interface IGraphBuilder {
	public IGraph<ISpace> buildGraph(ISpace[] spaces);
}
