package src;
import java.util.ArrayList;

public interface GraphInterface {
	void addVertex(int x, int y, int id);
	void addEdge(Vertex u, Vertex v);
	void removeEdge(Edge e);
	void removeVertex(Vertex v);
	boolean isConnected();
	int getEdgeWeight(Edge e);
	Edge getMinIncidentEdge(Graph graph);
	void clear();
	ArrayList<Vertex> getVertices();
	ArrayList<Edge> getEdges();
	Vertex getVertex(int i) throws NoSuchFieldException;
	Edge getEdge(int i) throws NoSuchFieldException;
}
