package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class AdjacencyMapUndirectedGraph<V,E> {
	private class Vertex<V> {
		private V element;
		private Map<Vertex<V>,Edge<E>> edges;
		public Vertex(V elem) {
			element = elem;
			edges = new HashMap<Vertex<V>,Edge<E>>();
		}
		public V getElement() {
			return element;
		}
		public Map<Vertex<V>, Edge<E>> getEdges() {
			return edges;
		}
	}// end of Vertex class
	
	private class Edge<E> {
		private E element;
		private int weigth;
		private Vertex<V>[] endpoints;
		public Edge(Vertex<V> u, Vertex<V> v, E elem) {
			element   = elem;
			endpoints = (Vertex<V>[]) new Vertex[]{u,v};
		}
		public E getElement() {
			return element;
		}
		public Vertex<V>[] getEndpoints() {
			return endpoints;
		}
	}// end of Edge class
	
	private ArrayList<Vertex<V>> vertices;
	private ArrayList<Edge<E>> edges;
	public AdjacencyMapUndirectedGraph(){}
	public int numVertices() {return vertices.size();}
	public Iterable<Vertex<V>> vertices(){return vertices;}
	public int numEdges() {return edges.size();}
	public Iterable<Edge<E>> adjacentEdges(Vertex<V> v) {
		return v.getEdges().values();
	}
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v){
		return u.getEdges().get(v);
	}
	public Vertex<V>[] endVertices(Edge<E> e){
		return e.getEndpoints();
	}
	
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		Vertex<V>[] endPoints = e.getEndpoints();
		if (endPoints[0] == v)
			return endPoints[1];
		else if (endPoints[1] == v)
			return endPoints[0];
		else
			throw new IllegalArgumentException("v is not incident to this edge");
	}

	public Vertex<V> insertVertex(V element) {
		Vertex<V> v = new Vertex<>(element);
		vertices.add(v);
		return v;
	}
	
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException{
		if (getEdge(u,v)==null) {
			Edge<E> e = new Edge<>(u,v,element);
			edges.add(e);
			u.getEdges().put(v,e);
			v.getEdges().put(u,e);
			return e;
		}else{
			throw new IllegalArgumentException("Edge between u and v already exists");
		}
	}
	public void removeEdge(Edge<E> e) {
		e.getEndpoints()[0].getEdges().remove(e);
		e.getEndpoints()[1].getEdges().remove(e);
		edges.remove(e);
	}
	public void removeVertix(Vertex<V> v) {
		for (Edge<E> e : v.getEdges().values()){
			removeEdge(e);
		}
		vertices.remove(v);
	}
}
