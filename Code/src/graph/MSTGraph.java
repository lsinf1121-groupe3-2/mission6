package graph;

import java.util.Arrays;
import java.util.PriorityQueue;

public class MSTGraph {

	private AdjacencyMapUndirectedGraph<Integer,Integer> allFlights;
	private AdjacencyMapUndirectedGraph<Integer,Integer> chosenFlights;
	private Boolean[] exploredAirports;
	private PriorityQueue<Vertex<Integer,Integer>> pQueue;

	public MSTGraph(AdjacencyMapUndirectedGraph<Integer,Integer> originalGraph) {
		allFlights = originalGraph;
		chosenFlights = new AdjacencyMapUndirectedGraph<Integer, Integer>();
		int numAirports = allFlights.numVertices();
		exploredAirports = new Boolean[numAirports];
		Arrays.fill(exploredAirports, Boolean.FALSE);
		pQueue = new PriorityQueue(numAirports);
	}
	
	public void initializePriorityQueue() {
		int initialCost = allFlights.getHeaviestEdge() * allFlights.numVertices();
		for (Vertex<Integer, Integer> v : allFlights.getVertices()){
			v.setCost(initialCost);
			pQueue.add(v);
		}
		Vertex<Integer,Integer> startVertex = pQueue.poll();
		startVertex.setCost(0);
		pQueue.add(startVertex);
	}
	
	public void primJarnik(){
		while(!pQueue.isEmpty()){
			Vertex<Integer,Integer> cheapestAirport = pQueue.poll();// a chaque iteration on ajoute l'aéroport le moins cher à ajouter
			chosenFlights.insertVertex(cheapestAirport);
			for(Edge<Integer,Integer> e : cheapestAirport.getEdges().values()){//on met à jour le prix pour atteindre les aéroports voisins
				Vertex<Integer,Integer> oppositeAirport = allFlights.opposite(cheapestAirport,e);
				if(oppositeAirport.getCost()>e.getElement()){
					oppositeAirport.setCost(e.getElement());
					pQueue.add(oppositeAirport);
				}
			}
		}
	}
	
}

