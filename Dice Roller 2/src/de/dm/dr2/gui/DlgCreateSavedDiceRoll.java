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
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import de.dm.dr2.data.parser.ConsoleParser;
import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.data.xml.SavedDiceRoll;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DlgCreateSavedDiceRoll extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 365970891422375007L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private JTextField txtExpression;
	
	private SavedDiceRoll roll;

	private boolean errorWasDetected = false;
	
	private static final String EMPTY_ERROR_MESSAGE = "enter an expression";
	
	/**
	 * Create the dialog.
	 */
	public DlgCreateSavedDiceRoll() {
		setModalityType(ModalityType.DOCUMENT_MODAL);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImages(UtilFunction.getDiceRollerIconImages());
		setBounds(100, 100, 287, 146);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {75, 200};
		gbl_contentPanel.rowHeights = new int[] {25, 25};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblName = new JLabel("Name:");
			GridBagConstraints gbc_lblName = new GridBagConstraints();
			gbc_lblName.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblName.insets = new Insets(0, 0, 5, 5);
			gbc_lblName.gridx = 0;
			gbc_lblName.gridy = 0;
			contentPanel.add(lblName, gbc_lblName);
		}
		{
			txtName = new JTextField();
			GridBagConstraints gbc_txtName = new GridBagConstraints();
			gbc_txtName.insets = new Insets(0, 0, 5, 0);
			gbc_txtName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtName.gridx = 1;
			gbc_txtName.gridy = 0;
			contentPanel.add(txtName, gbc_txtName);
			txtName.setColumns(10);
		}
		{
			JLabel lblExpression = new JLabel("Expression:");
			GridBagConstraints gbc_lblExpression = new GridBagConstraints();
			gbc_lblExpression.fill = GridBagConstraints.HORIZONTAL;
			gbc_lblExpression.insets = new Insets(0, 0, 0, 5);
			gbc_lblExpression.gridx = 0;
			gbc_lblExpression.gridy = 1;
			contentPanel.add(lblExpression, gbc_lblExpression);
		}
		{
			txtExpression = new JTextField();
			txtExpression.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent arg0) {
					if (errorWasDetected) {
						inputIsParseableAndSetErrorPartsAccordingly(false);
					}
				}
			});
			txtExpression.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					if (errorWasDetected) {
						inputIsParseableAndSetErrorPartsAccordingly(true);
					}
				}
				@Override
				public void focusGained(FocusEvent e) {
					if (errorWasDetected && txtExpression.getText().equals(EMPTY_ERROR_MESSAGE)) {
						txtExpression.setText("");
					}
				}
			});
			GridBagConstraints gbc_txtExpression = new GridBagConstraints();
			gbc_txtExpression.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtExpression.gridx = 1;
			gbc_txtExpression.gridy = 1;
			contentPanel.add(txtExpression, gbc_txtExpression);
			txtExpression.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnSave = new JButton("Save");
				btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (inputIsParseableAndSetErrorPartsAccordingly(true)) {
							if (roll == null) {
								roll = new SavedDiceRoll();
							}
							roll.name = txtName.getText();
							roll.expression = txtExpression.getText();
							dispose();
						}
						
					}
				});
				btnSave.setActionCommand("OK");
				buttonPane.add(btnSave);
				getRootPane().setDefaultButton(btnSave);
			}
			{
				JButton btnCancel = new JButton("Cancel");
				btnCancel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						roll = null;
						dispose();
					}
				});
				btnCancel.setActionCommand("Cancel");
				buttonPane.add(btnCancel);
			}
		}
	}
	
	public DlgCreateSavedDiceRoll(SavedDiceRoll roll) {
		this();
		this.roll = roll;
		txtName.setText(roll.name);
		txtExpression.setText(roll.expression);
	}

	public SavedDiceRoll getRoll() {
		return roll;
	}
	
	private boolean inputIsParseableAndSetErrorPartsAccordingly(boolean showEmptyErrorMessage) {
		try {
			ConsoleParser.parse(txtExpression.getText());
			txtExpression.setForeground(Color.black);
			txtExpression.setToolTipText("");
			errorWasDetected = false;
			return true;
		} catch (Exception ex) {
			txtExpression.setForeground(Color.red);
			txtExpression.setToolTipText(ex.getMessage());
			if (txtExpression.getText().isEmpty() && showEmptyErrorMessage) {
				txtExpression.setText(EMPTY_ERROR_MESSAGE);
			}
			errorWasDetected = true;
			return false;
		}
	}

}
