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
import de.dm.dr2.main.DiceRoller2;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * A Swing {@code JFrame} that contains panels which
 * implement the dice rolling features Dice Roller 2
 * supports.
 * <br>
 * When a {@code Mainframe} is created, all it's
 * panels are initialized and can be retrieved via the getters.
 * If a panel should be displayed, use {@link
 * JFrame#setContentPane(java.awt.Container)}.
 * @author D. Meersteiner
 *
 */
public class Mainframe extends JFrame {

	private static final long serialVersionUID = -2059380158064523278L;
	
	private final PnlConsole pnlConsole = new PnlConsole();
	private final PnlDice pnlDice = new PnlDice();
	private final PnlSaved pnlSaved = new PnlSaved();
	private final PnlStatistics pnlStats = new PnlStatistics();
	private JMenuBar menuBar;
	
	/**
	 * Create the frame.
	 */
	public Mainframe() {
		DiceRoller2.FRAMES.add(this);
		
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
						JFrame frame = new JFrame();
						frame.setContentPane(new PnlAbout());
						frame.setSize(650, 400);
						frame.setVisible(true);
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
						JFrame frame = new JFrame();
						frame.setContentPane(new PnlHelp());
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
					}
				});
			}
		});
		mnDatei.add(mntmHelp);
		
		JMenuItem mitQuit = new JMenuItem("Quit");
		mitQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DiceRoller2.disposeAllFrames();
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
		DiceRoller2.FRAMES.remove(this);
		super.dispose();
	}
	
	public void addToSavedDiceList(List<SavedDiceRoll> list) {
		pnlSaved.addToRollsList(list);
	}

	/**
	 * @return the pnlConsole of this Mainframe
	 * @see PnlConsole
	 */
	public PnlConsole getPnlConsole() {
		return pnlConsole;
	}

	/**
	 * @return the pnlDice of this Mainframe
	 * @see PnlDice
	 */
	public PnlDice getPnlDice() {
		return pnlDice;
	}

	/**
	 * @return the pnlSaved of this Mainframe
	 * @see PnlSaved
	 */
	public PnlSaved getPnlSaved() {
		return pnlSaved;
	}

	/**
	 * @return the pnlStats of this Mainframe
	 * @see PnlStatistics
	 */
	public PnlStatistics getPnlStats() {
		return pnlStats;
	}
	
}
