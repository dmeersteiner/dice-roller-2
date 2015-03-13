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
 * This {@code DiceExpression} is an implementation of the
 * mathematical leading sign "-". A subtraction should use the
 * {@link AddExpression} in conjunction with a {@code NegativeExpression}.
 * @author D. Meersteiner
 * @see DiceExpression
 */
public class NegativeExpression extends DiceExpression {

	private DiceExpression expression;

	/**
	 * Creates a new {@code NegativeExpression}.
	 */
	public NegativeExpression() {}
	
	/**
	 * Creates a new {@code AddExpression} with a set {@code DiceExpression}.
	 * @param expression the expression to negate
	 */
	public NegativeExpression(DiceExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public RollReturn roll() {
		RollReturn roll = expression.roll();
		roll.setMessage("- "+roll.getMessage());
		roll.setValue(-roll.getValue());
		return roll;
	}
	
	@Override
	public CollectionReturn collect() {
		CollectionReturn collection = expression.collect();
		collection.setMessage("- "+collection.getMessage());
		collection.negateValues();
		return collection;
	}
	
	@Override
	public String toString() {
		return "- "+expression.toString();
	}
	
	@Override
	public boolean isAddOrNegative() {
		return true;
	}

	/**
	 * @return the negated expression
	 */
	public DiceExpression getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to negate to set
	 */
	public void setExpression(DiceExpression expression) {
		this.expression = expression;
	}
}
