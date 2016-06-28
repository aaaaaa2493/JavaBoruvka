package src;

import com.trolltech.qt.gui.*;

/**
 * �������� ����� ����������, �������������� �� ������
 * <code>QMainWindow</code> � ���������� ����� �����
 */

public class Main extends QMainWindow {

	/** ������ ����������������� ����������*/
	private MainWindowUI ui;
	
	/** 
	 * <p>����������� ������ <code>Main</code></p>
	 * <p>������� � ������������� ���������������� ���������</p>
	 **/
	public Main() {
		ui = new MainWindowUI();
		ui.setupUi(this);
	}
	
	/**
	 * ���������������� ����� ������ <code>QMainWindow</code>,
	 * �������������� ����������� ����
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
