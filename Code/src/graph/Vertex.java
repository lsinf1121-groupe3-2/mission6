package graph;

import java.util.HashMap;
import java.util.Map;

public class Vertex<V, E> {
	private V element;
	private Map<Vertex<V, E>,Edge<V, E>> edges;
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
}// end of Vertex class
