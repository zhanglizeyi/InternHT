/*
	SearchCrawler 

	using JFrame, application
*/

import java.awt.*;
import java.awt.events.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.swing.*;
import java.swing.table.*;

//search web Crawler
public class searchCrawler extends JFrame{

	//Max URLs drop-down values
	private static final String[] max_urls = {"50", "100", "500", "1000"};

	//search GUI controls
	private JTextField startTextField;
	private JComboBox maxComboBox;
	private JCheckBox limitCheckBox;
	private JTextField searchTextField;
	private JCheckBox caseCheckBox;
	private JButton searchButton;

	//search stats GUI controls
	private JLabel crawlingLabel2;
	private JLabel crawledLabel2;
	private JLabel toCrawLabel2;
	private JProgressBar progressBar;
	private JLabel matchesLabel2;

	//Table listing search matches
	private JTable table;

	//Flag for whether or not crawling is underway.
	private boolean crawling;

	//Matches log file print writer.
	private PrintWriter logFileWriter;

	//Constructor for search web crawler.
	public searchCrawler(){
		//set application title
		setTitle("Search Crawler");

		//set window size
		setSize(600, 600);

		//handle window closing events
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				actionExit();
			}
		});
	
		//set up file menu.
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		JMenuItem fileExitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
		fileExitMenuItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				actionExit();
			}
		});
		fileMenu.add(fileExitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	
		// Set up search panel.
		JPanel searchPanel = new JPanel();
		GridBagConstraints constraints;
		GridBagLayout layout = new GridBagLayout();
		searchPanel.setLayout(layout);
		JLabel startLabel = new JLabel("Start URL:");
		constraints = new GridBagConstraints();

		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(startLabel, constraints);
		searchPanel.add(startLabel);
	
		JLabel maxLabel = new JLabel("Max URLs to Crawl: ");
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.EAST;
		constraints.insets = new Insets(5, 5, 0, 0);
		layout.setConstraints(maxLabel, constraints);
		searchPanel.add(maxLabel);

		maxComboBox = new JComboBox(max_urls);
		maxComboBox.setEditable(true);
		constraints = new GridBagConstraints();
		constraints.insets = new insets(5, 5, 0, 0);
		layout.setConstraints(maxComboBox, constraints);
		searchPanel.add(maxComboBox);	
	}

	public static void main(String[] args){
		searchCrawler sc = new searchCrawler();
	}
}



