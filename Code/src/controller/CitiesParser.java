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
 * Cette classe est utilis�e pour convertir un fichier texte contenant des villes et les couts associ�s pour voyager en avion d'une ville � l'autre (vols directs).
 * Le format de ce fichier doit �tre le suivant:
 * ville1	ville2	cout
 * ville3	ville4	cout
 * 
 * Ou chacun des �l�ments est un entier positif.
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
	 * @post Le fichier citiesFilePath a �t� interpr�t� en un graphe de type AdjancencyMapUndirectedGraph<Integer,Integer>.
	 * Chaque ville pr�sente dans le fichier a �t� ajout�e une seule fois au graphe
	 * Chaque lien repr�sent� par une ligne du fichier a �t� ajout�e au graphe.
	 * Le graphe est retourn�.
	 * 
	 * @throws UncorrectLineException Exception lanc�e si une ligne du fichier citiesFilePath est mal form�e.
	 * @throws FileNotFoundException Exception lanc�e si le fichier citiesFilePath n'a pu �tre trouv�.
	 * @throws IOException Exception lanc�e si une erreur d'ouverture, de lecture ou de fermeture du fichier survient.
	 * @throws IllegalArgumentException Exception lanc�e si un lien entre 2 villes est ajout� 2 fois.
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
	 * @pre La variable graph est initialis�e. Elle repr�sente un graphe vide, non null.
	 * La Map alreadyAddedVertices est initialis�e.
	 * L'arguments args contient 3 entr�es.
	 * Entr�e 1 = la ville 1
	 * Entr�e 2 = la ville 2
	 * Entr�e 3 = le cout associ� au vol direct entre ville 1 et ville 2
	 * @post Ville 1 et ville 2 sont ajout�s au graphe s'ils n'�taient aps encor� pr�sent dedans.
	 * Le lien entre les 2 villes est ajout� au graphe.

	 * @throws IllegalArgumentException Exception lanc�e si un lien entre 2 villes est d�j� pr�sent dans le graphe.
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
