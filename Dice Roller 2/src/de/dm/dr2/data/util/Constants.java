/*******************************************************************************
 *  Dice Roller 2 is a tabletop rpg dice roll utility tool
 *     Copyright (C) 2014 David Meersteiner
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
 *******************************************************************************/
package de.dm.dr2.data.util;

import de.dm.dr2.data.consolemessages.StatisticsMessage;
import de.dm.dr2.data.diceexpressions.CustomDiceRollExpression;
import de.dm.dr2.data.diceexpressions.ModifiedDiceRollExpression;
import de.dm.dr2.data.parser.ConsoleParser;
import de.dm.dr2.data.parser.DiceExpressionParser;

/**
 * Constant values used in Dice Roller 2
 * @author D. Meersteiner
 *
 */
public class Constants {

	/**
	 * Concatenates more Commands together. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _CommandConcatKeyword = "and";
	/**
	 * Introduces the roll command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _rollCommandKeyword = "roll";
	/**
	 * Introduces the roll command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _rollCommandKeywordShort = "r";
	/**
	 * Introduces the stat command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _statCommandKeyword = "stat";
	/**
	 * Introduces the stat command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _statCommandKeywordShort = "s";
	/**
	 * Introduces the print command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _printCommandKeyword = "print";
	/**
	 * Introduces the print command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _printCommandKeywordShort = "p";
	/**
	 * Introduces the register command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _registerCommandKeyword = "register";
	/**
	 * Introduces the register command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _registerCommandKeywordShort = "reg";
	/**
	 * Introduces the name for the register command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _registerCommandNamingKeyword = "as";
	/**
	 * Introduces the register command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _unregisterCommandKeyword = "unregister";
	/**
	 * Introduces the register command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _unregisterCommandKeywordShort = "unreg";
	/**
	 * Introduces the register list command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _registerListCommandKeyword = "registerlist";
	/**
	 * Introduces the register list command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _registerListCommandKeywordShort = "regl";
	/**
	 * Introduces the register list command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _testCommandKeyword = "test";
	/**
	 * Introduces the register list command. Used by the {@linkplain ConsoleParser}.
	 */
	public static String _testCommandKeywordShort = "t";
	/**
	 * This String in a message will be replaced with a new line.
	 * Used by the {@linkplain ConsoleParser#parsePrint(String)}.
	 */
	public static String _printNewLineReplace = ";;";
	/**
	 * The {@code char} that introduces a literal text passage.
	 * Used by {@linkplain ConsoleParser#parsePrint(String)}.
	 */
	public static char _printliteralMarker = '"';
	/**
	 * The {@code char} that separates the {@code times} and the {@code sides}
	 * in a {@linkplain DiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static char _standardDiceDivider = 'd';
	/**
	 * The {@code char} that separates the {@code times} and the {@code sides}
	 * in a {@linkplain DiceRollExpression}, when it should count from zero.
	 * Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static char _zeroDiceDivider = 'z';
	/**
	 * The {@code String} that follows after the {@code times} and dice divider, when a fudge die should be rolled.
	 * Used by the {@linkplain DiceExpressionParser}.
	 */
	public static String _fudgeDieMarker = "f";
	/**
	 * The {@code String} that introduces {@code Modification.LARGEST}
	 * in a {@linkplain ModifiedDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static String _largestModificationKeyword = "largest";
	/**
	 * The {@code String} that introduces {@code Modification.MAX}
	 * in a {@linkplain ModifiedDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static String _maxModificationKeyword = "max";
	/**
	 * The {@code String} that introduces {@code Modification.LEAST}
	 * in a {@linkplain ModifiedDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static String _leastModificationKeyword = "least";
	/**
	 * The {@code String} that introduces {@code Modification.MIN}
	 * in a {@linkplain ModifiedDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static String _minModificationKeyword = "min";
	/**
	 * The {@code String} that introduces {@code Modification.EXPL}
	 * in a {@linkplain ModifiedDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}.
	 */
	public static String _explodingModificationKeyword = "expl";
	/**
	 * The {@code String} that marks the start of custom side values
	 * in a {@linkplain CustomDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}. 
	 */
	public static String _customStartKeyword = "{";
	/**
	 * The {@code String} that marks the end of custom side values
	 * in a {@linkplain CustomDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}. 
	 */
	public static String _customEndKeyword = "}";
	/**
	 * The {@code String} that separates the custom side values
	 * in a {@linkplain CustomDiceRollExpression}. Also used by the {@linkplain DiceExpressionParser}. 
	 */
	public static String _customSeparatorKeyword = ";";
	/**
	 * The {@code String} that signals a scope of values for the custom sides
	 * of a {@linkplain CustomDiceRollExpression}.
	 * Used by the {@linkplain DiceExpressionParser}. 
	 */
	public static String _customValueScopeKeyword = "..";
	/**
	 * The {@code String} that follows after the counter of the rolled value
	 * Used in {@linkplain StatisticsMessage}. 
	 */
	public static String _statisticsTimes = "x";
	
