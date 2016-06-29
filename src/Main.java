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
	 */
	public void begin() {
		/** 
		 * TODO: начало работы алгоритма
		 */
	}
	
	/**
	 * Осуществление шага работы алгоритма
	 */
	public void step() {
		/** 
		 * TODO: шаг алгоритма
		 */
	}
	
	/**
	 * Метод для очистки графа
	 */
	public void clear() {
		/** 
		 * TODO: очитска графа
		 */
	}
	
	public static void main(String[] args) {
		
		QApplication.initialize(args);
		Main mainWindow = new Main();
        mainWindow.show();
		QApplication.execStatic();

	}

}
