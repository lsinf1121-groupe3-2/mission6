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
			Vertex<Integer,Integer> cheapestAirport = pQueue.poll();// a chaque iteration on ajoute l'a�roport le moins cher � ajouter
			chosenFlights.insertVertex(cheapestAirport);
			
			cheapestAirport.setCost(0);// On met le prix de l'a�roprt � 0 pour �viter de le remettre dans la file plus tard
			for(Edge<Integer,Integer> e : cheapestAirport.getEdges().values()){//on met � jour le prix pour atteindre les a�roports voisins
				Vertex<Integer,Integer> oppositeAirport = allFlights.opposite(cheapestAirport,e);
				if(oppositeAirport.getCost()>e.getElement()){
					oppositeAirport.setCost(e.getElement());
					oppositeAirport.setFlightToReach(e);
					pQueue.add(oppositeAirport);
				}
			}
		}
		
		for(Vertex<Integer,Integer> v : chosenFlights.getVertices()) {
			if(v.getFlightToReach()!=null){
				chosenFlights.insertEdge(v.getFlightToReach());
			}
		}
		
		
	}

	public AdjacencyMapUndirectedGraph<Integer, Integer> getChosenFlights() {
		return chosenFlights;
	}

}

