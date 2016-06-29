package src;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import java.util.ArrayList;

/**
 * �������� ����� ����������, �������������� �� ������
 * <code>QMainWindow</code> � ���������� ����� �����
 */

public class Main extends QMainWindow {

	/** ������ ����������������� ����������*/
	private MainWindowUI ui;
	
	/** ����������� ���� */
	private Graph graph;
	
	/** ������ ��������� ������ */
	private ArrayList<Vertex> selected;
	
	/** ������� ���� ��������� ����� */
	private int width, height;
	
	/** ��������� ������� ���� ��������� �����*/
	public static final int WIDTH = 610;
	public static final int HEIGHT = 355;
	
	/** �������� �� ����������� ���� ��������� �����*/
	public static final int OFFSET = 160;
	
	/** ������ ������� ����� */
	public static final int VERTEX_RADIUS = 13;
	
	/** ��� ��������� ��������� */
	private ArrayList<Graph> forest;
	
	/** ��������� ���������� */
	private boolean processing;
	
	
	/** 
	 * <p>����������� ������ <code>Main</code></p>
	 * <p>������� � ������������� ���������������� ���������</p>
	 **/
	public Main() {
		
		width = WIDTH;
		height = HEIGHT;
		ui = new MainWindowUI();
		ui.setupUi(this);
		ui.beginButton.clicked.connect(this, "begin()");
		ui.stepButton.clicked.connect(this, "step()");
		ui.clearButton.clicked.connect(this, "clear()");
		graph = new Graph();
		selected = new ArrayList<Vertex>();
		forest = new ArrayList<Graph>();
		processing = false;
		
	}
	
	/**
	 * ���������������� ����� ������ <code>QMainWindow</code>,
	 * �������������� ����������� ����
	 */
	protected void paintEvent(QPaintEvent e) {
		
		QPainter p = new QPainter(this);
		p.setRenderHint(QPainter.RenderHint.Antialiasing);
		
		/** ��������� ���� */
        p.fillRect(OFFSET, 10, width, height, Qt.GlobalColor.white);
        p.drawRect(OFFSET, 10, width, height);
        
        /** ��������� ����� */
        for (Edge edge : graph.getEdges())
        {
            QPen pen = new QPen();
            
            /** �������� ����� �� ��� ������� � ����������� ��������� */
            for (Graph gr : forest)
                for (Edge edge2 : gr.getEdges())
                    if (((edge.getU().getId() == edge2.getU().getId()) &&
                        (edge.getV().getId() ==edge2.getV().getId())) ||
                        (edge.getV().getId() == edge2.getV().getId()) &&
                        (edge.getU().getId() == edge2.getU().getId()))
                    {
                        pen.setBrush(new QBrush(new QColor(0, 160, 0, 255)));
                        pen.setWidth(3);
                        break;
                    }
            p.setPen(pen);
            p.drawLine(edge.getU().getX(), edge.getU().getY(), 
            		edge.getV().getX(),edge.getV().getY());
            pen.setBrush(new QBrush(new QColor(0, 0, 0, 255)));
            pen.setWidth(1);
            p.setPen(pen);
        }
        
        /** ��������� ������*/
        for (Vertex vertex : graph.getVertices()) {
        	p.setBrush(new QBrush(new QColor(160, 230, 255, 255)));
        	if (selected.contains(vertex)) {
        		p.setBrush(new QBrush(new QColor(180, 230, 90, 255)));
        	}
        	p.drawEllipse(vertex.getX() - VERTEX_RADIUS, vertex.getY() - VERTEX_RADIUS, 
        			VERTEX_RADIUS * 2, VERTEX_RADIUS * 2);
        	p.drawText(vertex.getX() - VERTEX_RADIUS, vertex.getY() - VERTEX_RADIUS + 5, 
        			VERTEX_RADIUS * 2, VERTEX_RADIUS * 2, 4, String.valueOf(vertex.getId()));
        }
        p.end();
        
	}
	
	/**
	 * ���������������� ����� ������ <code>QMainWindow</code>,
	 * �������������� ������� ������ ����
	 */
	protected void mousePressEvent(QMouseEvent e) {
		
		if (processing) return;
		if (e.button() == Qt.MouseButton.LeftButton) {
			if ((e.x() > OFFSET + VERTEX_RADIUS) && (e.x() < OFFSET + width - VERTEX_RADIUS)
					&& (e.y() > 10 + VERTEX_RADIUS) && (e.y() < height + 10 - + VERTEX_RADIUS)) {
				for (Vertex vertex : graph.getVertices()) {
					if ((Math.abs(vertex.getX() - e.x()) <= VERTEX_RADIUS * 2) &&
			                (Math.abs(vertex.getY() - e.y()) <= VERTEX_RADIUS * 2)) {
						if (selected.contains(vertex)) {
							selected.remove(vertex);
							repaint();
							return;
						}
						else {
							selected.add(vertex);
							repaint();
							return;
						}
					}
				}
				graph.addVertex(e.x(), e.y(), 0);
				repaint();
			}
		}
		
		if (e.button() == Qt.MouseButton.RightButton) {
			for (int i = 0; i < graph.getVertices().size(); i++) {
	             if ((Math.abs(graph.getVertices().get(i).getX() - e.x()) <= VERTEX_RADIUS * 2) &&
	                 (Math.abs(graph.getVertices().get(i).getY() - e.y()) <= VERTEX_RADIUS * 2))
	             {
	                selected.remove(graph.getVertices().get(i));
	                graph.removeVertex(graph.getVertices().get(i));
	                i--;
	                repaint();
	                return;
	             }
			}

	        for (int i = 0; i < selected.size(); i++)
	            for (int j = i + 1; j < selected.size(); j++)
	                graph.addEdge(selected.get(i), selected.get(j));
	        repaint();
		}
	}
	
