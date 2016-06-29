package src;
import java.util.ArrayList;

/**
 * Класс, реализующий вершину графа с координатами <b>x</b> и <b>y</b>
 */
class Vertex {
	
	private int x, y, id;
		
	public Vertex(int x, int y, int id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getId(){
		return id;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public void setID(int id){
		this.id = id;
	}
}

/**
 * Класс, реализующий ребро графа, связанное с вершинами <b>u</b> и <b>v</b>
 */
class Edge {
	
	private Vertex u, v;
	
	public Edge(Vertex u, Vertex v){
		this.u = u;
		this.v = v;
	}
	
	public Vertex getU(){
		return u;
	}
	
	public Vertex getV(){
		return v;
	}
}

/**
 * Класс, реализующий граф со списком вершин <b>vertices</b> и списком рёбер <b>edges</b>
 */
public class Graph implements GraphInterface {
	
	/** Список вершин графа*/
	private ArrayList<Vertex> vertices;
	/** Список рёбер графа*/
	private ArrayList<Edge> edges;
	private final int MAX_VERTICES = 1000;

	public Graph() {
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * Метод реализующий добавление новой вершины в граф
	 * @param x - Координата по оси X добавляемой вершины
	 * @param y - Координата по оси Y добавляемой вершины
	 * @param id - Уникальный идентификатор вершины
	 */
	public void addVertex(int x, int y, int id){
		if(id == 0){
			id = 1;
			while(id <= MAX_VERTICES){
				boolean contain = false;
				for(int j = 0, size = vertices.size(); j < size; j++){
					if(vertices.get(j).getId() == id){
						contain = true;
					}
				}
				if(!contain) break;
				id++;
			}
		}
		vertices.add(new Vertex(x,y,id));
	}
	
	/**
	 * Метод, реализующий добавление нового ребра в граф
	 * @param u - Первая вершина, с которой соединено ребро
	 * @param v - Вторая вершина, с которой соединено ребро
	 */
	public void addEdge(Vertex u, Vertex v){
		for(int i = 0, size = edges.size(); i < size; i++){
			if(edges.get(i).getU() == u && edges.get(i).getV() == v ||
				edges.get(i).getU() == v && edges.get(i).getV() == u) return;
		}
		edges.add(new Edge(u, v));
	}
	
	/**
	 * Метод, реализующий удаление ребра <b>e</b> из графа
	 */
	public void removeEdge(Edge e){
		for(int i = 0, size = edges.size(); i < size; i++){
			if(edges.get(i) == e){
				edges.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Метод реализующий удаление вершины <b>v</b> из графа
	 */
	public void removeVertex(Vertex v){
		for(int i = 0; i < vertices.size(); i++){
			if(vertices.get(i) == v){
				for(int j = 0; j < edges.size(); j++){
					if(edges.get(j).getU() == v || edges.get(j).getV() == v){
						removeEdge(edges.get(j));
						j--;
					}
				}
				vertices.remove(i);
				break;
			}
		}
	}
	
	/**
	 * Метод, возвращающий true, если граф связный, иначе false
	 */
	public boolean isConnected(){
		
		ArrayList<Vertex> vert = new ArrayList<Vertex>();
		vert.add(vertices.get(0));
		
		int j = 0, verticesSize = vertices.size();
		while(true){
			
			boolean found  = false;
			for(int i = 0, size = edges.size(); i < size; i++){
				if(vert.get(j) == edges.get(i).getU() && 
						!vert.contains(edges.get(i).getV())){
					
					vert.add(edges.get(i).getV());
					found = true;
				}
				if(vert.get(j) == edges.get(i).getV() && 
						!vert.contains(edges.get(i).getU())){
					
					vert.add(edges.get(i).getU());
					found = true;
				}
			}
			if(!found && vert.size() == j+1) return false;
			if(vert.size() == verticesSize) return true;
			j++;
		}
	}
	
	/**
	 * Метод, возвращающий вес ребра <b>e</b>
	 */
	public int getEdgeWeight(Edge e){
		int diffX = e.getU().getX() - e.getV().getX();
		int diffY = e.getU().getY() - e.getV().getY();
		return (int) Math.sqrt(diffX * diffX + diffY * diffY);
	}
	
	/**
	 * Метод, предназначенный для поиска минимального ребра, инцидентного подграфу <b>graph</b>
	 */
	public Edge getMinIncidentEdge(Graph graph){
		Edge edge = null;
		int min = Integer.MAX_VALUE;
		boolean u, v;
		for(int i = 0, size = edges.size(); i < size; i++){
			u = false;
			for(int j = 0, size1 = graph.vertices.size(); j < size1; j++){
				if(graph.vertices.get(j).getId() == edges.get(i).getU().getId()){
					u = true;
					break;
				}
			}
			v = false;
			for(int j = 0, size1 = graph.vertices.size(); j < size1; j++){
				if(graph.vertices.get(j).getId() == edges.get(i).getV().getId()){
					v = true;
					break;
				}
			}
			if( u != v && getEdgeWeight(edges.get(i)) < min){
				min = getEdgeWeight(edges.get(i));
				edge = edges.get(i);
			}
		}
		return edge;
	}
	
	/**
	 * Метод, удаляющий все рёбра и вершины графа
	 */
	public void clear(){
		vertices.clear();
		edges.clear();
	}
	
	/**
	 * Метод, возвращающий список вершин графа
	 */
	public ArrayList<Vertex> getVertices(){
		return vertices;
	}
	
	/**
	 * Метод, возвращающий список рёбер графа
	 */
	public ArrayList<Edge> getEdges(){
		return edges;
	}
	
	/**
	 * Метод, возвращающий true если граф пуст, иначе false
	 */
	public boolean isEmpty(){
		return vertices.isEmpty();
	}
	
	/**
	 * Метод, возвращающий вершину по индексу <b>i</b>
	 */
	public Vertex getVertex(int i) throws NoSuchFieldException {
		if(i >= vertices.size() || i < 0) throw new NoSuchFieldException();
		return vertices.get(i);
	}
	
	/**
	 * Метод, возвращающий ребро по индексу <b>i</b>
	 */
	public Edge getEdge(int i) throws NoSuchFieldException {
		if(i >= edges.size() || i < 0) throw new NoSuchFieldException();
		return edges.get(i);
	}
}
