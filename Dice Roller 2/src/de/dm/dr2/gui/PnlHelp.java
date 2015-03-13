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

import java.awt.BorderLayout;

import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;
import javax.swing.JScrollPane;

import de.dm.dr2.data.util.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PnlHelp extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8627972652775399550L;

	/**
	 * Create the panel.
	 */
	public PnlHelp() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		final JTextPane textPane = new JTextPane();
		textPane.setContentType("text/html");
		scrollPane.setViewportView(textPane);
		textPane.setText(getHelpText());
		textPane.setEditable(false);
		textPane.setCaretPosition(0);
		textPane.addHyperlinkListener(new HyperlinkListener() {			
			@Override
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType().equals(EventType.ACTIVATED)) {
					textPane.scrollToReference(e.getDescription());
				}
			}
		});

	}
	
	private String getHelpText() {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(PnlHelp.class.getResourceAsStream("help.txt")));
			String line = reader.readLine();
			while (line != null) {
				sb.append(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Constants.replaceConstantNamesWithConstants(sb.toString());
	}

}