	/**
	 * A new line
	 */
	public static final String NEW_LINE = "\n";
	/**
	 * The command parameter argument that should invoke a load of an XML File
	 * of SavedDiceRolls
	 */
	public static final String ARGUMENT_LOAD = "-load=";
	
	public static String getDiceDividers() {
		return String.valueOf(_standardDiceDivider)+String.valueOf(_zeroDiceDivider);
	}
	
	/**
	 * Used when help.txt gets read.
	 * @param toReplace
	 * @return
	 */
	public static String replaceConstantNamesWithConstants(String toReplace) {
		//the ..KeywordShort must be replaced before the ..Keyword
		toReplace = toReplace.replace("_rollCommandKeywordShort", _rollCommandKeywordShort);
		toReplace = toReplace.replace("_statCommandKeywordShort", _statCommandKeywordShort);
		toReplace = toReplace.replace("_printCommandKeywordShort", _printCommandKeywordShort);
		toReplace = toReplace.replace("_registerCommandKeywordShort", _registerCommandKeywordShort);
		toReplace = toReplace.replace("_unregisterCommandKeywordShort", _unregisterCommandKeywordShort);
		toReplace = toReplace.replace("_CommandConcatKeyword", _CommandConcatKeyword);
		toReplace = toReplace.replace("_rollCommandKeyword", _rollCommandKeyword);
		toReplace = toReplace.replace("_statCommandKeyword", _statCommandKeyword);
		toReplace = toReplace.replace("_printCommandKeyword", _printCommandKeyword);
		toReplace = toReplace.replace("_registerCommandKeyword", _registerCommandKeyword);
		toReplace = toReplace.replace("_registerCommandNamingKeyword", _registerCommandNamingKeyword);
		toReplace = toReplace.replace("_unregisterCommandKeyword", _unregisterCommandKeyword);
		toReplace = toReplace.replace("_printNewLineReplace", _printNewLineReplace);
		toReplace = toReplace.replace("_printliteralMarker", String.valueOf(_printliteralMarker));
		toReplace = toReplace.replace("_standardDiceDivider", String.valueOf(_standardDiceDivider));
		toReplace = toReplace.replace("_zeroDiceDivider", String.valueOf(_zeroDiceDivider));
		toReplace = toReplace.replace("_largestModificationKeyword", _largestModificationKeyword);
		toReplace = toReplace.replace("_maxModificationKeyword", _maxModificationKeyword);
		toReplace = toReplace.replace("_leastModificationKeyword", _leastModificationKeyword);
		toReplace = toReplace.replace("_minModificationKeyword", _minModificationKeyword);
		toReplace = toReplace.replace("_explodingModificationKeyword", _explodingModificationKeyword);
		toReplace = toReplace.replace("_customStartKeyword", _customStartKeyword);
		toReplace = toReplace.replace("_customEndKeyword", _customEndKeyword);
		toReplace = toReplace.replace("_customSeparatorKeyword", _customSeparatorKeyword);
		toReplace = toReplace.replace("_customValueScopeKeyword", _customValueScopeKeyword);
		return toReplace;
	}
}
