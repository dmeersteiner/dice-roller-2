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

import de.dm.dr2.data.parser.ConsoleParser;
import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.main.DiceRoller2;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.ListIterator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * A Swing {@code JPanel} that implements a user friendly interface for
 * using the console commands of DR2.
 * @author dmeerste
 * @see ConsoleParser
 */
public class PnlConsole extends JPanel {

	private static final long serialVersionUID = -3854654738304667095L;
	private JTextPane tpConsole;
	private JTextField txtInput;
	
	private LinkedList<String> enteredCommandsList = new LinkedList<String>();
	private ListIterator<String> currentCommandIterator = enteredCommandsList.listIterator();
	private String lastInputString = "";
	private boolean lastKeyWasEnter = false;
	
	private boolean inputDoubleClickAccessEnabled = false;
	
	/**
	 * Creates a new PnlConsole
	 */
	public PnlConsole() {
		addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				requestInputFocus();
			}
		});
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
		tpConsole.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (inputDoubleClickAccessEnabled && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() > 1) {
					e.consume();
					requestInputFocus();
				}
			}
		});
		tpConsole.setRequestFocusEnabled(false);
		tpConsole.setEditable(false);
		tpConsole.setAutoscrolls(true);
		scrollPane.setViewportView(tpConsole);
	}
	
	/**
	 * Requests focus for the input field and sets
	 * the caret to its end.
	 * @see JTextField#requestFocusInWindow()
	 */
	public void requestInputFocus() {
		txtInput.requestFocusInWindow();
		txtInput.setCaretPosition(txtInput.getDocument().getLength());
	}
	
	/**
	 * the detection method for the KeyListener of the input field
	 * @param evt
	 */
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
		UtilFunction.appendToText(tpConsole, DiceRoller2.parseCommand(s));
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

	/**
	 * 
	 * @return the inputDoubleClickAccessEnabled
	 */
	public boolean isInputDoubleClickAccessEnabled() {
		return inputDoubleClickAccessEnabled;
	}

	/**
	 * If set to {@code true} the output text pane can be double clicked
	 * to put the cursor into the input text field.
	 * @param inputDoubleClickAccessEnabled the inputDoubleClickAccessEnabled to set
	 */
	public void setInputDoubleClickAccessEnabled(
			boolean inputDoubleClickAccessEnabled) {
		this.inputDoubleClickAccessEnabled = inputDoubleClickAccessEnabled;
	}
	
	public void doLastCommand() {
		UtilFunction.appendToText(tpConsole, DiceRoller2.parseCommand(lastInputString));
	}

}
