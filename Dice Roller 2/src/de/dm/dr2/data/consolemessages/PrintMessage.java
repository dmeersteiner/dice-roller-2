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
package de.dm.dr2.data.consolemessages;

import de.dm.dr2.data.util.Constants;

/**
 * Wrapper class to hold a {@code String}.
 * 
 * @author D. Meersteiner
 */
public class PrintMessage extends ConsoleMessage {

	private String message;
	
	/**
	 * Creates a new formated message from a {@code String}.
	 * @param message the {@code String} that contains the message
	 */
	public PrintMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message+Constants.NEW_LINE+Constants.NEW_LINE;
	}

	@Override
	public String getValue() {
		return message;
	}

}
