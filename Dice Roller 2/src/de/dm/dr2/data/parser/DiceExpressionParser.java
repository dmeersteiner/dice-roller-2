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
package de.dm.dr2.data.parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.dm.dr2.data.diceexpressions.*;
import de.dm.dr2.data.util.Constants;
import de.dm.dr2.data.xml.SavedDiceRoll;
import de.dm.dr2.main.DiceRoller2;

/**
 * The {@code DiceExpressionParser} is the main parser
 * of DR2. It reads {@code String}s and tries to convert
 * them into {@link DiceExpression}s or throws a 
 * {@code ParseException} otherwise. The main method is
 * {@link #parse(String)}. It handles the parsing of all
 * DiceExpressions used in DR2.
 * @author David Meersteiner
 *
 */
public abstract class DiceExpressionParser {

	private static final String TIMES_DIVIDER_REGEX = "(?<times>\\S*)\\s*(?<divider>["+Constants.getDiceDividers()+"])\\s*";
	private static final String DICE_REGEX = TIMES_DIVIDER_REGEX+"(?<sides>\\S+)";
	private static final String DICE_PARENTHESES_REGEX = TIMES_DIVIDER_REGEX+"(?<sides>\\(\\S+\\))";
	private static final String CUSTOM_DICE_REGEX = TIMES_DIVIDER_REGEX+Pattern.quote(Constants._customStartKeyword)+"(?<customsides>.+)"+Pattern.quote(Constants._customEndKeyword);
	
	private static final char PARENTHESIS_OPEN = '(';
	private static final char PARENTHESIS_CLOSE = ')';
	
	/**
	 * Parses a {@code String} and returns a valid {@code DiceExpression}.
	 * @param stringToParse the {@code String} that is formatted according to
	 * the rules of the {@code DiceExpression} 
	 * @return a valid {@code DiceExpression} parsed from the {@code String}
	 * @throws ParseException if the {@code String} couldn't be parsed
	 */
	public static DiceExpression parse(String stringToParse) throws ParseException {
		stringToParse = stringToParse.trim();
		
		DiceExpression returnValue = parseAllDiceExpressions(stringToParse);
		if (returnValue != null) {
			return returnValue;
		} else {
			throw new java.text.ParseException(String.format("Invalid dice expression. It was '%s'.", stringToParse), 0);
		}
	}
	