	/**
	 * ���������������� ����� ������ <code>QMainWindow</code>,
	 * �������������� ������� �������
	 */
	protected void keyPressEvent(QKeyEvent e) {
		
		if ((!processing) && (e.key() == Qt.Key.Key_Backspace.value())) {
			for (int i = 0; i < graph.getEdges().size(); i++)
	            if (selected.contains(graph.getEdges().get(i).getU()) 
	            		&& selected.contains(graph.getEdges().get(i).getV()))
	            {
	                graph.removeEdge(graph.getEdges().get(i));
	                i--;
	            }
			repaint();
		}
		
	}
	
	/**
	 * ���������������� ����� ������ <code>QMainWindow</code>,
	 * �������������� ��������� �������� ����
	 */
	protected void resizeEvent(QResizeEvent e) {
		
		this.width = e.size().width() - OFFSET - 10;
		this.height = e.size().height() - 20;
		
	}
	
	
	/**
	 * ������ ������ ���������
	 * (������������� ����������, ������������� ����������������� ����������)
	 * @throws NoSuchFieldException 
	 */
	public void begin() throws NoSuchFieldException {
		
		if (graph.getVertices().size() == 1) return;
	    if (graph.getVertices().size() == 0) {
	        QMessageBox m = new QMessageBox();
	        m.setWindowTitle("������");
	        m.setText("���� ������ ���� ��������     ");
	        m.show();
	        return;
	    }
	    if (graph.isConnected() == false) {
	        QMessageBox m = new QMessageBox();
	        m.setWindowTitle("������");
	        m.setText("���� ������ ���� �������     ");
	        m.show();
	        return;
	    }

	    /** �������� ����������� ��������� ���������� */
	    forest.clear();
	    for (int i = 0; i < graph.getVertices().size(); i++) {
	        forest.add(new Graph());
			forest.get(forest.size()-1).addVertex(graph.getVertex(i).getX(), graph.getVertex(i).getY(), 0);
			forest.get(forest.size()-1).getVertex(0).setID(graph.getVertex(i).getId());
	    }
	    ui.stepButton.setEnabled(true);
	    ui.clearButton.setDisabled(true);
	    processing = true;
	    repaint();
	}
	
	/**
	 * ������������� ���� ������ ���������
	 * @throws NoSuchFieldException 
	 */
	public void step() throws NoSuchFieldException {
		
		ArrayList<Edge> edges = new ArrayList<Edge>();

	    /** ���������� ����� ��� ���������� */
	    for (int i = 0; i < forest.size(); i++)
	    {
	        Edge edge = graph.getMinIncidentEdge(forest.get(i));
	        if (edges.contains(edge) == false) {
	        	edges.add(edge);
	        }
	    }

	    /** ������� ��������� ��������� */
	    for (int i = 0; i < edges.size(); i++)
	    {
	        /** ����� ��������� ���������, ������� ���������� ������� ������� */
	        int uc = 0 ,vc = 0;
	        for (int j = 0; j < forest.size(); j++) {
	            for (int k = 0; k < forest.get(j).getVertices().size(); k++) {
	                if (forest.get(j).getVertex(k).getId() == edges.get(i).getU().getId()) {
	                    uc = j;
	                    break;
	                }
	            }
	        }
	        for (int j = 0; j < forest.size(); j++) {
	            for (int k = 0; k < forest.get(j).getVertices().size(); k++) {
	                if (forest.get(j).getVertex(k).getId() == edges.get(i).getV().getId()) {
	                    vc = j;
	                    break;
	                }
	            }
	        }
	        /** ���������� ������ � ����� �� ���������� v � ���������� u */
	        for (int j = 0; j < forest.get(vc).getVertices().size(); j++) {
	            forest.get(uc).getVertices().add(forest.get(vc).getVertex(j));
	        }
	        for (int j = 0; j < forest.get(vc).getEdges().size(); j++) {
	            forest.get(uc).getEdges().add(forest.get(vc).getEdge(j));
	        }
	        /** ���������� � ���������� u �������� ����� */
	        forest.get(uc).getEdges().add(edges.get(i));

	        /** ������� ���������� ��������� */
	        if (forest.get(uc).getVertices().size() == graph.getVertices().size()) {
	            processing = false;
	            ui.stepButton.setDisabled(true);
	            ui.clearButton.setEnabled(true);

	        }

	        /** �������� ���������� v */
	        forest.remove(vc);
	        repaint();
	    }
	}
	
	/**
	 * ����� ��� ������� �����
	 */
	public void clear() {
		
		selected.clear();
	    graph.clear();
	    repaint();
	    
	}
	
	public static void main(String[] args) {
		
		QApplication.initialize(args);
		Main mainWindow = new Main();
        mainWindow.show();
		QApplication.execStatic();

	}

}
