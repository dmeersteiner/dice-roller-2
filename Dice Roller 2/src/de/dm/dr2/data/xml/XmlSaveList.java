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
package de.dm.dr2.data.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlSaveList {

	private List<SavedDiceRoll> savedDiceRolls = new ArrayList<SavedDiceRoll>();
	private List<SavedDiceRoll> registeredDiceRolls = new ArrayList<SavedDiceRoll>();
	
	public XmlSaveList() {}

	@XmlElement (name = "diceroll")
	public List<SavedDiceRoll> getSavedDiceRolls() {
		return savedDiceRolls;
	}

	public void setSavedDiceRolls(List<SavedDiceRoll> savedDiceRolls) {
		this.savedDiceRolls = savedDiceRolls;
	}

	/**
	 * @return the registeredDiceRolls
	 */
	@XmlElement (name = "registered")
	public List<SavedDiceRoll> getRegisteredDiceRolls() {
		return registeredDiceRolls;
	}

	/**
	 * @param registeredDiceRolls the registeredDiceRolls to set
	 */
	public void setRegisteredDiceRolls(List<SavedDiceRoll> registeredDiceRolls) {
		this.registeredDiceRolls = registeredDiceRolls;
	}
	
}