	/**
	 * <p>
	 * Checks if a {@code String} contains a valid {@code DiceExpression}
	 * by calling:
	 * <ol>
	 * <li>{@link #parseMathematicalOperations(String)}</li>
	 * <li>{@link #parseLeadingSigns(String)}</li>
	 * <li>{@link #parseParentheses(String)}</li>
	 * <li>{@link #parseAllModifications(String)}</li>
	 * <li>{@link #parseDiceRoll(String)}</li>
	 * <li>{@link #parseConstant(String)}</li>
	 * </ol>
	 * in this order. If any of these return a {@code DiceExpression} this {@code DiceExpression} will be returned.
	 * </p> 
	 * @param stringToParse the string to test for {@code DiceExpression}s
	 * @return {@link DiceExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static DiceExpression parseAllDiceExpressions(String stringToParse) throws ParseException {
		DiceExpression returnValue;
		
		returnValue = parseMathematicalOperations(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseLeadingSigns(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseParentheses(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseAllModifications(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseDiceRoll(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseCustomDiceRoll(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseFudgeDiceRoll(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseConstant(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseRegisteredExpression(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Checks if a {@code String} contains a '+', '-', '*' or '/' with correct substrings left and right to it
	 * by calling:
	 * <ol>
	 * <li>{@link #parseAddition(String)}</li>
	 * <li>{@link #parseSubtraction(String)}</li>
	 * <li>{@link #parseMultiplication(String)}</li>
	 * <li>{@link #parseDivision(String)}</li>
	 * </ol>
	 * in this order. If any of these return a {@code DiceExpression} this {@code DiceExpression} will be returned.
	 * </p> 
	 * @param stringToParse the string to test for an mathematical operations
	 * @return {@link DiceExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static DiceExpression parseMathematicalOperations(String stringToParse) throws ParseException {
		DiceExpression returnValue;
		
		returnValue = parseAddition(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseSubtraction(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseMultiplication(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseDivision(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		return null;
	}
	
	/**
	 * Checks if a {@code String} contains a '+' with correct substrings left and right to it
	 * by using {@link #determineCorrectSubstrings(String, char)} and returns an {@code AddExpression}
	 * with the parameters being calls of {@link #parse(String)} on the substrings.
	 * @param stringToParse the string to test for an addition
	 * @return {@link AddExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static AddExpression parseAddition(String stringToParse) throws ParseException {
		StringReturn check = determineCorrectSubstrings(stringToParse, '+');
		if (check != null) {
			return new AddExpression(parse(check.left), parse(check.right));
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} contains a '-' with correct substrings left and right to it
	 * by using {@link #determineCorrectSubstrings(String, char)} and returns an {@code AddExpression}
	 * with the second parameter being a {@code NegativeExpression} and
	 * the parameters being calls of {@link #parse(String)} on the substrings.
	 * @param stringToParse the string to test for an subtraction
	 * @return {@code AddExpression} with the second parameter being a {@code NegativeExpression}
	 * or {@code null} if the string doesn't match  
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static AddExpression parseSubtraction(String stringToParse) throws ParseException {
		StringReturn check = determineCorrectSubstrings(stringToParse, '-');
		if (check != null) {
			return new AddExpression(parse(check.left), new NegativeExpression(parse(check.right)));
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} contains a '*' with correct substrings left and right to it
	 * by using {@link #determineCorrectSubstrings(String, char)} and returns a {@code MultiplyExpression} and
	 * the parameters being calls of {@link #parse(String)} on the substrings.
	 * @param stringToParse the string to test for an multiplication
	 * @return {@code MultiplyExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static MultiplyExpression parseMultiplication(String stringToParse) throws ParseException {
		StringReturn check = determineCorrectSubstrings(stringToParse, '*');
		if (check != null) {
			return new MultiplyExpression(parse(check.left), parse(check.right));
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} contains a '/' with correct substrings left and right to it
	 * by using {@link #determineCorrectSubstrings(String, char)} and returns a {@code DivideExpression} and
	 * the parameters being calls of {@link #parse(String)} on the substrings.
	 * @param stringToParse the string to test for an division
	 * @return {@code DivideExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static DivideExpression parseDivision(String stringToParse) throws ParseException {
		StringReturn check = determineCorrectSubstrings(stringToParse, '/');
		if (check != null) {
			return new DivideExpression(parse(check.left), parse(check.right));
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the correct substrings of a {@code String input} that are
	 * separated by the {@code char separator}.
	 * If there are more occurrences of {@code separator} the first correct
	 * substrings are returned.
	 * <p>
	 * The substrings are correct if the substring left to the {@code separator}
	 * contains equal amounts of open and close parentheses.
	 * <p>
	 * If no correct substrings are found, {@code null} will be returned.
	 * @param input the string to test
	 * @param separator the char that separates the Strings that should be returned
	 * @return a {@link StringReturn}, with {@code left} being the left side of 
	 * {@code separator} and {@code right} the right side, or null if there is no correct match
	 */
	protected static StringReturn determineCorrectSubstrings(String input, char separator) {

		/*
		 * Check if we can exit early
		 */
		if (input == null) {
			return null;
		}
		
		/*
		 * Iterate over the input
		 */
		int parenthesesCounter = 0; //currently open parentheses - currently closed parentheses
		boolean isInCustomDiceExpression = false;
		int currentCustomTagIndex = 0;
		for (int i = 0; i < input.length(); ++i) {
			
			//get the current char
			char charValue = input.charAt(i);
						
			if (charValue == Constants._customStartKeyword.charAt(currentCustomTagIndex)) {
				currentCustomTagIndex++;
				if (currentCustomTagIndex >= Constants._customStartKeyword.length()) {
					isInCustomDiceExpression = true;
					currentCustomTagIndex = 0;
				}
				continue;
			}
			
			if (isInCustomDiceExpression) {
				if (charValue == Constants._customEndKeyword.charAt(currentCustomTagIndex)) {
					currentCustomTagIndex++;
					if (currentCustomTagIndex >= Constants._customEndKeyword.length()) {
						isInCustomDiceExpression = false;
						currentCustomTagIndex = 0;
					}
				}
				continue;
			}
			
			if (parenthesesCounter == 0 && charValue == separator) {
				/*
				 * If the expression left to the current char is a correct expression
				 * (determined by having no open parentheses) and the current char
				 * is the searched separator, check if the length of both substrings
				 * is greater than 0 and then return those Strings.
				 */
				if (i > 0 && i+1 < input.length()) {
					return new StringReturn(input.substring(0, i), input.substring(i+1));
				}
				
			} else if (charValue == PARENTHESIS_OPEN) {
				parenthesesCounter++;
			} else if (charValue == PARENTHESIS_CLOSE) {
				parenthesesCounter--;
			}
		}
		
		/*
		 * If there was no match, return null
		 */
		return null;
	}
	
