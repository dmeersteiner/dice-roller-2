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
package de.dm.dr2.data.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import de.dm.dr2.data.consolemessages.*;
import de.dm.dr2.data.util.Constants;
import de.dm.dr2.data.util.UtilFunction;
import de.dm.dr2.data.xml.SavedDiceRoll;
import de.dm.dr2.main.Root;

/**
 * The {@code ConsoleParser} is a parser
 * of DR2. It reads {@code String}s and converts
 * them into {@link ConsoleMessage}s. The main method is
 * {@link #parse(String)}. It handles the parsing of all
 * Console Commands used in DR2.
 * @author dmeerste
 *
 */
public abstract class ConsoleParser {
	
	/**
	 * Generates a {@link ConsoleMessage} from an input.
	 * It checks for the supported Console Commands of DR2 as defined by
	 * {@link Constants}.
	 * If no Command was detected it assumes the Roll Command
	 * was wanted. If any exception is thrown during a Command
	 * (usually because of a {@code ParseException} from
	 * {@code DiceExpressionParser#parse(String)}) an {@link ErrorMessage}
	 * is generated.
	 * @param stringToParse the input {@code String}, usually a
	 * Console Command with Dice Expressions
	 * @return an appropriate {@code ConsoleMessage} to the {@code stringToParse}
	 */
	public static ConsoleMessage parse(String stringToParse) {
		stringToParse = stringToParse.trim();
		
		try {
			String substring;
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._rollCommandKeyword);
			if (substring != null) {
				return parseRoll(substring.toLowerCase());
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._rollCommandKeywordShort);
			if (substring != null) {
				return parseRoll(substring.toLowerCase());
			}
			
			String[] testSubstings = detectTestSubstrings(stringToParse);
			if (testSubstings != null) {
				return parseTest(testSubstings);
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._statCommandKeyword);
			if (substring != null) {
				return parseStats(substring.toLowerCase());
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._statCommandKeywordShort);
			if (substring != null) {
				return parseStats(substring.toLowerCase());
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._printCommandKeyword);
			if (substring != null) {
				return parsePrint(substring);
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._printCommandKeywordShort);
			if (substring != null) {
				return parsePrint(substring);
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._registerCommandKeyword);
			if (substring != null) {
				return parseRegister(substring);
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._registerCommandKeywordShort);
			if (substring != null) {
				return parseRegister(substring);
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._unregisterCommandKeyword);
			if (substring != null) {
				return parseUnregister(substring);
			}
			
			substring = UtilFunction.stringAfter(stringToParse, Constants._unregisterCommandKeywordShort);
			if (substring != null) {
				return parseUnregister(substring);
			}
			
			if (stringToParse.toLowerCase().startsWith(Constants._registerListCommandKeyword)) {
				return listRegistered();
			}
			
			if (stringToParse.toLowerCase().startsWith(Constants._registerListCommandKeywordShort)) {
				return listRegistered();
			}
			
			return parseRoll(stringToParse.toLowerCase()); // if no command was detected, assume the roll command was wanted
			
		} catch (Exception e) {
			return new ErrorMessage(stringToParse, e);
		}
	}
	
	/**
	 * Generates a {@link DiceRollMessage} from the {@code stringToParse}.
	 * @param stringToParse a {@code String} that contains {@code DiceExpression}s
	 * formatted for the {@code DiceExpressionParser}.
	 * @return a {@code DiceRollMessage} according to the {@code stringToParse}
	 * @throws ParseException
	 */
	public static DiceRollMessage parseRoll(String stringToParse) throws ParseException {
		return new DiceRollMessage(DiceExpressionParser.parse(stringToParse));
	} 
	
	private static String[] detectTestSubstrings(String stringToParse) throws ParseException {
		stringToParse = stringToParse.toLowerCase();
		if (stringToParse.contains(" "+Constants._testCommandKeyword+" ")) {
			return stringToParse.split(" "+Constants._testCommandKeyword+" ", 2);
		} else if (stringToParse.contains(" "+Constants._testCommandKeywordShort+" ")) {
			return stringToParse.split(" "+Constants._testCommandKeywordShort+" ", 2);
		}
		return null;
	}
	
	/**
	 * 
	 * @param substrings
	 * @return
	 * @throws ParseException
	 * @throws IllegalArgumentException if substrings length != 2
	 */
	public static TestMessage parseTest(String[] substrings) throws ParseException {
		if (substrings.length != 2) {
			throw new IllegalArgumentException("substrings[] length must be 2");
		}
		return new TestMessage(DiceExpressionParser.parse(substrings[0]),
				DiceExpressionParser.parse(substrings[1]));
	}
	
	/**
	 * Generates a {@link StatisticsMessage} from the {@code stringToParse}.
	 * @param stringToParse a {@code String} that contains {@code DiceExpression}s
	 * formatted for the {@code DiceExpressionParser}.
	 * @return a {@code StatisticsMessage} according to the {@code stringToParse}
	 * @throws ParseException
	 */
	public static StatisticsMessage parseStats(String stringToParse) throws ParseException {		
		return new StatisticsMessage(DiceExpressionParser.parse(stringToParse));
	}
	
	/**
	 * Generates a {@link PrintMessage} from the {@code stringToParse}.
	 * @param stringToParse a {@code String} that contains Console Commands - as
	 * defined by {@link #parse(String)} - and literals between
	 * {@link Constants#_printliteralMarker}.
	 * @return a {@code PrintMessage} according to the {@code stringToParse}
	 * @throws ParseException
	 */
	public static PrintMessage parsePrint(String stringToParse) throws ParseException {
		StringBuilder printMessageBuilder = new StringBuilder();
		StringBuilder nonLiteralBuilder = new StringBuilder();
		boolean isInLiteral = false;
		
		//iterate over the input string
		for (int i = 0; i < stringToParse.length(); ++i) {
			char c = stringToParse.charAt(i);
			
			//
			if (c == Constants._printliteralMarker) {
				isInLiteral = !isInLiteral;
				if (isInLiteral) {
					/* 
					 * literal starts now, so append value of the entered
					 * command in front of the literal
					 */
					appendParsedValueIfNotEmpty(printMessageBuilder, nonLiteralBuilder.toString());
					nonLiteralBuilder.setLength(0); //reset
				}
			} else {
				if (isInLiteral) {
					printMessageBuilder.append(c);
				} else {
					nonLiteralBuilder.append(c);
				}
			}
		}
		/*
		 * print command ends now, so append any non literal commands that
		 * weren't added before
		 */
		appendParsedValueIfNotEmpty(printMessageBuilder, nonLiteralBuilder.toString());
		
		//do replacement of keywords
		String message = printMessageBuilder.toString();
		message = message.replace(Constants._printNewLineReplace, Constants.NEW_LINE);
		
		return new PrintMessage(message);
	}
	
	/**
	 * A help method for {@link #parsePrint(String)}.<p>
	 * If {@code value} is not empty, or just consists of whitespaces,
	 * it will parse it with {@link ConsoleParser#parse(String)} and appends
	 * the value (via {@link ConsoleMessage#getValue()} of the returned message to
	 * {@code appendTo}
	 * @param appendTo a {@code StringBuilder}
	 * @param value a {@code String} that should be parsed
	 */
	private static void appendParsedValueIfNotEmpty(StringBuilder appendTo, String value) {
		if (!value.trim().isEmpty()) {
			ConsoleMessage mes = parse(value);
			appendTo.append(mes.getValue());
		}
	}
	
	/**
	 * Registers the entered expressions and generates a
	 * {@link RegisterMessage} from the {@code stringToParse} and feedback
	 * from the registering process.
	 * @param stringToParse a {@code String} that contains Syntax for the
	 * Register Command
	 * @return a {@code RegisterMessage} according to the {@code stringToParse}
	 */
	public static RegisterMessage parseRegister(String stringToParse) {
		String[] commands = stringToParse.split("\\s+(?i)("+Constants._CommandConcatKeyword+")\\s+");
		String[] registeredMessages = new String[commands.length];
		for (int i = 0; i < commands.length; ++i) {
			String[] registerParts = commands[i].split("\\s+(?i)("+Constants._registerCommandNamingKeyword+")\\s+", 2);
			if (registerParts.length == 2) {
				registeredMessages[i] = registerExpressionOrReturnErrorMessage(registerParts[0], registerParts[1]);
			} else {
				registeredMessages[i] = "error near '"+commands[i]+"' - command syntax was faulty";
			}
		}
		return new RegisterMessage(registeredMessages, RegisterMessage.TYPE_REGISTER);
	}
	
	/**
	 * A help method for {@link #parseRegister(String)}.<p>
	 * Registers {@code expression} as {@code name} if the expression is
	 * parseable by {@link DiceExpressionParser#parse(String)}. Either way
	 * it returns an appropriate {@code String}, which describes any errors
	 * or the successful registering process.
	 * @param expression the expression to register
	 * @param name the name of the registered expression
	 * @return a {@code String} which contains a feedback message for the user
	 */
	private static String registerExpressionOrReturnErrorMessage(String expression, String name) {
		String errorMessage = "unable to register '"+expression.trim()+"'";
		try {
			DiceExpressionParser.parse(expression);
			SavedDiceRoll save = new SavedDiceRoll();
			save.expression = expression.trim();
			save.name = name.trim();
			Root.register(save);
			return "'"+save.expression+"' as '"+save.name+"'";
		} catch (ParseException e) {
			return errorMessage+" - expression was faulty";
		}
	}
	
	/**
	 * Unregisters the entered names and generates a
	 * {@link RegisterMessage} from the {@code stringToParse} and feedback
	 * from the unregistering process.
	 * @param stringToParse a {@code String} that contains Syntax for the
	 * Unregister Command
	 * @return a {@code RegisterMessage} according to the {@code stringToParse}
	 */
	public static RegisterMessage parseUnregister(String stringToParse) {
		String[] commands = stringToParse.split("\\s+(?i)("+Constants._CommandConcatKeyword+")\\s+");
		String[] registeredMessages = new String[commands.length];
		for (int i = 0; i < commands.length; ++i) {
			registeredMessages[i] = unregisterExpressionOrReturnErrorMessage(commands[i]);
		}
		
		return new RegisterMessage(registeredMessages, RegisterMessage.TYPE_UNREGISTER);
	}
	
	/**
	 * A help method for {@link #parseUnregister(String)}.<p>
	 * Unregisters the registered Expression with {@code name} if the
	 * expression is expression. Either way it returns an appropriate
	 * {@code String}, which describes any errors.
	 * or the successful unregistering process.
	 * @param name the name of the registered expression to unregister
	 * @return a {@code String} that contains a message about the unregistering
	 * process
	 */
	private static String unregisterExpressionOrReturnErrorMessage(String name) {		
		if (Root.unregister(name)) {
			return "'"+name+"' unregistered";
		} else {
			return "unable to unregister '"+name+"' - it did not exist";
		}
	}
	
	/**
	 * Generates a {@link RegisterMessage} that lists all registered
	 * expressions.
	 * @return a {@code RegisterMessage} that lists all registered Expressions
	 */
	public static RegisterMessage listRegistered() {
		ArrayList<SavedDiceRoll> regs = new ArrayList<SavedDiceRoll>(Root._registeredExpressions.values());
		Collections.sort(regs);
		String[] messages = new String[regs.size()];
		for (int i = 0; i < regs.size(); ++i) {
			SavedDiceRoll sdr = regs.get(i);
			messages[i] = String.format(sdr.name+": "+sdr.expression);
		}
		return new RegisterMessage(messages, RegisterMessage.TYPE_LIST);
	}
}
