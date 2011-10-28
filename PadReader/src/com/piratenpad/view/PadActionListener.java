package com.piratenpad.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.piratenpad.model.PadLoader;

public class PadActionListener implements ActionListener {

	PadLoader loader;
	
	@Override
	public void actionPerformed(ActionEvent object) {
        if (object.getActionCommand() == "Open") {
        	String url = JOptionPane.showInputDialog("Enter Pad URL: ");
        	if (url != null)
        	{
        		JPanel panel = new JPanel(new BorderLayout());
        		JTextArea area = new JTextArea();
        		JLabel label = new JLabel();

        		area.setEditable(false);
//        		ImageIcon img = new ImageIcon("resources/padicon.jpg");
        		panel.add(label, BorderLayout.NORTH);
        		panel.add(new JScrollPane(area), BorderLayout.CENTER);
        		MainWindow.tabPane.addTab(url, panel);
        		
        		loader = new PadLoader(area, url, MainWindow.tabPane, MainWindow.tabPane.getTabCount() - 1, label);
        		MainWindow.tabPane.setTabComponentAt(MainWindow.tabPane.getTabCount() - 1, new ButtonTabComponent(MainWindow.tabPane, loader));
        		
        		MainWindow.tabPane.setSelectedIndex(MainWindow.tabPane.getTabCount() - 1);
        	}
        }
        /*if (object.getSource() == beenden){
             System.out.println("beenden wurde angeklickt");
        }
        if (object.getSource() == faq){
             System.out.println("faq wurde angeklickt");
        }
        if (object.getSource() == about){
             System.out.println("über wurde angeklickt");
        }*/
   }

}
