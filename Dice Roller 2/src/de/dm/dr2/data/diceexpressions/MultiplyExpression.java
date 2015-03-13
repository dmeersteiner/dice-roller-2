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
 * This {@code DiceExpression} connects two
 * {@code DiceExpression}s with the mathematical "*"-operator.
 * @author D. Meersteiner
 * @see DiceExpression
 */
public class MultiplyExpression extends DiceExpression {

	private DiceExpression first;
	private DiceExpression second;
	
	/**
	 * Creates a new {@code MultiplyExpression}.
	 */
	public MultiplyExpression() {}
	
	/**
	 * Creates a new {@code MultiplyExpression} with set {@code DiceExpression}s.
	 * @param first first factor
	 * @param second second factor
	 */
	public MultiplyExpression(DiceExpression first, DiceExpression second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public RollReturn roll() {
		RollReturn firstRet = first.roll();
		RollReturn secondRet = second.roll();
		
		//Build value
		double value = firstRet.getValue()*secondRet.getValue();
		
		//Build message
		StringBuilder sb = new StringBuilder();
		if (first.isAddOrNegative()) {
			sb.append("(");
			sb.append(firstRet.getMessage());
			sb.append(")");
		} else {
			sb.append(firstRet.getMessage());
		}
		sb.append(" * ");
		if (second.isAddOrNegative()) {
			sb.append("(");
			sb.append(secondRet.getMessage());
			sb.append(")");
		} else {
			sb.append(secondRet.getMessage());
		}
		
		RollReturn roll = new RollReturn();
		roll.setMessage(sb.toString());
		roll.setValue(value);
		return roll;
	}
	
	@Override
	public CollectionReturn collect() {
		ConstantExpression exp;
		CollectionReturn collection;
		if (first instanceof ConstantExpression) {
			exp = (ConstantExpression) first;
			collection = second.collect();
		} else if (second instanceof ConstantExpression) {
			collection = first.collect();
			exp = (ConstantExpression) second;
		} else {
			throw new UnsupportedOperationException("Can't multiply Collections");
		}
		collection.setMessage(collection.getMessage()+" with magnitude "+exp.getValue());
		collection.mult(exp.getValue());
		return collection;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (first.isAddOrNegative()) {
			sb.append("(");
			sb.append(first.toString());
			sb.append(")");
		} else {
			sb.append(first.toString());
		}
		sb.append(" * ");
		if (second.isAddOrNegative()) {
			sb.append("(");
			sb.append(second.toString());
			sb.append(")");
		} else {
			sb.append(second.toString());
		}
		return sb.toString();
	}

	/**
	 * @return the first factor
	 */
	public DiceExpression getFirst() {
		return first;
	}

	/**
	 * @param first the first factor to set
	 */
	public void setFirst(DiceExpression first) {
		this.first = first;
	}

	/**
	 * @return the second factor
	 */
	public DiceExpression getSecond() {
		return second;
	}

	/**
	 * @param second the second factor to set
	 */
	public void setSecond(DiceExpression second) {
		this.second = second;
	}

}
