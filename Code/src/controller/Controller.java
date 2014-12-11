package controller;

import graph.AdjancencyMapUndirectedGraph;

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
	AdjancencyMapUndirectedGraph<Integer,Integer> graph;
	
	public Controller() {
		citiesParser = new CitiesParser();
	}

	public void start(String[] args) {
		this.parseFile(args);
		
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

}