	/**
	 * <p>
	 * Checks if a {@code String} contains a leading '+' or '-'
	 * with correct substrings right to it
	 * by calling:
	 * <ol>
	 * <li>{@link #parseLeadingPlus(String)}</li>
	 * <li>{@link #parseLeadingMinus(String)}</li>
	 * </ol>
	 * in this order. If any of these return a {@code DiceExpression} this {@code DiceExpression} will be returned.
	 * </p> 
	 * @param stringToParse the string to test for an mathematical operations
	 * @return {@link DiceExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static DiceExpression parseLeadingSigns(String stringToParse) throws ParseException {
		DiceExpression returnValue;
		
		returnValue = parseLeadingPlus(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseLeadingMinus(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		return null;
	}
	
	/**
	 * Checks if a {@code String} begins with a leading '+' by using {@link #determineStringAfterLeadingSign(String, char)}
	 * and calls on {@link #parse(String)} on the substring.
	 * @param stringToParse the string to test for a leading plus
	 * @return a {@code DiceExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static DiceExpression parseLeadingPlus(String stringToParse) throws ParseException {
		String check = determineStringAfterLeadingSign(stringToParse, '+');
		if (check != null) {
			return parse(check);
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} begins with a leading '-' by using {@link #determineStringAfterLeadingSign(String, char)}
	 * and returns a new {@code NegativeExpression} with its parameter being a call on {@link #parse(String)} on the substring.
	 * @param stringToParse the string to test for a leading minus
	 * @return a {@code NegativeExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static NegativeExpression parseLeadingMinus(String stringToParse) throws ParseException {
		String check = determineStringAfterLeadingSign(stringToParse, '-');
		if (check != null) {
			return new NegativeExpression(parse(check));
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the correct String of a {@code String input} after a
	 * {@code char leading sign}.
	 * The algorithm uses a {@code Pattern} equal to:
	 * <pre>
	 * <code>
	 * Pattern.compile("\\"+leadingSign+".+")
	 * </code>
	 * </pre>
	 * <p>
	 * If this pattern does not match for the whole {@code input}, {@code null} will be returned.
	 * @param input the string to test
	 * @param leadingSign the leading sign to test
	 * @return a {@code String} or null if there is no match
	 */
	protected static String determineStringAfterLeadingSign(String input, char leadingSign) {
		
		//Check if we can exit early
		if (input == null) {
			return null;
		}
		
		//
		if (input.length() > 0 && input.charAt(0) == leadingSign) {
			return input.substring(1);
		}
		
		// If there was no match, return null
		return null;
	}
	
