package src;

import com.trolltech.qt.core.*;
import com.trolltech.qt.gui.*;

/**
 * Класс пользовательского интерфейса, сгенерированный
 * программой  <b>Qt User Interface Compiler</b>
 */

public class MainWindowUI implements com.trolltech.qt.QUiForm<QMainWindow>
{
    public QWidget centralwidget;
    public QPushButton stepButton;
    public QPushButton clearButton;
    public QPushButton beginButton;
    public QLabel label_1;
    public QLabel label_2;
    public QLabel label_3;
    public QLabel label_4;
    public QLabel label_5;
    public QLabel label_6;
    public QLabel label_7;
    public QLabel label_8;

    
    public MainWindowUI() { 
    	super();
    }

    
    public void setupUi(QMainWindow MainWindow) {
        MainWindow.setObjectName("MainWindow");
        MainWindow.resize(new QSize(785, 378).expandedTo(MainWindow.minimumSizeHint()));
        MainWindow.setMinimumHeight(300);
        MainWindow.setMinimumWidth(600);
        centralwidget = new QWidget(MainWindow);
        centralwidget.setObjectName("centralwidget");
        stepButton = new QPushButton(centralwidget);
        stepButton.setObjectName("stepButton");
        stepButton.setGeometry(new QRect(10, 40, 141, 23));
        clearButton = new QPushButton(centralwidget);
        clearButton.setObjectName("clearButton");
        clearButton.setGeometry(new QRect(10, 70, 141, 23));
        beginButton = new QPushButton(centralwidget);
        beginButton.setObjectName("beginButton");
        beginButton.setGeometry(new QRect(10, 10, 141, 23));
        label_1 = new QLabel(centralwidget);
        label_1.setObjectName("label_1");
        label_1.setGeometry(new QRect(10, 100, 131, 16));
        label_2 = new QLabel(centralwidget);
        label_2.setObjectName("label_2");
        label_2.setGeometry(new QRect(10, 130, 131, 16));
        label_3 = new QLabel(centralwidget);
        label_3.setObjectName("label_3");
        label_3.setGeometry(new QRect(10, 135, 131, 31));
        label_4 = new QLabel(centralwidget);
        label_4.setObjectName("label_4");
        label_4.setGeometry(new QRect(10, 170, 131, 16));
        label_5 = new QLabel(centralwidget);
        label_5.setObjectName("label_5");
        label_5.setGeometry(new QRect(10, 170, 131, 41));
        label_6 = new QLabel(centralwidget);
        label_6.setObjectName("label_6");
        label_6.setGeometry(new QRect(10, 210, 141, 16));
        label_7 = new QLabel(centralwidget);
        label_7.setObjectName("label_7");
        label_7.setGeometry(new QRect(10, 240, 141, 16));
        label_8 = new QLabel(centralwidget);
        label_8.setObjectName("label_8");
        label_8.setGeometry(new QRect(10, 250, 131, 21));
        MainWindow.setCentralWidget(centralwidget);
        retranslateUi(MainWindow);
        MainWindow.connectSlotsByName();
    }

    void retranslateUi(QMainWindow MainWindow) {
        MainWindow.setWindowTitle(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Алгоритм Борувки", null));
        stepButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Сделать шаг", null));
        clearButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Очистить граф", null));
        beginButton.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Начать", null));
        label_1.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Добавить вершину - ЛКМ", null));
        label_2.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Удалить вершину - ПКМ", null));
        label_3.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "по вершине", null));
        label_4.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Выделить вершину - ЛКМ", null));
        label_5.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "по вершине", null));
        label_6.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Связать выбранные - ПКМ", null));
        label_7.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "Удалить ребра между", null));
        label_8.setText(com.trolltech.qt.core.QCoreApplication.translate("MainWindow", "выбранными - Backspace", null));
    }

}

