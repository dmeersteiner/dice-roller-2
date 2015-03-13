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
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.data.xml.SavedDiceRoll;
import de.dm.dr2.data.xml.XMLInterface;
import de.dm.dr2.data.xml.XmlSaveList;
import de.dm.dr2.main.Main;
import de.dm.dr2.main.Root;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import javax.swing.JList;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class PnlSaved extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7364603769968726909L;
	private JTextPane tpConsole;

	private Vector<SavedDiceRoll> savedRolls = new Vector<SavedDiceRoll>();
	private JList<SavedDiceRoll> rollsList = new JList<SavedDiceRoll>();
	
	private JList<SavedDiceRoll> registeredList = new JList<SavedDiceRoll>();
	
	private Component selectedComponent;
	
	private JFileChooser fc = new JFileChooser();
	private JPanel pnlRegisteredExpressions;
	private JPanel pnlSavedRolls;
	
	/**
	 * Create the panel.
	 */
	public PnlSaved() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JPanel pnlSaves = new JPanel();
		pnlSaves.setBorder(new EmptyBorder(0, 0, 0, 5));
		pnlSaves.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlXML = new JPanel();
		pnlSaves.add(pnlXML, BorderLayout.NORTH);
		pnlXML.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnLoadXML = new JButton("Load XML");
		btnLoadXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setFileFilter(new FileNameExtensionFilter("XML-Files with saved rolls","xml"));
				fc.setMultiSelectionEnabled(false);
				if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						XmlSaveList saves = XMLInterface.loadXml(fc.getSelectedFile());
						savedRolls.addAll(saves.getSavedDiceRolls());
						for (SavedDiceRoll save : saves.getRegisteredDiceRolls()) {
							Root.register(save);
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Couldn't load saved dice rolls.", "Error", JOptionPane.ERROR_MESSAGE);
					}
					
					reloadLists();
				}
			}
		});
		pnlXML.add(btnLoadXML);
		
		JButton btnSaveXML = new JButton("Save XML");
		btnSaveXML.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fc.setFileFilter(new FileNameExtensionFilter("XML-Files with saved rolls","xml"));
				fc.setMultiSelectionEnabled(false);
				if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (!file.getName().toLowerCase().endsWith(".xml")) {
						file = new File(file.getAbsolutePath()+".xml");
					}
					XMLInterface.saveXml(savedRolls, Root._registeredExpressions.values(), file);
				}
				
			}
		});
		pnlXML.add(btnSaveXML);
		setLayout(new GridLayout(0, 2, 0, 0));
		add(pnlSaves);
		
		JPanel pnlRollsBtns = new JPanel();
		pnlSaves.add(pnlRollsBtns, BorderLayout.SOUTH);
		pnlRollsBtns.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnAddRoll = new JButton("Add");
		btnAddRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgCreateSavedDiceRoll dlg = new DlgCreateSavedDiceRoll();
				dlg.setVisible(true);
				if (dlg.getRoll() != null) {
					addToActiveList(dlg.getRoll());
					reloadLists();
				}
			}
		});
		pnlRollsBtns.add(btnAddRoll);
		
		final JButton btnDoRoll = new JButton("Do");
		btnDoRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<SavedDiceRoll> selection = getActiveJList().getSelectedValuesList();
				for (SavedDiceRoll roll : selection) {
					doRoll(roll);
				}
			}
		});
		pnlRollsBtns.add(btnDoRoll);
		
		JButton btnEditRoll = new JButton("Edit");
		btnEditRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<SavedDiceRoll> selection = getActiveJList().getSelectedValuesList();
				for (SavedDiceRoll roll : selection) {
					editSaveFromActiveList(roll);
				}
				reloadLists();
			}
		});
		pnlRollsBtns.add(btnEditRoll);
		
		JButton btnRemoveRoll = new JButton("Remove");
		btnRemoveRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<SavedDiceRoll> selection = getActiveJList().getSelectedValuesList();
				for (SavedDiceRoll roll : selection) {
					removeFromActiveList(roll);
				}
				reloadLists();
			}
		});
		pnlRollsBtns.add(btnRemoveRoll);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		pnlSaves.add(tabbedPane, BorderLayout.CENTER);
		
		pnlSavedRolls = new JPanel();
		tabbedPane.addTab("Saved", null, pnlSavedRolls, null);
		pnlSavedRolls.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneList = new JScrollPane();
		pnlSavedRolls.add(scrollPaneList);
		rollsList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
					List<SavedDiceRoll> selection = rollsList.getSelectedValuesList();
					for (SavedDiceRoll roll : selection) {
						doRoll(roll);
					}
					evt.consume();
				}
			}
		});
		scrollPaneList.setViewportView(rollsList);
		
		pnlRegisteredExpressions = new JPanel();
		tabbedPane.addTab("Registered Expressions", null, pnlRegisteredExpressions, null);
		pnlRegisteredExpressions.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPaneRegs = new JScrollPane();
		pnlRegisteredExpressions.add(scrollPaneRegs);
		
		scrollPaneRegs.setViewportView(registeredList);
		
		JScrollPane scrollPaneConsole = new JScrollPane();
		add(scrollPaneConsole);
		scrollPaneConsole.setFocusable(false);
		scrollPaneConsole.setRequestFocusEnabled(false);
		
		tpConsole = new JTextPane();
		tpConsole.setRequestFocusEnabled(false);
		tpConsole.setEditable(false);
		scrollPaneConsole.setViewportView(tpConsole);
		
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent event) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) event.getSource();
				selectedComponent = sourceTabbedPane.getSelectedComponent();
				btnDoRoll.setEnabled(selectedComponent != pnlRegisteredExpressions);
				reloadLists();
			}
		});

		selectedComponent = tabbedPane.getComponentAt(0);
		
	}
	
	private void reloadLists() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Collections.sort(savedRolls);
				rollsList.setListData(savedRolls);
				rollsList.revalidate();
				
				List<SavedDiceRoll> registered = new LinkedList<SavedDiceRoll>(Root._registeredExpressions.values());
				Collections.sort(registered);
				registeredList.setListData(UtilFunction.toVector(registered));
				registeredList.revalidate();
			}
		});
	}
	
	private void doRoll(SavedDiceRoll roll) {
		String message = Main.parseCommand(roll.expression);
		UtilFunction.appendToText(tpConsole, message);
	}
	
	public void addToRollsList(List<SavedDiceRoll> list) {
		savedRolls.addAll(list);
		reloadLists();
	}
	
	public List<SavedDiceRoll> getSavedList() {
		return savedRolls;
	}
	
	private void removeFromActiveList(SavedDiceRoll roll) {
		if (selectedComponent == pnlSavedRolls) {
			savedRolls.remove(roll);
		} else if (selectedComponent == pnlRegisteredExpressions) {
			Root.unregister(roll.name);
		}
	}
	
	private void addToActiveList(SavedDiceRoll roll) {
		if (selectedComponent == pnlSavedRolls) {
			savedRolls.add(roll);
		} else if (selectedComponent == pnlRegisteredExpressions) {
			if (Root._registeredExpressions.get(roll.name) != null) {
				roll.name = roll.name + "copy";
			}
			Root.register(roll);
		}
	}
	
	private void editSaveFromActiveList(SavedDiceRoll roll) {
		boolean registered = selectedComponent == pnlRegisteredExpressions;
		if (registered) {
			Root.unregister(roll.name);
		}
		DlgCreateSavedDiceRoll dlg = new DlgCreateSavedDiceRoll(roll);
		dlg.setVisible(true);
		if (registered) {
			if (Root._registeredExpressions.get(roll.name) != null) {
				roll.name = roll.name + "copy";
			}
			Root.register(roll);
		}
	}
	
	public JList<SavedDiceRoll> getActiveJList() {
		if (selectedComponent == pnlSavedRolls) {
			return rollsList;
		} else if (selectedComponent == pnlRegisteredExpressions) {
			return registeredList;
		}
		return null;
	}
}
