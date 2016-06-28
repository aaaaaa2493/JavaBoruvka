package src;

import com.trolltech.qt.gui.*;

/**
 * Основной класс приложения, унаследованный от класса
 * <code>QMainWindow</code> и содержащий точку входа
 */

public class Main extends QMainWindow {

	/** Объект пользовательского интерфейса*/
	private MainWindowUI ui;
	
	/** 
	 * <p>Конструктор класса <code>Main</code></p>
	 * <p>Создает и устанавливает пользовательский интерфейс</p>
	 **/
	public Main() {
		ui = new MainWindowUI();
		ui.setupUi(this);
	}
	
	/**
	 * Переопределенный метод класса <code>QMainWindow</code>,
	 * осуществляющий перерисовку окна
	 */
	protected void paintEvent(QPaintEvent e) {
		QPainter p = new QPainter(this);
        //TODO: painting
        p.end();
	}
	
	public static void main(String[] args) {
		
		QApplication.initialize(args);
		Main mainWindow = new Main();
        mainWindow.show();
		QApplication.execStatic();

	}

}
