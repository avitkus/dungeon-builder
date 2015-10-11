package dungeonBuilder.util.graph;

import java.util.ArrayList;

import dungeonBuilder.space.ISpace;

public class oops  {

	public IGraph<ISpace> buildGraph(ISpace[] spaces) {
		ISpace first;
		ISpace prev;
		ISpace max;
		ISpace[] copy = new ISpace[spaces.length];
		
		for (int i = 0; i < spaces.length; i++) {
			copy[i] = spaces[i];
		}
		
		//find min
		first = findMin(spaces);
		prev = first;
		
		max = findMax(spaces);
		
		return BuildGraph(spaces, copy, prev, max);
	}
	
	public ISpace findMin(ISpace[] spaces) {
		//Finds the space with the smallest y value
		ISpace min = spaces[0];
		
		for (int i = 1; i < spaces.length; i++) {
			if (spaces[i].getCenter().getY() < min.getCenter().getY()) {
				min = spaces[i];
			}
		}
		return min;
	}
	public ISpace findMax(ISpace[] spaces) {
		//Finds the space with the largest y value
		ISpace max = spaces[0];
		
		for (int i = 1; i < spaces.length; i++) {
			if (spaces[i].getCenter().getY() > max.getCenter().getY()) {
				max = spaces[i];
			}
		}
		return max;
	}
	
	public ISpace findNextone(ISpace[] spaces, ISpace[] copy, ISpace prev) {
		//Finds the space with the next lowest y value
		ISpace next = copy[0];
		for (int i = 0; i < spaces.length; i++) {
			double diff1 = Math.abs(prev.getCenter().getY()-next.getCenter().getY());
			double diff2 = Math.abs(prev.getCenter().getY() - spaces[i].getCenter().getY());
			if (diff1 >= diff2) {
				next = spaces[i];
			}
		}
		for (int i = 0; i < spaces.length; i++) { //removes the node from the copy list, so we can check that all
			if (copy[i] == next) {					//spaces are included in the graph
				copy[i] = null;
			}
		}
		return next;
	}
	
	public ISpace findNexttwo(ISpace[] spaces, ISpace[] copy, ISpace prev, ISpace next1, ISpace first) {
		//Finds the space with the next smallest y value such that it is not on the same side of the 
		//Parent node as the sister node
		
		//This is done to prevent cris-crossing paths
		ISpace next = first;
		for (int i = 0; i < spaces.length; i++) {
			double diff1 = Math.abs(prev.getCenter().getY()-next.getCenter().getY());
			double diff2 = Math.abs(prev.getCenter().getY()-spaces[i].getCenter().getY());
			
			double xdiff1 = prev.getCenter().getX()-next1.getCenter().getX();
			double xdiff2 = prev.getCenter().getX()-next.getCenter().getX();
			
			if (diff1 >= diff2) {
				if (xdiff1 >= 0 && !(xdiff2 >= 0) || !(xdiff1 >=0) && xdiff2 >= 0 ) {
					next = spaces[i];
				}
			}
		}
		if (next == first) {
			next = findNextone(spaces, copy, prev);
		}
		for (int i = 0; i < spaces.length; i++) {
			if (copy[i] == next) {
				copy[i] = null;
			}
		}
		return next;
	}
	
	public IGraph<ISpace> BuildGraph(ISpace[] spaces, ISpace[] copy, ISpace prev, ISpace max) {
		//Recursively builds the graph to be returned in the first method
		ISpace next;
		ISpace next2;
		ISpace[] edge = new ISpace[2];
		ArrayList<ISpace[]> graph = new ArrayList<ISpace[]>();
		
		if (prev != max) {
			edge[0]=prev;
			next = findNextone(spaces, copy, prev);
			edge[1] = next;
			graph.add(edge);
			
			BuildGraph(spaces, copy, next, max);
			
			next2 = findNexttwo(spaces, copy, prev, next, findMin(spaces));
			edge[1] = next2;
			graph.add(edge);
			
			BuildGraph(spaces, copy, next2, max);
		}
		
		IGraph<ISpace> spaceGraph = new Graph<>(graph.toArray(new ISpace[graph.size()][2]));
		return spaceGraph;
	}


}
