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
 * This {@code DiceExpression} puts a
 * {@code DiceExpression} in brackets for further mathematical
 * operations to be correct.
 * @author D. Meersteiner
 * @see DiceExpression
 */
public class ParenthesisExpression extends DiceExpression {

	private DiceExpression expression;

	/**
	 * Creates a new {@code ParenthesisExpression}.
	 */
	public ParenthesisExpression() {}

	/**
	 * Creates a new {@code ParenthesisExpression} with a set {@code DiceExpression}.
	 * @param expression the expression to put in brackets
	 */
	public ParenthesisExpression(DiceExpression expression) {
		this.expression = expression;
	}
	
	@Override
	public RollReturn roll() {
		
		//hide unnecessary parentheses
		DiceExpression chosenExpression = expression;
		while (chosenExpression instanceof ParenthesisExpression) {
			chosenExpression = ((ParenthesisExpression) chosenExpression).expression;
		}
		
		RollReturn roll = chosenExpression.roll();
		roll.setMessage("("+roll.getMessage()+")");
		
		return roll;
	}
	
	@Override
	public CollectionReturn collect() {
		
		//hide unnecessary parentheses
		DiceExpression chosenExpression = expression;
		while (chosenExpression instanceof ParenthesisExpression) {
			chosenExpression = ((ParenthesisExpression) chosenExpression).expression;
		}
		
		CollectionReturn collection = chosenExpression.collect();

		collection.setMessage("("+collection.getMessage()+")");
		
		return null;
	}
	
	@Override
	public String toString() {
		//hide unnecessary parentheses
		DiceExpression chosenExpression = expression;
		while (chosenExpression instanceof ParenthesisExpression) {
			chosenExpression = ((ParenthesisExpression) chosenExpression).expression;
		}
		return "("+chosenExpression.toString()+")";
	}
	
	@Override
	public boolean isConstant() {
		return expression.isConstant();
	}

	/**
	 * @return the expression
	 */
	public DiceExpression getExpression() {
		return expression;
	}

	/**
	 * @param expression the expression to set
	 */
	public void setExpression(DiceExpression expression) {
		this.expression = expression;
	}
	
}
