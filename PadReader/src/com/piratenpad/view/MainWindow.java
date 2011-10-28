package com.piratenpad.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow extends JFrame{
	
	static JTabbedPane tabPane = null;
	
	public JTabbedPane getTabPane() {
		return tabPane;
	}

	public void setTabPane(JTabbedPane tabPane) {
		this.tabPane = tabPane;
	}

	public JMenuItem getOpen() {
		return open;
	}

	JMenuItem open = null;
	
	public MainWindow()
	{
		super();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		PadActionListener actionListener = new PadActionListener();
		
		setTitle("Pad Reader");
		setSize(700, 500);
		setLocation(200, 200);
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("resources/padicon.jpg"));

		
//		JPanel panel = new JPanel(new BorderLayout());
//		JPanel panel2 = new JPanel();
//		JTextArea textArea = new JTextArea();
		tabPane = new JTabbedPane();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Pad");
		open = new JMenuItem("Open");
		open.addActionListener(actionListener);
		
		menuBar.add(menu);
		menu.add(open);
		
//        panel.add(new JScrollPane(textArea), BorderLayout.NORTH);
//        tabPane.addTab("Tab1", panel);
        
		Container con = this.getContentPane();
		con.add(menuBar, BorderLayout.NORTH);
		con.add(tabPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
}
