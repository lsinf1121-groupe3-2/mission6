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
		chosenFlights.insertVertex(startVertex);
		for(Edge<Integer,Integer> e : startVertex.getEdges().values()){//on met à jour le prix pour atteindre les aéroports voisins
			Vertex<Integer,Integer> oppositeAirport = allFlights.opposite(startVertex,e);
			oppositeAirport.setCost(e.getElement());
			oppositeAirport.setFlightToReach(e);
			pQueue.add(oppositeAirport);
		}
	}
	
	public void primJarnik(){
		while(!pQueue.isEmpty()){
			Vertex<Integer,Integer> cheapestAirport = pQueue.poll();// a chaque iteration on ajoute l'aéroport le moins cher à ajouter
			if(cheapestAirport.getCost()!=0){
				chosenFlights.insertVertex(cheapestAirport);
				if(cheapestAirport.getFlightToReach()!=null){
					chosenFlights.insertEdge(cheapestAirport.getFlightToReach());
				}
				cheapestAirport.setFlightToReach(null);
				cheapestAirport.setCost(0);// On met le prix de l'aéroprt à 0 pour éviter de le remettre dans la file plus tard
				for(Edge<Integer,Integer> e : cheapestAirport.getEdges().values()){//on met à jour le prix pour atteindre les aéroports voisins
					Vertex<Integer,Integer> oppositeAirport = allFlights.opposite(cheapestAirport,e);
					if(oppositeAirport.getCost()>e.getElement()){
						oppositeAirport.setCost(e.getElement());
						oppositeAirport.setFlightToReach(e);
						pQueue.add(oppositeAirport);
					}
				}
			}
		}
	}

	public AdjacencyMapUndirectedGraph<Integer, Integer> getChosenFlights() {
		return chosenFlights;
	}

}

