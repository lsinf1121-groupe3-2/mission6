package controller;

import graph.AdjacencyMapUndirectedGraph;
import graph.Vertex;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import controller.exceptions.UncorrectLineException;

/**
 * Cette classe est utilisée pour convertir un fichier texte contenant des villes et les couts associés pour voyager en avion d'une ville à l'autre (vols directs).
 * Le format de ce fichier doit être le suivant:
 * ville1	ville2	cout
 * ville3	ville4	cout
 * 
 * Ou chacun des éléments est un entier positif.
 * @author Tanguy
 *
 */
public class CitiesParser {
	BufferedReader br;
	AdjacencyMapUndirectedGraph<Integer,Integer> graph;
	Map<Integer, Vertex<Integer, Integer>> alreadyAddedVertices;
	
	public CitiesParser(){
		graph = new AdjacencyMapUndirectedGraph<>();
		alreadyAddedVertices = new HashMap<>();
	}
	
	/**
	 * @pre citiesFilePath est un chemin de fichier valide
	 * @post Le fichier citiesFilePath a été interprété en un graphe de type AdjancencyMapUndirectedGraph<Integer,Integer>.
	 * Chaque ville présente dans le fichier a été ajoutée une seule fois au graphe
	 * Chaque lien représenté par une ligne du fichier a été ajoutée au graphe.
	 * Le graphe est retourné.
	 * 
	 * @throws UncorrectLineException Exception lancée si une ligne du fichier citiesFilePath est mal formée.
	 * @throws FileNotFoundException Exception lancée si le fichier citiesFilePath n'a pu être trouvé.
	 * @throws IOException Exception lancée si une erreur d'ouverture, de lecture ou de fermeture du fichier survient.
	 * @throws IllegalArgumentException Exception lancée si un lien entre 2 villes est ajouté 2 fois.
	 */
	public AdjacencyMapUndirectedGraph<Integer,Integer> parseCitiesFile(String citiesFilePath) throws UncorrectLineException, FileNotFoundException, IOException, IllegalArgumentException{
		this.initializeReader(citiesFilePath);
		
		String readLine = "";
		int lineNumber = 0;
		while((readLine = br.readLine()) != null){
			String args[] = readLine.split("	");
			if(args.length == 3){
				this.parseLine(args);
			}
			else{
				throw new UncorrectLineException("The line " + lineNumber + " is unreadable. Please check the format.");
			}
			lineNumber++;
		}
		
		this.closeReader();
		return graph;
	}

	/**
	 * @pre La variable graph est initialisée. Elle représente un graphe vide, non null.
	 * La Map alreadyAddedVertices est initialisée.
	 * L'arguments args contient 3 entrées.
	 * Entrée 1 = la ville 1
	 * Entrée 2 = la ville 2
	 * Entrée 3 = le cout associé au vol direct entre ville 1 et ville 2
	 * @post Ville 1 et ville 2 sont ajoutés au graphe s'ils n'étaient aps encoré présent dedans.
	 * Le lien entre les 2 villes est ajouté au graphe.

	 * @throws IllegalArgumentException Exception lancée si un lien entre 2 villes est déjà présent dans le graphe.
	 */
	private void parseLine(String[] args) throws IllegalArgumentException {
		Integer city1 = Integer.parseInt(args[0]);
		Integer city2 = Integer.parseInt(args[1]);
		Integer linkCost = Integer.parseInt(args[2]);
		if(alreadyAddedVertices.get(city1) == null){
			Vertex<Integer, Integer> vertex =  graph.insertVertex(city1);
			alreadyAddedVertices.put(city1, vertex);
		}
		if(alreadyAddedVertices.get(city2) == null){
			Vertex<Integer, Integer> vertex =  graph.insertVertex(city2);
			alreadyAddedVertices.put(city2, vertex);
		}
		graph.insertEdge(alreadyAddedVertices.get(city1), alreadyAddedVertices.get(city2), linkCost);
	}

	private void closeReader() throws IOException {
		br.close();
	}

	private void initializeReader(String citiesFilePath) throws FileNotFoundException {
		InputStream ips = new FileInputStream(citiesFilePath);
		InputStreamReader ipsr = new InputStreamReader(ips);
		this.br = new BufferedReader(ipsr);
	}
}
