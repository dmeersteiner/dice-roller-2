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
package de.dm.dr2.data.diceexpressions;

import de.dm.dr2.data.util.CollectionReturn;
import de.dm.dr2.data.util.RollReturn;

/**
 * A {@code DiceExpression} is the internalized data format
 * of Dice Roller 2. There are subclasses for each mathematical operator
 * ({@link AddExpression}, {@link NegativeExpression},
 * {@link MultiplyExpression} and {@link DivideExpression}),
 * for the use of parentheses ({@link ParenthesisExpression}),
 * for numbers ({@link ConstantExpression}) and
 * dice rolls ({@link DiceRollExpression}).
 * @author D. Meersteiner
 *
 */
public abstract class DiceExpression {
	
	/**
	 * Returns a {@code RollReturn} that is expected from the specific implementation
	 * of the {@code DiceExpression} abstract class.
	 * @return a new {@code RollReturn} that includes the messages and values of
	 * subjacent {@code RollReturn}s
	 * @see RollReturn 
	 * @see DiceExpression
	 */
	public abstract RollReturn roll();
	
	/**
	 * Returns a {@code CollectionReturn} that is expected from the specific implementation
	 * of the {@code DiceExpression} abstract class.
	 * @return a new {@code CollectionReturn} that includes the messages and values of
	 * subjacent {@code CollectionReturn}s
	 * @see CollectionReturn 
	 * @see DiceExpression
	 */
	public abstract CollectionReturn collect();
	
	/**
	 * This method is used to determine the use of parentheses in {@code RollReturn} messages.
	 * @return true if this DiceExpression is an instance of {@code AddExpression} or {@code NegativeExpression}
	 * @see NegativeExpression
	 * @see AddExpression
	 */
	public boolean isAddOrNegative() {
		return false;
	}
	
	/**
	 * This method is used to determine if this is an instance of {@code ConstantExpression}
	 * @return true if this DiceExpression is an instance of {@code ConstantExpression}
	 * @see ConstantExpression
	 */
	public boolean isConstant() {
		return false;
	}
	
	/**
	 * Returns a string representation of the dice expression. This
	 * string will be read by the user and should be semantically equal to the parsed input string.   
	 */
	public abstract String toString();
	
}
