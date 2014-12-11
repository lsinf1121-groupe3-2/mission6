package controller;

import graph.AdjancencyMapUndirectedGraph;
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

public class CitiesParser {
	BufferedReader br;
	AdjancencyMapUndirectedGraph<Integer,Integer> graph;
	Map<Integer, Vertex<Integer, Integer>> alreadyAddedVertices;
	
	public CitiesParser(){
		graph = new AdjancencyMapUndirectedGraph<>();
		alreadyAddedVertices = new HashMap<>();
	}
	
	public AdjancencyMapUndirectedGraph<Integer,Integer> parseCitiesFile(String citiesFilePath) throws UncorrectLineException, FileNotFoundException, IOException, IllegalArgumentException{
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
