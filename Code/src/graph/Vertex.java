package graph;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author Boris 
 *
 * @param <V> Type des sommets
 * @param <E> Type des arr�tes
 */
public class Vertex<V, E> implements Comparable<Vertex<V,E>> {
	private V element;
	private Map<Vertex<V, E>,Edge<V, E>> edges;
	private int minCostToReach;
	private Edge<V,E> flightToReach;
	public Edge<V, E> getFlightToReach() {
		return flightToReach;
	}
	public void setFlightToReach(Edge<V, E> flightToReach) {
		this.flightToReach = flightToReach;
	}
	public Vertex(V elem) {
		element = elem;
		edges = new HashMap<Vertex<V, E>,Edge<V, E>>();
	}
	public V getElement() {
		return element;
	}
	public Map<Vertex<V, E>, Edge<V, E>> getEdges() {
		return edges;
	}
	
	public int getCost(){
		return minCostToReach;
	}
	public void setCost(int c){
		minCostToReach = c;
	}

	public int compareTo(Vertex<V, E> o) {
		return this.getCost() - o.getCost();
	}
}// end of Vertex class
