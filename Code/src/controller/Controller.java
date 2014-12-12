package controller;

import graph.AdjacencyMapUndirectedGraph;
import graph.MSTGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import controller.exceptions.UncorrectLineException;

/**
 * 
 * @author Tanguy
 *
 */
public class Controller {
	CitiesParser citiesParser;
	AdjacencyMapUndirectedGraph<Integer,Integer> graph;
	String file_name = "result.txt";
	MSTGraph mst;
	
	public Controller() {
		citiesParser = new CitiesParser();
	}

	public void start(String[] args) {
		this.parseFile(args);
		
		mst = new MSTGraph(graph);
		mst.initializePriorityQueue();
		mst.primJarnik();
		
		graph = mst.getChosenFlights();
		
		this.parseGraph(args);
	}
	
	private void parseFile(String[] args){
		if(args.length < 1){
			throw new IllegalArgumentException("First argument must be the input cities file");
		}
		try {
			graph = citiesParser.parseCitiesFile(args[0]);
		} catch (IllegalArgumentException | UncorrectLineException | IOException e) {
			if(e instanceof FileNotFoundException){
				System.err.println("Input file not found. Please check the path.");
			}
			else{
				System.err.println(((Exception)e).getMessage());
			}
			e.printStackTrace();
		}
	}
	
	private void parseGraph(String[] args){
		if(args.length == 2) {
			this.file_name = args[1];
		}
		File file = new File(file_name);
		GraphParser graphparser = new GraphParser(file, graph);
		graphparser.parse();
	}

}
