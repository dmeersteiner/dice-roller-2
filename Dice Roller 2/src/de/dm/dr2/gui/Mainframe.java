/*******************************************************************************
 *  Dice Roller 2 is a tabletop rpg dice roll utility tool
 *     Copyright (C) 2014, 2015 David Meersteiner
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *     
 *     Contact me under:
 *     
 *     dmeersteiner@gmail.com
 *     
 *     David Meersteiner
 *     Am Hang 10
 *     94253 Bischofsmais
 *     GERMANY
 *******************************************************************************/
package de.dm.dr2.gui;

import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.data.xml.SavedDiceRoll;
import de.dm.dr2.main.Root;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class Mainframe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2059380158064523278L;
	
	
	private final PnlHelp pnlHelp = new PnlHelp();
	private final PnlConsole pnlConsole = new PnlConsole();
	private final PnlDice pnlDice = new PnlDice();
	private final PnlSaved pnlSaved = new PnlSaved();
	private final PnlAbout pnlAbout = new PnlAbout();
	private final PnlStatistics pnlStats = new PnlStatistics();
	private JMenuBar menuBar;
	
	/**
	 * Create the frame.
	 */
	public Mainframe() {
		Root._frames.add(this);
		
		setTitle("Dice Roller 2");
		setIconImages(UtilFunction.getDiceRollerIconImages());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 350);
		setLocationByPlatform(true);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnDatei = new JMenu("Program");
		menuBar.add(mnDatei);
		
		JMenuItem mitAbout = new JMenuItem("About");
		mitAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Mainframe frame = new Mainframe();
							frame.setContentPane(frame.pnlAbout);
							frame.menuBar.setVisible(false);
							frame.setSize(650, 400);
							frame.setVisible(true);
							frame.addToSavedDiceList(pnlSaved.getSavedList());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDatei.add(mitAbout);
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mntmHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Mainframe frame = new Mainframe();
							frame.setContentPane(frame.pnlHelp);
							frame.menuBar.setVisible(false);
							frame.setSize(500, 800);
							Mainframe parent = Mainframe.this;
							Point p = parent.getLocation();
							p.x += parent.getWidth();
							Point tmp = p.getLocation();
							tmp.x += frame.getWidth()/2;
							if (UtilFunction.isLocationInScreenBounds(tmp)) {
								frame.setLocation(p);
							} else {
								p.x -= parent.getWidth();
								p.x -= frame.getWidth();
								tmp = p.getLocation();
								tmp.x += frame.getWidth()/2;
								if (UtilFunction.isLocationInScreenBounds(tmp)) {
									frame.setLocation(p);
								} else {
									frame.setLocationByPlatform(true);
								}
							}
							frame.setVisible(true);
							frame.addToSavedDiceList(pnlSaved.getSavedList());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnDatei.add(mntmHelp);
		
		JMenuItem mitQuit = new JMenuItem("Quit");
		mitQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Root.disposeAllFrames();
			}
		});
		mnDatei.add(mitQuit);
		
		JMenu mnFenster = new JMenu("Window");
		menuBar.add(mnFenster);
		
		JMenuItem mitNewWindow = new JMenuItem("New Window");
		mitNewWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Mainframe frame = new Mainframe();
							Mainframe parent = Mainframe.this;
							frame.setLocation(parent.getX()+parent.getWidth()/2, parent.getY()+parent.getHeight()/2);
							frame.setVisible(true);
							frame.addToSavedDiceList(pnlSaved.getSavedList());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		mnFenster.add(mitNewWindow);
		
		JMenuItem mitCloseWindow = new JMenuItem("Close Window");
		mnFenster.add(mitCloseWindow);
		mitCloseWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		mnFenster.addSeparator();
		
		JMenuItem mitConsole = new JMenuItem("Console");
		mitConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(pnlConsole);
				validate();
				pnlConsole.requestFocusInWindow();
			}
		});
		mnFenster.add(mitConsole);
		
		JMenuItem mitDice = new JMenuItem("Dice");
		mitDice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(pnlDice);
				validate();
			}
		});
		mnFenster.add(mitDice);

		JMenuItem mntmSaves = new JMenuItem("Saves");
		mntmSaves.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setContentPane(pnlSaved);
				validate();
			}
		});
		mnFenster.add(mntmSaves);
		
		JMenuItem mntmStatistics = new JMenuItem("Statistics");
		mntmStatistics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setContentPane(pnlStats);
				validate();
			}
		});
		mnFenster.add(mntmStatistics);
		
		setContentPane(pnlDice);
	}
	
	@Override
	public void dispose() {
		Root._frames.remove(this);
		super.dispose();
	}
	
	public void addToSavedDiceList(List<SavedDiceRoll> list) {
		pnlSaved.addToRollsList(list);
	}
	
}
