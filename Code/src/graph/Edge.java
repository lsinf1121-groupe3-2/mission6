package graph;

public class Edge<V, E> {
	private E element;
	private Vertex<V, E>[] endpoints;
	public Edge(Vertex<V, E> u, Vertex<V, E> v, E elem) {
		element   = elem;
		endpoints = (Vertex<V, E>[]) new Vertex[]{u,v};
	}
	public E getElement() {
		return element;
	}
	public Vertex<V, E>[] getEndpoints() {
		return endpoints;
	}
}// end of Edge class