	/**
	 * Checks if a {@code String} is formatted as {@code DiceRollExpression} by
	 * using {@link #determineStringInParentheses(String)} and returns a {@link ParenthesisExpression}.
	 * @param stringToParse the string to test
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a call of a subsequent {@code parse(String)} throws it
	 * @see DiceExpression
	 */
	protected static ParenthesisExpression parseParentheses(String stringToParse) throws ParseException {
		String check = determineStringInParentheses(stringToParse);
		if (check != null) {
			return new ParenthesisExpression(parse(check));
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the values used for a {@linkplain ParenthesisExpression} from an input.
	 * If {@code input} does not match, {@code null} will be returned.
	 * @param input the string to test
	 * @return the {@code String} between the parentheses or {@code null}
	 */
	protected static String determineStringInParentheses(String input) {
		if (input.startsWith(String.valueOf(PARENTHESIS_OPEN)) && input.endsWith(String.valueOf(PARENTHESIS_CLOSE))) {
			return input.substring(1, input.length()-1);
		}
		//If there was no match, return null
		return null;
	}
	
	/**
	 * <p>
	 * Checks if a {@code String} contains a valid {@code ModifiedDiceRollExpression}
	 * by calling:
	 * <ol>
	 * <li>{@link #parseModificationMax(String)}</li>
	 * <li>{@link #parseModificationMin(String)}</li>
	 * <li>{@link #parseModificationLargest(String)}</li>
	 * <li>{@link #parseModificationLeast(String)}</li>
	 * <li>{@link #parseModificationExpl(String)}</li>
	 * </ol>
	 * in this order. If any of these return a {@code DiceExpression} this {@code DiceExpression} will be returned.
	 * </p> 
	 * @param stringToParse the string to test for an mathematical operations
	 * @return {@link DiceExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a non-defined dice divider was used
	 * @see DiceExpression
	 */
	protected static DiceExpression parseAllModifications(String stringToParse) throws ParseException {
		DiceExpression returnValue;
		
		returnValue = parseModificationMax(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseModificationMin(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseModificationLargest(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseModificationLeast(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		returnValue = parseModificationExpl(stringToParse);
		if (returnValue != null) {
			return returnValue;
		}
		
		return null;
	}
	
	/**
	 * Checks if a {@code String} begins with a leading {@linkplain Constants#_maxModificationKeyword} by
	 * using {@link #determineModifiedDiceRoll(String, String)} and returns a
	 * {@link ModifiedDiceRollExpression} with its modification being {@code Modification.MAX}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * If a modifier was detected it will return a {@code ModifiedDiceRollExpression} with
	 * its modification being {@code Modification.LARGEST} instead, thus correcting the input
	 * to the intended {@code Modification}.
	 * @param stringToParse the string to test for a Max Modification
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a non-defined dice divider was used
	 * @see DiceExpression
	 */
	protected static ModifiedDiceRollExpression parseModificationMax(String stringToParse) throws ParseException {
		ModifiedDiceReturn check = determineModifiedDiceRoll(stringToParse, Constants._maxModificationKeyword);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times== null) {
				check.times = new ConstantExpression(1);
			}
			boolean startFromZero = diceDividerIsZeroDivider(check.divider);
			if (check.modifier != null) {
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.MAX, check.times, check.sides, startFromZero);
			} else {
				//User used the wrong syntax
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.LARGEST, check.modifier, check.times, check.sides, startFromZero);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} begins with a leading {@linkplain Constants#_minModificationKeyword} by
	 * using {@link #determineModifiedDiceRoll(String, String)} and returns a
	 * {@link ModifiedDiceRollExpression} with its modification being {@code Modification.MIN}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * If a modifier was detected it will return a {@code ModifiedDiceRollExpression} with
	 * its modification being {@code Modification.LEAST} instead, thus correcting the input
	 * to the intended {@code Modification}.
	 * @param stringToParse the string to test for a Min Modification
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a non-defined dice divider was used
	 * @see DiceExpression
	 */
	protected static ModifiedDiceRollExpression parseModificationMin(String stringToParse) throws ParseException {
		ModifiedDiceReturn check = determineModifiedDiceRoll(stringToParse, Constants._minModificationKeyword);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times== null) {
				check.times = new ConstantExpression(1);
			}
			boolean startFromZero = diceDividerIsZeroDivider(check.divider);
			if (check.modifier == null) {
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.MIN, check.times, check.sides, startFromZero);
			} else {
				//User used the wrong syntax
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.LEAST, check.modifier, check.times, check.sides, startFromZero);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} begins with a leading {@linkplain Constants#_largestModificationKeyword} by
	 * using {@link #determineModifiedDiceRoll(String, String)} and returns a
	 * {@link ModifiedDiceRollExpression} with its modification being {@code Modification.LARGEST}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * If no modifier was detected it will return a {@code ModifiedDiceRollExpression} with
	 * its modification being {@code Modification.MAX} instead, thus correcting the input
	 * to the intended {@code Modification}.
	 * @param stringToParse the string to test for a Largest Modification
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a non-defined dice divider was used
	 * @see DiceExpression
	 */
	protected static ModifiedDiceRollExpression parseModificationLargest(String stringToParse) throws ParseException {
		ModifiedDiceReturn check = determineModifiedDiceRoll(stringToParse, Constants._largestModificationKeyword);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times== null) {
				check.times = new ConstantExpression(1);
			}
			boolean startFromZero = diceDividerIsZeroDivider(check.divider);
			if (check.modifier != null) {
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.LARGEST, check.modifier, check.times, check.sides, startFromZero);
			} else {
				//User used the wrong syntax
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.MAX, check.times, check.sides, startFromZero);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} begins with a leading {@linkplain Constants#_leastModificationKeyword} by
	 * using {@link #determineModifiedDiceRoll(String, String)} and returns a
	 * {@link ModifiedDiceRollExpression} with its modification being {@code Modification.LEAST}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * If no modifier was detected it will return a {@code ModifiedDiceRollExpression} with
	 * its modification being {@code Modification.MIN} instead, thus correcting the input
	 * to the intended {@code Modification}.
	 * @param stringToParse the string to test for a Largest Modification
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match 
	 * @throws ParseException if a non-defined dice divider was used 
	 * @see DiceExpression
	 */
	protected static ModifiedDiceRollExpression parseModificationLeast(String stringToParse) throws ParseException {
		ModifiedDiceReturn check = determineModifiedDiceRoll(stringToParse, Constants._leastModificationKeyword);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times== null) {
				check.times = new ConstantExpression(1);
			}
			boolean startFromZero = diceDividerIsZeroDivider(check.divider);
			if (check.modifier != null) {
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.LEAST, check.modifier, check.times, check.sides, startFromZero);
			} else {
				//User used the wrong syntax
				return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.MIN, check.times, check.sides, startFromZero);
			}
		} else {
			return null;
		}
	}
	
	/**
	 * Checks if a {@code String} begins with a leading {@linkplain Constants#_explodingModificationKeyword} by
	 * using {@link #determineModifiedDiceRoll(String, String)} and returns a
	 * {@link ModifiedDiceRollExpression} with its modification being {@code Modification.EXPL}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * If no {@code modifier} could be determined, it will be set to {@code sides}.
	 * @param stringToParse the string to test for a Largest Modification
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match  
	 * @throws ParseException if a non-defined dice divider was used
	 * @see DiceExpression
	 */
	protected static ModifiedDiceRollExpression parseModificationExpl(String stringToParse) throws ParseException {
		ModifiedDiceReturn check = determineModifiedDiceRoll(stringToParse, Constants._explodingModificationKeyword);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times== null) {
				check.times = new ConstantExpression(1);
			}
			boolean startFromZero = diceDividerIsZeroDivider(check.divider);
			if (check.modifier == null) {
				check.modifier = check.sides;
			}
			return new ModifiedDiceRollExpression(ModifiedDiceRollExpression.Modification.EXPL, check.modifier, check.times, check.sides, startFromZero);
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the values used for a {@linkplain ModifiedDiceRollExpression} from an input.
	 * The algorithm uses a {@code Pattern} equal to:
	 * <pre>
	 * <code>
	 * Pattern.compile(modificationKeyword+"\\s*(?<modifier>\\d*?)\\s*(?<times>\\d*)\\s*(?<divider>\\D)\\s*(?<sides>\\d+)")
	 * </code>
	 * </pre>
	 * <p>
	 * There are certain scenarios for the {@code ModifiedDiceReturn}'s values:
	 * If group("modifier").isEmpty() returns true, {@code modifier} will be {@code 0}.
	 * If group("times").isEmpty() returns true, {@code times} will be {@code 0}.
	 * If an input like {@code modificationKeyword+"3d6"} is entered,
	 * {@code modifier} will be 0, {@code times} will be 3 and {@code sides} will be 6, as
	 * the {@code d*?} of group("modifier") is reluctant (as defined in {@linkplain Pattern}).
	 * Those values should be set to the wanted values manually.
	 * <p>
	 * If this pattern does not match for the whole {@code input}, {@code null} will be returned.
	 * @param input the string to test
	 * @param modificationKeyword the keyword the string must begin with
	 * @return a {@link ModifiedDiceReturn}, with the extracted values, or null if there is no correct match
	 */
	protected static ModifiedDiceReturn determineModifiedDiceRoll(String input, String modificationKeyword) throws ParseException {
		/*
		 * Check if we can exit early
		 */
		if (!input.contains(modificationKeyword)) {
			return null;
		}
		
		/*
		 * get the values from input or set them to 0
		 */
		Matcher matcher = Pattern.compile(modificationKeyword+"\\s*(?<modifier>\\S*)\\s*"+DICE_REGEX).matcher(input);
		if (matcher.matches()) {
			DiceExpression modifier;
			if (matcher.group("modifier").isEmpty()) {
				modifier = null;
			} else {
				modifier = parse(matcher.group("modifier"));
			}
			DiceExpression times;
			if (matcher.group("times").isEmpty()) {
				times = new ConstantExpression(0);
			} else {
				times = parse(matcher.group("times"));
			}
			DiceExpression sides = parse(matcher.group("sides"));
			return new ModifiedDiceReturn(modifier,times,matcher.group("divider"),sides);
		}
		
		/*
		 * If there was no match, return null
		 */
		return null;
	}
	
	/**
	 * Checks if a {@code String} is formatted as {@code DiceRollExpression} by
	 * using {@link #determineDiceRoll(String)} and returns a {@link DiceRollExpression}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * @param stringToParse the string to test
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a non-defined dice divider was used  
	 * @see DiceExpression
	 */
	protected static DiceRollExpression parseDiceRoll(String stringToParse) throws ParseException {
		DiceReturn check = determineDiceRoll(stringToParse);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times == null) {
				check.times = new ConstantExpression(1);
			}
			boolean startFromZero = diceDividerIsZeroDivider(check.divider);
			return new DiceRollExpression(check.times, check.sides, startFromZero);
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the values used for a {@linkplain ModifiedDiceRollExpression} from an input.
	 * The algorithm uses a {@code Pattern} equal to:
	 * <pre>
	 * <code>
	 * Pattern.compile("(?<times>\\d*)\\s*(?<divider>\\D)\\s*(?<sides>\\d+)")
	 * </code>
	 * </pre>
	 * <p>
	 * There are certain scenarios for the {@code DiceReturn}'s values:
	 * If group("times").isEmpty() returns true, {@code times} will be {@code 0}.
	 * If an input like {@code "d6"} is entered,
	 * {@code times} will be 0 and {@code sides} will be 6.
	 * Those values should be set to the wanted values manually.
	 * <p>
	 * If this pattern does not match for the whole {@code input}, {@code null} will be returned.
	 * @param input the string to test
	 * @return a {@link DiceReturn}, with the extracted values, or null if there is no correct match
	 */
	protected static DiceReturn determineDiceRoll(String input) throws ParseException {
		Matcher matcher = Pattern.compile(DICE_REGEX).matcher(input);
		return determineDiceRoll(input, matcher);
	}
	
	private static DiceReturn determineDiceRoll(String input, Matcher matcher) throws ParseException {
		if (matcher.matches()) {
			String sidesString = matcher.group("sides");
			if (containsEqualAmountOfParentheses(sidesString)) {

				// get the values from input or set them to null
				DiceExpression times;
				if (matcher.group("times").isEmpty()) {
					times = null;
				} else {
					times = parse(matcher.group("times"));
				}
				
				DiceExpression sides = parse(matcher.group("sides"));
				return new DiceReturn(times,matcher.group("divider"),sides);
			} else {
				return determineDiceRoll(input, Pattern.compile(DICE_PARENTHESES_REGEX).matcher(input));
			}
		}
		
		/*
		 * If there was no match, return null
		 */
		return null;
	}
	
	/**
	 * Checks if a {@code String} is formatted as {@code CustomDiceRollExpression} by
	 * using {@link #determineCustomDiceRoll(String)} and returns a {@link CustomDiceRollExpression}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * @param stringToParse the string to test
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a non-defined dice divider was used  
	 * @see DiceExpression
	 */
	protected static DiceRollExpression parseCustomDiceRoll(String stringToParse) throws ParseException {
		CustomDiceReturn check = determineCustomDiceRoll(stringToParse);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			if (check.times== null) {
				check.times = new ConstantExpression(1);
			}
			return new CustomDiceRollExpression(check.times, check.values);
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the values used for a {@linkplain CustomDiceRollExpression} from an input.
	 * The algorithm uses a {@code Pattern} equal to:
	 * <pre>
	 * <code>
	 * Pattern.compile("(?<times>\\d*)\\s*(?<divider>\\D)\\s*"+Constants._customStartKeyword+"(?<customsides>.+)"+Constants._customEndKeyword)
	 * </code>
	 * </pre>
	 * <p>
	 * There are certain scenarios for the {@code DiceReturn}'s values:
	 * The group("customsides") will be split with a regex equal to {@link Constants#_customSeparatorKeyword} and
	 * the values will be parse
	 * If group("times").isEmpty() returns true, {@code times} will be {@code 0}.
	 * If an input like {@code "d"+Constants._customStartKeyword+"2"+Constants._customEndKeyword} is entered,
	 * {@code times} will be 0 and {@code customsides} will be [2].
	 * Those values should be set to the wanted values manually.
	 * <p>
	 * If this pattern does not match for the whole {@code input}, {@code null} will be returned.
	 * @param input the string to test
	 * @return a {@link CustomDiceReturn}, with the extracted values, or null if there is no correct match
	 */
	protected static CustomDiceReturn determineCustomDiceRoll(String input) throws ParseException {
		
		/*
		 * get the values from input or set them to 0
		 */
		Matcher matcher = Pattern.compile(CUSTOM_DICE_REGEX).matcher(input);
		if (matcher.matches()) {
			DiceExpression times;
			if (matcher.group("times").isEmpty()) {
				times = null;
			} else {
				times = parse(matcher.group("times"));
			}
			String customsides = matcher.group("customsides");
			String[] customsidesStringArray = customsides.split(Constants._customSeparatorKeyword);
			/*
			 * customValues needs to be of flexible size because we don't know if the user
			 * always enters correct statements.
			 */
			ArrayList<Double> customValues = new ArrayList<Double>(customsidesStringArray.length);
			for (String s : customsidesStringArray) {
				s = s.trim();
				if (!s.isEmpty()) {// check if user made a mistake and entered an empty value
					if (s.contains(Constants._customValueScopeKeyword)) {// check if user used a scope
						String[] scopeStrings = s.split(Pattern.quote(Constants._customValueScopeKeyword), 2);
						int startScope = parseInt(scopeStrings[0]);
						int endScope = parseInt(scopeStrings[1]);
						for (int i = startScope; i <= endScope; ++i) {
							customValues.add(new Double(i));
						}
					} else {
						customValues.add(parseDouble(s));
					}
				}
			}
			double[] primitiveValues = new double[customValues.size()];
			for (int i = 0; i < customValues.size(); ++i) {
				primitiveValues[i] = customValues.get(i);
			}
			return new CustomDiceReturn(times,matcher.group("divider"), primitiveValues);
		}
		
		/*
		 * If there was no match, return null
		 */
		return null;
	}
	
	/**
	 * Checks if a {@code String} is formatted as {@code CustomDiceRollExpression} by
	 * using {@link #determineCustomDiceRoll(String)} and returns a {@link CustomDiceRollExpression}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * @param stringToParse the string to test
	 * @return a {@code ModifiedDiceRollExpression} or {@code null} if the string doesn't match
	 * @throws ParseException if a non-defined dice divider was used  
	 * @see DiceExpression
	 */
	protected static DiceRollExpression parseFudgeDiceRoll(String stringToParse) throws ParseException {
		DiceExpression check = determineFudgeDiceRollTimes(stringToParse);
		if (check != null) {
			/*
			 * set times to 1 if no times was detected
			 */
			CustomDiceRollExpression exp = new CustomDiceRollExpression();
			exp.setCustomValuesToFudgeDie();
			if (check.isConstant() && ((ConstantExpression) check).getValue() == 0) {
				((ConstantExpression) check).setValue(1);
			}
			exp.setTimes(check);
			return exp;
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the values used for a fudge die, a special {@linkplain CustomDiceExpression} 
	 * The algorithm uses a {@code Pattern} equal to:
	 * <pre>
	 * <code>
	 * Pattern.compile("(?<times>\\d*)"+Constants._fudgeDiceDivider)
	 * </code>
	 * </pre>
	 * If this pattern does not match for the whole {@code input}, {@code null} will be returned.
	 * @param input the string to test
	 * @return the parsed {@code times Integer} or {@code null}
	 */
	protected static DiceExpression determineFudgeDiceRollTimes(String input) throws ParseException {
		
		/*
		 * get the value from input
		 */
		Matcher matcher = Pattern.compile("(?<times>\\d*)["+Constants.getDiceDividers()+"]"+Constants._fudgeDieMarker).matcher(input);
		if (matcher.matches()) {
			if (matcher.group("times").isEmpty()) {
				return new ConstantExpression(0);
			} else {
				return parse(matcher.group("times"));
			}
		}
		
		/*
		 * If there was no match, return null
		 */
		return null;
	}
	
	/**
	 * Checks if a {@code String} is formatted as {@code ConstantExpression} by
	 * using {@link #determineConstant(String)} and returns a {@link ConstantExpression}.
	 * <p>
	 * If no {@code times} could be determined, it will be set to {@code 1}.
	 * @param stringToParse the string to test for a Constant
	 * @return a {@code ConstantExpression} or {@code null} if the string doesn't match  
	 * @see DiceExpression
	 */
	protected static ConstantExpression parseConstant(String stringToParse) {
		Integer check = determineConstant(stringToParse);
		if (check != null) {
			return new ConstantExpression(check.intValue());
		} else {
			return null;
		}
	}
	
	/**
	 * Determines the values used for a {@linkplain ConstantExpression} from an input.
	 * The algorithm uses a {@code Pattern} equal to:
	 * <pre>
	 * <code>
	 * Pattern.compile("\\d+")
	 * </code>
	 * </pre>
	 * If this pattern does not match for the whole {@code input}, {@code null} will be returned.
	 * @param input the string to test
	 * @return the parsed {@code Integer} or {@code null}
	 */
	protected static Integer determineConstant(String input) {
		
		/*
		 * get the value from input
		 */
		Matcher matcher = Pattern.compile("\\d+").matcher(input);
		if (matcher.matches()) {
			return new Integer(parseInt(input));
		}
		
		/*
		 * If there was no match, return null
		 */
		return null;
	}
	
	/**
	 * Checks if a {@code String} is equal to the name of a Registered Expression,
	 * as determined by being in {@link DiceRoller2#REGISTERED_EXPRESSIONS}.
	 * @param stringToParse the string to test for a Registered Expression
	 * @return a {@code DiceExpression} or {@code null} if the string doesn't match  
	 * @see DiceExpression
	 */
	protected static DiceExpression parseRegisteredExpression(String stringToParse) throws ParseException {
		SavedDiceRoll registered = DiceRoller2.REGISTERED_EXPRESSIONS.get(stringToParse);
		if (registered != null) {
			return parse(registered.expression);
		}
		return null;
	}
	
	/**
	 * Checks a String for being equal to {@linkplain Constants#_standardDiceDivider}
	 * and {@linkplain Constants#_zeroDiceDivider}. In the latter case return true,
	 * else throw an {@code ParseException}.
	 * @param diceDivider the string that divides {@code times} and {@code sides}
	 * @return true if diceDiver equals {@linkplain Constants#_zeroDiceDivider}
	 * @throws ParseException if an invalid dice divider was used
	 */
	private static boolean diceDividerIsZeroDivider(String diceDivider) throws ParseException {
		boolean startFromZero;
		if (diceDivider.equals(String.valueOf(Constants._standardDiceDivider))) {
			startFromZero = false;
		} else if (diceDivider.equals(String.valueOf(Constants._zeroDiceDivider))) {
			startFromZero = true;
		} else {
			throw new java.text.ParseException(String.format("Invalid dice divider. It was '%s'.", diceDivider), 0);
		}
		return startFromZero;
	}
	
	private static int parseInt(String string) throws NumberFormatException {
		string = string.trim();
		return Integer.parseInt(string);
	}
	
	private static double parseDouble(String string) throws NumberFormatException {
		string = string.trim();
		return Double.parseDouble(string);
	}
	
	private static boolean containsEqualAmountOfParentheses(String string) {
		int counter = 0;
		char[] array = string.toCharArray();
		for (char c : array) {
			if (c == PARENTHESIS_OPEN) {
				counter++;
			} else if (c == PARENTHESIS_CLOSE) {
				counter--;
			}
		}
		return counter == 0;
	}
	
	/**
	 * A simple class that holds 2 {@code String}s: {@code left} and {@code right}.
	 * Used by {@link DiceExpressionParser} for return values.
	 * @author D. Meersteiner
	 */
	protected static class StringReturn {
		/**
		 * The {@code left String}
		 */
		public String left;
		/**
		 * The {@code right String}
		 */
		public String right;
		
		/**
		 * Creates a new {@code StringReturn}.
		 */
		protected StringReturn() {}
		
		/**
		 * Creates a new {@code StringReturn} with set values.
		 * @param left left to set
		 * @param right right to set
		 */
		protected StringReturn(String left, String right) {
			this.left = left;
			this.right = right;
		}
	}

	/**
	 * A simple class that holds 2 {@code int}s: {@code times} and {@code sides}, and
	 * 1 {@code String}: {@code divider}.
	 * Used by {@link DiceExpressionParser} for return values.
	 * @author D. Meersteiner
	 */
	protected static class DiceReturn {
		
		/**
		 * The {@code times DiceExpression}
		 */
		public DiceExpression times;
		/**
		 * The {@code sides DiceExpression}
		 */
		public DiceExpression sides;
		/**
		 * The {@code divider String}
		 */
		public String divider;
		
		/**
		 * Creates a new {@code DiceReturn}.
		 */
		protected DiceReturn() {}
		
		/**
		 * Creates a new {@code DiceReturn} with set values.
		 * @param times
		 * @param sides
		 */
		protected DiceReturn(DiceExpression times, String divider, DiceExpression sides) {
			this.times = times;
			this.divider = divider;
			this.sides = sides;
		}
	}
	
	/**
	 * A simple class that holds 3 {@code int}s: {@code modifier}, {@code times} and {@code sides}, 
	 * and 1 {@code String}: {@code divider}.
	 * Used by {@link DiceExpressionParser} for return values.
	 * @author D. Meersteiner
	 */
	protected static class ModifiedDiceReturn extends DiceReturn {
		/**
		 * The {@code modifier}
		 */
		public DiceExpression modifier;
		
		/**
		 * Creates a new {@code ModifiedDiceReturn}.
		 */
		protected ModifiedDiceReturn() {}
		
		/**
		 * Creates a new {@code ModifiedDiceReturn} with set values.
		 * @param modifier
		 * @param times
		 * @param sides
		 */
		protected ModifiedDiceReturn(DiceExpression modifier, DiceExpression times, String divider, DiceExpression sides) {
			this.modifier = modifier;
			this.times = times;
			this.divider = divider;
			this.sides = sides;
		}
	}
	
	/**
	 * A simple class that holds 1 {@code DiceExpression}: {@code times}, 
	 * 1 {@code double[]}: {@code customValues} 
	 * and 1 {@code String}: {@code divider}.
	 * Used by {@link DiceExpressionParser} for return values.
	 * @author D. Meersteiner
	 */
	protected static class CustomDiceReturn extends DiceReturn {
		/**
		 * The {@code values double[]}
		 */
		public double[] values;
		
		/**
		 * Creates a new {@code CustomDiceReturn}.
		 */
		protected CustomDiceReturn() {}
		
		/**
		 * Creates a new {@code CustomDiceReturn} with set values.
		 * @param modifier
		 * @param times
		 * @param sides
		 */
		protected CustomDiceReturn(DiceExpression times, String divider, double[] values) {
			this.times = times;
			this.divider = divider;
			this.values = values;
		}
	}
	
}