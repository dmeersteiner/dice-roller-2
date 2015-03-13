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

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.BorderLayout;

import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.main.Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ListIterator;

public class PnlConsole extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3854654738304667095L;
	private JTextPane tpConsole;
	private JTextField txtInput;
	
	private LinkedList<String> enteredCommandsList = new LinkedList<String>();
	private ListIterator<String> currentCommandIterator = enteredCommandsList.listIterator();
	private String lastInputString = "";
	private boolean lastKeyWasEnter = false;
	
	/**
	 * Creates a new 
	 */
	public PnlConsole() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(5, 5));
		
		txtInput = new JTextField();
		txtInput.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent evt) {
				detectInput(evt);
			}
		});
		txtInput.setColumns(10);
		add(txtInput, BorderLayout.SOUTH);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setRequestFocusEnabled(false);
		add(scrollPane, BorderLayout.CENTER);
		
		tpConsole = new JTextPane();
		tpConsole.setRequestFocusEnabled(false);
		tpConsole.setEditable(false);
		tpConsole.setAutoscrolls(true);
		scrollPane.setViewportView(tpConsole);
	}
	
	@Override
	public void validate() {
		super.validate();
		txtInput.requestFocusInWindow();
	}
	
	private void detectInput(KeyEvent evt) {
		switch (evt.getExtendedKeyCode()) {
		
		case KeyEvent.VK_ENTER:
			String input = txtInput.getText();
			doCommandEntered(input);
			lastInputString = input;
			lastKeyWasEnter = true;
			break;
			
		case KeyEvent.VK_UP:
			if (lastKeyWasEnter) {
				setTxt(lastInputString);
			} else if (currentCommandIterator.hasNext()) {
				String s = currentCommandIterator.next();
				if (s.equals(lastInputString) && currentCommandIterator.hasNext())
					s = currentCommandIterator.next();
				lastInputString = s;
				setTxt(lastInputString);
			}
			lastKeyWasEnter = false;
			break;
			
		case KeyEvent.VK_DOWN:
			if (lastKeyWasEnter) {
				setTxt(lastInputString);
			} else if (currentCommandIterator.hasPrevious()) {
				String s = currentCommandIterator.previous();
				if (s.equals(lastInputString) && currentCommandIterator.hasPrevious())
					s = currentCommandIterator.previous();
				lastInputString = s;
				setTxt(lastInputString);
			}
			lastKeyWasEnter = false;
			break;
			
		}
	}
	
	private void doCommandEntered(String s) {
		addToList(s);
		UtilFunction.appendToText(tpConsole, Main.parseCommand(s));
		setTxt(null);
	}
	
	private void setTxt(String s) {
		if (s != null) {
			txtInput.setText(s);
		} else {
			txtInput.setText("");
		}
	}
	
	private void addToList(String s) {
		if (!enteredCommandsList.contains(s)) {
			if (enteredCommandsList.size() >= 10) {
				enteredCommandsList.removeLast();
			}
			enteredCommandsList.addFirst(s);
			currentCommandIterator = enteredCommandsList.listIterator();
		}
	}

}
