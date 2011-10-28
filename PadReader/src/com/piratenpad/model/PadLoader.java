package com.piratenpad.model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class PadLoader implements Runnable {

	private JTextArea area;
	private URL url;
	private int time = 10000;
	private volatile Thread thread;
	private JTabbedPane tabPane;
	private int padId;
	private JLabel label;

	public PadLoader(JTextArea area, String urlString, JTabbedPane tabPane, int padId, JLabel label) {
		this.area = area;
		this.tabPane = tabPane;
		this.padId = padId;
		this.label = label;
		getUrl(urlString);
		thread = new Thread(this);
        thread.start();
	}

	public void stop()
	{
		thread = null;
	}
	
	public void run() {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yy HH:mm");
			String title = tabPane.getTitleAt(padId);
			Thread thisThread = Thread.currentThread();
	        while (thread == thisThread) {
				System.out.println("loadPad: " + url);
				tabPane.setTitleAt(padId, "(Loading...) " + title);
				tabPane.setSize(tabPane.getWidth() + 20, tabPane.getHeight());
				loadPad();
				label.setText(formatter.format(new Date()));
				tabPane.setTitleAt(padId, title);
				Thread.sleep(time);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void setLoadTime(int time)
	{
		this.time = time;
	}
	
	private void getUrl(String urlString)
	{
		if (!urlString.startsWith("http://") && !urlString.startsWith("https://")) {
			urlString = "http://" + urlString;
		} else if (urlString.startsWith("https://")) {
			urlString = "https://" + urlString;
		}
		String[] urlArr = urlString.split("/");
		String padName = urlArr[urlArr.length - 1];
		String urlFirstPart = urlArr[0] + "//" + urlArr[2];
		
		urlString = urlFirstPart + "/ep/pad/export/" + padName
		+ "/latest?format=txt";
		
		try {
			this.url = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void loadPad() {

		String ret = "";
		try {

			URLConnection connection = url.openConnection();
			// connection.setRequestProperty("Referer",
			// "http://143930649807.comet.piratenpad.de/comet/xhrXdFrame");
			// connection.setRequestProperty("Referer",
			// "http://piratenpad.de/s21");
			connection.setRequestProperty("User-Agent", "Pad Reader");
//			connection.setRequestProperty("content-type", "text/plain; charset=utf-8"); 

			InputStream stream = connection.getInputStream();
			BufferedInputStream in = new BufferedInputStream(stream);
			int length = 0;
			byte[] data = new byte[1024];
			while ((length = in.read(data)) != -1) {
//				 System.out.write(data);
				ret += new String(data, 0, length, "UTF-8");
				;
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		area.setText(ret);
	}
}
