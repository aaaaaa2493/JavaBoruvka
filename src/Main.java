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
	 * �������������� ������� ����
	 */
	protected void mousePressEvent(QMouseEvent e) {
		
		if (e.button() == Qt.MouseButton.LeftButton) {
			if ((e.x() > OFFSET) && (e.x() < OFFSET + width)
					&& (e.y() > 10) && (e.y() < height + 10)) {
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
			//connect selected
		}
	}
	
	/**
	 * ���������������� ����� ������ <code>QMainWindow</code>,
	 * �������������� ������� �������
	 */
	protected void keyPressEvent(QKeyEvent e) {
		if (e.key() == Qt.Key.Key_Backspace.value()) {
			//remove
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
	 */
	public void begin() {
		/** 
		 * TODO: ������ ������ ���������
		 */
	}
	
	/**
	 * ������������� ���� ������ ���������
	 */
	public void step() {
		/** 
		 * TODO: ��� ���������
		 */
	}
	
	/**
	 * ����� ��� ������� �����
	 */
	public void clear() {
		/** 
		 * TODO: ������� �����
		 */
	}
	
	public static void main(String[] args) {
		
		QApplication.initialize(args);
		Main mainWindow = new Main();
        mainWindow.show();
		QApplication.execStatic();

	}

}
