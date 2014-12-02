
D[b] MaxBandWidth( Graph G, Vertex a, Vertex b){
	//Initialisation de D[a] a 0
	D[a] = 0;
	//Initialisation de D[v] a - infiny
	D[v] = -inf for each vertex v != a
	PriorityQueue Q contains all vertices of G using the D labels as keys
	
	while Q contains b
		u = Q.removeMax();
		List<D[u]>.add(u);
		for each vertex z adjacent to u such that z is in Q do
			//relaxation procedure
			if D[z] < D[u]
				D[z] = min(D[u], w(u, z));
				change the key of vertex z in Q to the new D[z]
			end if
		end for
	end while
	return List<D[u]>.get(b);
}