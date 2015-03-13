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
 * A formatted message regarding registered expressions.
 * 
 * @author dmeerste
 * @see ConsoleMessage
 */
public class RegisterMessage extends ConsoleMessage {

	public static final int TYPE_REGISTER = 0;
	public static final int TYPE_UNREGISTER = 1;
	public static final int TYPE_LIST = 2;
	
	private String premessage;
	private String value;
	
	/**
	 * Creates a new formated message from feedback messages of
	 * the registering process and a type.
	 * 
	 * @param registeredMessages the feedback messages of each
	 * @param type a {@code type} Constant of this class
	 */
	public RegisterMessage(String[] registeredMessages, int type) {
		StringBuilder sb = new StringBuilder();
		if (type == TYPE_REGISTER) {
			sb.append("Registering");
		} else if (type == TYPE_UNREGISTER) {
			sb.append("Unregistering");
		} else if (type == TYPE_LIST){
			sb.append("Listing");
		} else {
			sb.append("---Faulty Type---");
		}
		sb.append(" ");
		sb.append(registeredMessages.length);
		sb.append(" Expression"+(registeredMessages.length == 1 ? "" : "s")+":");
		sb.append(Constants.NEW_LINE);
		premessage = sb.toString();
		sb.setLength(0);
		if (registeredMessages.length > 0) {
			for (int i = 0; i < registeredMessages.length; ++i) {
				if (i != 0) {
					sb.append(", ");
				}
				sb.append(registeredMessages[i]);
			}
			sb.append(";");
		}
		sb.append(Constants.NEW_LINE);
		value = sb.toString();
	}
	
	@Override
	public String getMessage() {
		return premessage+value+Constants.NEW_LINE;
	}

	@Override
	public String getValue() {
		return value;
	}

}
