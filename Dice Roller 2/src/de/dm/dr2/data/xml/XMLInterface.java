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

import java.io.File;
import java.util.Collection;

import javax.xml.bind.JAXB;

public class XMLInterface {
	
	public static void saveXml(Collection<SavedDiceRoll> rolls, Collection<SavedDiceRoll> registered, File xmlFile) {
		XmlSaveList list = new XmlSaveList();
		for (SavedDiceRoll roll : rolls) {
			list.getSavedDiceRolls().add(roll);			
		}
		for (SavedDiceRoll register : registered) {
			list.getRegisteredDiceRolls().add(register);
		}
		JAXB.marshal(list, xmlFile);
	}
	
	public static XmlSaveList loadXml(File xmlFile) {
		XmlSaveList list = JAXB.unmarshal(xmlFile, XmlSaveList.class);
		return list;
	}
}
