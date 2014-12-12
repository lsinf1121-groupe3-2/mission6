package controller;

import graph.AdjacencyMapUndirectedGraph;
import graph.Edge;
import graph.Vertex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GraphParser {
	private BufferedWriter output;
	private AdjacencyMapUndirectedGraph<Integer, Integer> graph;
	
	public GraphParser(File file, AdjacencyMapUndirectedGraph<Integer, Integer> graph) {
		try {
			this.output = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.graph = graph;
	}

	public void parse() {
		List<Edge<Integer, Integer>> edges = graph.getEdges();
		for(Edge<Integer, Integer> edge : edges) {
			Vertex<Integer, Integer> v[] = edge.getEndpoints();
			try {
				output.write(v[0].getElement().toString() + " " + v[1].getElement().toString() + " " + edge.getElement().toString()+ "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
