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
package de.dm.dr2.main;

import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;

import de.dm.dr2.data.util.Constants;
import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.data.xml.SavedDiceRoll;
import de.dm.dr2.data.xml.XMLInterface;
import de.dm.dr2.data.xml.XmlSaveList;
import de.dm.dr2.gui.Mainframe;

public class Root {

	public static final List<Mainframe> _frames = Collections.synchronizedList(new LinkedList<Mainframe>());
	public static final Map<String, SavedDiceRoll> _registeredExpressions = Collections.synchronizedMap(new HashMap<String, SavedDiceRoll>()); 
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ArrayList<File> loadFiles = new ArrayList<File>();
		
		for (int i = 0; i < args.length; ++i) {
			String argument = args[i];
			if (argument.startsWith(Constants.ARGUMENT_LOAD)) {
				loadFiles.add(new File(UtilFunction.stringAfter(argument, Constants.ARGUMENT_LOAD)));
			/*} else if {*/ //other cases
			} else {
				loadFiles.add(new File(argument)); // pass an unspecified argument as load
			}
		}
		
		final ArrayList<SavedDiceRoll> list = new ArrayList<SavedDiceRoll>();
		for (File file : loadFiles) {
			try {
				XmlSaveList saves = XMLInterface.loadXml(file);
				list.addAll(saves.getSavedDiceRolls());
				for (SavedDiceRoll save : saves.getRegisteredDiceRolls()) {
					register(save);
				}
			} catch (Exception e) {
				//do nothing
			}
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mainframe frame = new Mainframe();
					frame.addToSavedDiceList(list);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void disposeAllFrames() {
		LinkedList<Mainframe> disposeList = new LinkedList<Mainframe>(_frames);
		for (final Mainframe frame : disposeList) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					frame.dispose();
				}
			});
		}
	}
	
	public static void register(SavedDiceRoll save) {
		_registeredExpressions.put(save.name.toLowerCase().trim(), save);
	}
	
	/**
	 * 
	 * @param name
	 * @return {@code true} if a registered expression was removed
	 */
	public static boolean unregister(String name) {
		return _registeredExpressions.remove(name.toLowerCase().trim()) != null;
	}
	
}
