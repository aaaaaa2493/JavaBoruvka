package src;

import com.trolltech.qt.core.Qt;
import com.trolltech.qt.gui.*;
import java.util.ArrayList;

/**
 * Основной класс приложения, унаследованный от класса
 * <code>QMainWindow</code> и содержащий точку входа
 */

public class Main extends QMainWindow {

	/** Объект пользовательского интерфейса*/
	private MainWindowUI ui;
	
	/** Тестируемый граф */
	private Graph graph;
	
	/** Список выбранных вершин */
	private ArrayList<Vertex> selected;
	
	/** Размеры поля отрисовки графа */
	private int width, height;
	
	/** Начальные размеры поля отрисовки графа*/
	public static final int WIDTH = 610;
	public static final int HEIGHT = 355;
	
	/** Смещение по горизонтали поля отрисовки графа*/
	public static final int OFFSET = 160;
	
	/** Радиус вершины графа */
	public static final int VERTEX_RADIUS = 13;
	
	private ArrayList<Graph> forest = new ArrayList<Graph>();
	/** 
	 * <p>Конструктор класса <code>Main</code></p>
	 * <p>Создает и устанавливает пользовательский интерфейс</p>
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
	 * Переопределенный метод класса <code>QMainWindow</code>,
	 * осуществляющий перерисовку окна
	 */
	protected void paintEvent(QPaintEvent e) {
		QPainter p = new QPainter(this);
		p.setRenderHint(QPainter.RenderHint.Antialiasing);
		
		/** Отрисовка поля */
        p.fillRect(OFFSET, 10, width, height, Qt.GlobalColor.white);
        p.drawRect(OFFSET, 10, width, height);
        
        /** Отрисовка вершин*/
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
	 * Переопределенный метод класса <code>QMainWindow</code>,
	 * обрабатывающий нажатие мыши
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
	 * Переопределенный метод класса <code>QMainWindow</code>,
	 * обрабатывающий нажатие клавиши
	 */
	protected void keyPressEvent(QKeyEvent e) {
		if (e.key() == Qt.Key.Key_Backspace.value()) {
			//remove
		}
	}
	
	/**
	 * Переопределенный метод класса <code>QMainWindow</code>,
	 * обрабатывающий изменение размеров окна
	 */
	protected void resizeEvent(QResizeEvent e) {
		this.width = e.size().width() - OFFSET - 10;
		this.height = e.size().height() - 20;
	}
	
	
	/**
	 * Начало работы алгоритма
	 * (инициализация переменных, корректировка пользовательского интерфейса)
	 * @throws NoSuchFieldException 
	 */
	public void begin() throws NoSuchFieldException {
		if (graph.getVertices().size() == 1) return;
	    if (graph.getVertices().size() == 0) {
	        QMessageBox m = new QMessageBox();
	        m.setWindowTitle("Ошибка");
	        m.setText("Граф должен быть непустым     ");
	        m.show();
	        return;
	    }
	    if (graph.isConnected() == false) {
	        QMessageBox m = new QMessageBox();
	        m.setWindowTitle("Ошибка");
	        m.setText("Граф должен быть связным     ");
	        m.show();
	        return;
	    }


	    //создание тривиальных компонент связностей
	    forest.clear();
	    for (int i = 0; i < graph.getVertices().size(); i++) {
	        forest.add(new Graph());
			forest.get(forest.size()-1).addVertex(graph.getVertex(i).getX(), graph.getVertex(i).getY(), 0);
			forest.get(forest.size()-1).getVertex(0).setID(graph.getVertex(i).getId());
	    }
	    ui.stepButton.setEnabled(true);
	    ui.clearButton.setDisabled(true);
	    repaint();
	}
	
	/**
	 * Осуществление шага работы алгоритма
	 * @throws NoSuchFieldException 
	 */
	public void step() throws NoSuchFieldException {
		ArrayList<Edge> edges = new ArrayList<Edge>();

	    //нахождение ребер для добавления
	    for (int i = 0; i < forest.size(); i++)
	    {
	        Edge edge = graph.getMinIncidentEdge(forest.get(i));
	        if (edges.contains(edge) == false) {
	        	edges.add(edge);
	        }
	    }

	    //склейка компонент связности
	    for (int i = 0; i < edges.size(); i++)
	    {
	        //поиск компонент связности, которым инцидентна текущая вершина
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
	        //добавление вершин и ребер из компоненты v в компоненту u
	        for (int j = 0; j < forest.get(vc).getVertices().size(); j++) {
	            forest.get(uc).getVertices().add(forest.get(vc).getVertex(j));
	        }
	        for (int j = 0; j < forest.get(vc).getEdges().size(); j++) {
	            forest.get(uc).getEdges().add(forest.get(vc).getEdge(j));
	        }
	        //добавление в компоненту u текущего ребра
	        forest.get(uc).getEdges().add(edges.get(i));

	        //условие завершения алгоритма
	        if (forest.get(uc).getVertices().size() == graph.getVertices().size()) {
	            //processing = false;
	            ui.stepButton.setDisabled(true);
	            ui.clearButton.setEnabled(true);

	        }

	        //удаление компоненты v
	        forest.remove(vc);
	        repaint();
	    }
	}
	
	/**
	 * Метод для очистки графа
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
