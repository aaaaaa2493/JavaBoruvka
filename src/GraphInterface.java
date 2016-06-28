package src;

public interface GraphInterface {
	void addVertex(int x, int y, int id);
	void addEdge(Vertex u, Vertex v);
	void removeEdge(Edge e);
	void removeVertex(Vertex v);
	boolean isConnected(); // Проверяет граф на связность
	int getEdgeWeight(Edge e);
	Edge getMinIncidentEdge(Graph graph);
	void clear();
}
