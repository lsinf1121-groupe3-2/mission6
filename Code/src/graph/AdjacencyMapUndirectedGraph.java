package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author Boris & Tanguy
 *
 * @param <V> Type des sommets
 * @param <E> Type des arrêtes
 */
public class AdjacencyMapUndirectedGraph<V,E> {
	private ArrayList<Vertex<V, E>> vertices;
	private ArrayList<Edge<V, E>> edges;
	private int heaviestEdge;
	
	public int getHeaviestEdge() {
		return heaviestEdge;
	}

	public void setHeaviestEdge(int heaviestEdge) {
		this.heaviestEdge = heaviestEdge;
	}

	public AdjacencyMapUndirectedGraph(){
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public int numVertices() {return vertices.size();}
	public int numEdges() {return edges.size();}
	public Iterable<Edge<V, E>> adjacentEdges(Vertex<V, E> v) {
		return v.getEdges().values();
	}
	public Edge<V, E> getEdge(Vertex<V, E> u, Vertex<V, E> v){
		return u.getEdges().get(v);
	}
	public Vertex<V, E>[] endVertices(Edge<V, E> e){
		return e.getEndpoints();
	}
	public ArrayList<Vertex<V, E>> getVertices(){
		return vertices;
	}
	
	public Vertex<V, E> opposite(Vertex<V, E> v, Edge<V, E> e) throws IllegalArgumentException {
		Vertex<V, E>[] endPoints = e.getEndpoints();
		if (endPoints[0] == v)
			return endPoints[1];
		else if (endPoints[1] == v)
			return endPoints[0];
		else
			throw new IllegalArgumentException("v is not incident to this edge");
	}

	public Vertex<V, E> insertVertex(V element) {
		Vertex<V, E> v = new Vertex<>(element);
		vertices.add(v);
		return v;
	}
	
	public void insertVertex(Vertex<V, E> v) {
		vertices.add(v);
	}
	
	public Edge<V, E> insertEdge(Vertex<V, E> u, Vertex<V, E> v, E element) throws IllegalArgumentException{
		if (getEdge(u,v)==null) {
			Edge<V, E> e = new Edge<>(u,v,element);
			edges.add(e);
			u.getEdges().put(v,e);
			v.getEdges().put(u,e);
			return e;
		}else{
			throw new IllegalArgumentException("Edge between u and v already exists");
		}
	}
	public void removeEdge(Edge<V, E> e) {
		e.getEndpoints()[0].getEdges().remove(e);
		e.getEndpoints()[1].getEdges().remove(e);
		edges.remove(e);
	}
	public void removeVertex(Vertex<V, E> v) {
		for (Edge<V, E> e : v.getEdges().values()){
			removeEdge(e);
		}
		vertices.remove(v);
	}
}
