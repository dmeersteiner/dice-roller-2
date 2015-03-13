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

import java.text.ParseException;

import de.dm.dr2.data.consolemessages.*;
import de.dm.dr2.data.diceexpressions.DiceExpression;
import de.dm.dr2.data.diceexpressions.DiceRollExpression;
import de.dm.dr2.data.parser.ConsoleParser;
import de.dm.dr2.data.util.Constants;

/**
 * All main actions used by Dice Roller 2
 * @author D. Meersteiner
 *
 */
public class Main {

	public static String parseCommand(String input) {
		ConsoleMessage message = ConsoleParser.parse(input);
		if (message != null)
			return message.getMessage();
		else
			return "FATAL ERROR";
	}

	public static String getRollOf(int times, int sides) {
		return getRollOf(getExpression(times, sides));
	}

	public static String getRollOf(DiceExpression expression) {
		ConsoleMessage msg = new DiceRollMessage(expression);
		return msg.getMessage();
	}
	
	public static String getStatsOf(int times, int sides) {
		return getStatMessageOf(times, sides).getMessage();
	}
	
	public static StatisticsMessage getStatMessageOf(int times, int sides) {
		return new StatisticsMessage(getExpression(times, sides));
	}
	
	public static StatisticsMessage getStatMessageOf(String times, String sides) throws ParseException {
		String input = times+Constants._standardDiceDivider+sides;
		return ConsoleParser.parseStats(input.toLowerCase());	
	}

	private static DiceRollExpression getExpression(int times, int sides) {
		return new DiceRollExpression(times, sides);
	}
	
}
