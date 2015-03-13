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

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;

import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.main.Main;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.GridLayout;

import javax.swing.ImageIcon;

public class PnlDice extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7364603769968726909L;
	private JTextPane tpConsole;

	/**
	 * Create the panel.
	 */
	public PnlDice() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel pnlDice = new JPanel();
		pnlDice.setBorder(new EmptyBorder(0, 0, 0, 5));
		pnlDice.setLayout(new GridLayout(0, 2, 5, 5));
		add(pnlDice);
		
		JButton btn1d4 = new JButton("1d4");
		btn1d4.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/iconD4_64.png")));
		btn1d4.setMnemonic(KeyEvent.VK_4);
		btn1d4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 4));
			}
		});
		pnlDice.add(btn1d4);
		
		JButton btn1d6 = new JButton("1d6");
		btn1d6.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/iconD6_64.png")));
		btn1d6.setMnemonic(KeyEvent.VK_6);
		btn1d6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 6));
			}
		});
		pnlDice.add(btn1d6);
		
		JButton btn2d6 = new JButton("2d6");
		btn2d6.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/icon2D6_64.png")));
		btn2d6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(2, 6));
			}
		});
		pnlDice.add(btn2d6);
		
		JButton btn1d8 = new JButton("1d8");
		btn1d8.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/iconD8_64.png")));
		btn1d8.setMnemonic(KeyEvent.VK_8);
		btn1d8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 8));
			}
		});
		pnlDice.add(btn1d8);
		
		JButton btn1d10 = new JButton("1d10");
		btn1d10.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/iconD10_64.png")));
		btn1d10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 10));
			}
		});
		pnlDice.add(btn1d10);
		
		JButton btn1d12 = new JButton("1d12");
		btn1d12.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/iconD12_64.png")));
		btn1d12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 12));
			}
		});
		pnlDice.add(btn1d12);
		
		JButton btn1d20 = new JButton("1d20");
		btn1d20.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/iconD20_64.png")));
		btn1d20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 20));
			}
		});
		pnlDice.add(btn1d20);
		
		JButton btn1d100 = new JButton("1d100");
		btn1d100.setIcon(new ImageIcon(PnlDice.class.getResource("/de/dm/dr2/images/icon2D10_64.png")));
		btn1d100.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UtilFunction.appendToText(tpConsole, Main.getRollOf(1, 100));
			}
		});
		pnlDice.add(btn1d100);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFocusable(false);
		scrollPane.setRequestFocusEnabled(false);
		add(scrollPane, BorderLayout.CENTER);
		
		tpConsole = new JTextPane();
		tpConsole.setRequestFocusEnabled(false);
		tpConsole.setEditable(false);
		tpConsole.setAutoscrolls(true);
		scrollPane.setViewportView(tpConsole);

	}

}
