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
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

public class PnlAbout extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public PnlAbout() {
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		
		JLabel picture = new JLabel("");
		panel.add(picture);
		picture.setIcon(new ImageIcon(PnlAbout.class.getResource("/de/dm/dr2/images/logo_256.png")));
		
		JTextPane txtpnDiceRoller = new JTextPane();
		panel.add(txtpnDiceRoller);
		txtpnDiceRoller.setBorder(new LineBorder(new Color(0, 0, 0)));
		txtpnDiceRoller.setEditable(false);
		txtpnDiceRoller.setText(" Dice Roller 2 is a tabletop rpg dice roll utility tool\r\n    Copyright (C) 2014 David Meersteiner\r\n\r\n    This program is free software: you can redistribute it and/or modify\r\n    it under the terms of the GNU General Public License as published by\r\n    the Free Software Foundation, either version 3 of the License, or\r\n    (at your option) any later version.\r\n\r\n    This program is distributed in the hope that it will be useful,\r\n    but WITHOUT ANY WARRANTY; without even the implied warranty of\r\n    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\r\n    GNU General Public License for more details.\r\n\r\n    You should have received a copy of the GNU General Public License\r\n    along with this program.  If not, see <http://www.gnu.org/licenses/>.\r\n    \r\n    Contact me under:\r\n    \r\n    dmeersteiner@gmail.com\r\n    \r\n    David Meersteiner\r\n    Am Hang 10\r\n    94253 Bischofsmais\r\n    GERMANY");

	}

}
