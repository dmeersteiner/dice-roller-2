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
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import javax.swing.JLabel;
import javax.swing.JButton;

import de.dm.dr2.data.consolemessages.ErrorMessage;
import de.dm.dr2.data.consolemessages.StatisticsMessage;
import de.dm.dr2.data.util.Constants;
import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.main.DiceRoller2;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JCheckBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PnlStatistics extends JPanel {

	private static final long serialVersionUID = 6512593543020902164L;
	
	private JTextPane tpRolls;
	private JTextPane tpStats;
	private JTextField txtTimes;
	private JTextField txtSides;
	private JScrollPane scrollPaneHistory;
	private JTextPane tpHistory;
	
	/**
	 * Create the panel.
	 */
	public PnlStatistics() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		final JPanel consolePanel = new JPanel();
		add(consolePanel, BorderLayout.CENTER);
		GridBagLayout gbl_consolePanel = new GridBagLayout();
		gbl_consolePanel.columnWidths = new int[] {0, 0};
		gbl_consolePanel.rowHeights = new int[] {0, 0};
		gbl_consolePanel.columnWeights = new double[]{1.0, 1.0};
		gbl_consolePanel.rowWeights = new double[]{0.0, 0.0};
		consolePanel.setLayout(gbl_consolePanel);
		
		JScrollPane scrollPaneRolls = new JScrollPane();
		GridBagConstraints gbc_scrollPaneRolls = new GridBagConstraints();
		gbc_scrollPaneRolls.weighty = 1.0;
		gbc_scrollPaneRolls.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneRolls.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPaneRolls.gridx = 0;
		gbc_scrollPaneRolls.gridy = 0;
		consolePanel.add(scrollPaneRolls, gbc_scrollPaneRolls);
		scrollPaneRolls.setRequestFocusEnabled(false);
		
		tpRolls = new JTextPane();
		tpRolls.setRequestFocusEnabled(false);
		tpRolls.setEditable(false);
		tpRolls.setAutoscrolls(true);
		scrollPaneRolls.setViewportView(tpRolls);
		
		JScrollPane scrollPaneStats = new JScrollPane();
		scrollPaneStats.setRequestFocusEnabled(false);
		GridBagConstraints gbc_scrollPaneStats = new GridBagConstraints();
		gbc_scrollPaneStats.weighty = 1.0;
		gbc_scrollPaneStats.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneStats.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneStats.gridx = 1;
		gbc_scrollPaneStats.gridy = 0;
		consolePanel.add(scrollPaneStats, gbc_scrollPaneStats);
		
		tpStats = new JTextPane();
		tpStats.setRequestFocusEnabled(false);
		tpStats.setEditable(false);
		tpStats.setAutoscrolls(true);
		scrollPaneStats.setViewportView(tpStats);
		
		scrollPaneHistory = new JScrollPane();
		scrollPaneHistory.setRequestFocusEnabled(false);
		final GridBagConstraints gbc_scrollPaneHistory = new GridBagConstraints();
		gbc_scrollPaneHistory.weighty = 1.0;
		gbc_scrollPaneHistory.gridwidth = 2;
		gbc_scrollPaneHistory.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneHistory.gridx = 0;
		gbc_scrollPaneHistory.gridy = 1;
		consolePanel.add(scrollPaneHistory, gbc_scrollPaneHistory);
		
		tpHistory = new JTextPane();
		tpHistory.setRequestFocusEnabled(false);
		tpHistory.setEditable(false);
		tpHistory.setAutoscrolls(true);
		scrollPaneHistory.setViewportView(tpHistory);
		
		JPanel inputPanel = new JPanel();
		add(inputPanel, BorderLayout.SOUTH);
		
		JLabel lblGenerateStatisticsFor = new JLabel("Generate statistics for");
		lblGenerateStatisticsFor.setFocusable(false);
		inputPanel.add(lblGenerateStatisticsFor);
		
		txtTimes = new JTextField();
		txtTimes.setHorizontalAlignment(SwingConstants.RIGHT);
		inputPanel.add(txtTimes);
		txtTimes.setColumns(5);
		
		JLabel lblD = new JLabel("d");
		lblD.setFocusable(false);
		inputPanel.add(lblD);
		
		txtSides = new JTextField();
		txtSides.setHorizontalAlignment(SwingConstants.LEFT);
		inputPanel.add(txtSides);
		txtSides.setColumns(3);
		
		final JButton btnGenerate = new JButton("Generate");
		btnGenerate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateStatistics();
			}
		});
		inputPanel.add(btnGenerate);
		
		final JCheckBox chckbxShowHistory = new JCheckBox("Show History");
		chckbxShowHistory.setSelected(true);
		chckbxShowHistory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				boolean b = chckbxShowHistory.isSelected();
				scrollPaneHistory.setVisible(b);
				if (b) {
					gbc_scrollPaneHistory.weighty = 1.;
				} else {
					gbc_scrollPaneHistory.weighty = 0.;
				}
				consolePanel.revalidate();
			}
		});
		inputPanel.add(chckbxShowHistory);
		
		KeyAdapter keyAction = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					generateStatistics();
				}
			}
		};

		txtTimes.addKeyListener(keyAction);
		txtSides.addKeyListener(keyAction);
	}
	
	private void generateStatistics() {
		try {
			StatisticsMessage msg = DiceRoller2.getStatMessageOf(txtTimes.getText(), txtSides.getText());
			appendToConsoles(msg);
		} catch (Exception e) {
			ErrorMessage msg = new ErrorMessage(txtTimes.getText()+Constants._standardDiceDivider+txtSides.getText(), e);
			appendToConsoles(msg);
		}
	}

	private void appendToConsoles(StatisticsMessage statMes) {
		tpStats.setText(statMes.getStatisticMessageWithNewLine());
		tpRolls.setText(statMes.getRollMessage());
		UtilFunction.appendToText(tpHistory, statMes.getMessage());
	}

	private void appendToConsoles(ErrorMessage errorMes) {
		String errorText = "Error. See history for details.";
		tpStats.setText(errorText);
		tpRolls.setText(errorText);
		UtilFunction.appendToText(tpHistory, errorMes.getMessage());
	}
	
}
