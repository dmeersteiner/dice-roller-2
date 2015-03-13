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
 * Wrapper class to get the localized message of an {@code exception}.
 * 
 * @author D. Meersteiner
 */
public class ErrorMessage extends ConsoleMessage {

	private Exception ex;
	private String input;
	
	/**
	 * <p>Creates a new formated message from a {@code Exception}.
	 * 
	 * @param ex an {@code Exception}
	 * @param input the entered input that produced the {@code Exception}
	 */
	public ErrorMessage(String input, Exception ex) {
		this.ex = ex;
		this.input = input;
	}
	
	@Override
	public String getMessage() {
		return "Error: "+input+Constants.NEW_LINE+ex.getLocalizedMessage()+Constants.NEW_LINE+Constants.NEW_LINE;
	}

	@Override
	public String getValue() {
		return "[Error]";
	}
	
}
