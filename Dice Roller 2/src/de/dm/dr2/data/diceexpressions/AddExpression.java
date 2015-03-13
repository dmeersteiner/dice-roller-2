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

import de.dm.dr2.data.util.*;

/**
 * This {@code DiceExpression} connects two
 * {@code DiceExpression}s with the mathematical "+"-operator.
 * @author D. Meersteiner
 * @see DiceExpression
 */
public class AddExpression extends DiceExpression {

	private DiceExpression first;
	private DiceExpression second;
	
	/**
	 * Creates a new {@code AddExpression}.
	 */
	public AddExpression() {}
	
	/**
	 * Creates a new {@code AddExpression} with set {@code DiceExpression}s.
	 * @param first first addend
	 * @param second second addend
	 */
	public AddExpression(DiceExpression first, DiceExpression second) {
		this.first = first;
		this.second = second;
	}
	
	@Override
	public RollReturn roll() {
		RollReturn firstRet = first.roll();
		RollReturn secondRet = second.roll();
		
		//Build value
		double value = firstRet.getValue()+secondRet.getValue();
		
		//Build message
		StringBuilder sb = new StringBuilder();
		sb.append(firstRet.getMessage());
		if (second instanceof NegativeExpression) {
			sb.append(" "); // a NegativeExpression already has an leading sign in its message
		} else {
			sb.append(" + ");
		}
		sb.append(secondRet.getMessage());
		
		RollReturn roll = new RollReturn();
		roll.setMessage(sb.toString());
		roll.setValue(value);
		return roll;
	}
	
	@Override
	public CollectionReturn collect() {
		CollectionReturn firstRet = first.collect();
		CollectionReturn secondRet = second.collect();
		
		//Build message
		StringBuilder sb = new StringBuilder();
		sb.append(firstRet.getMessage());
		if (second instanceof NegativeExpression) {
			sb.append(" "); // a NegativeExpression already has an leading sign in its message
		} else {
			sb.append(" + ");
		}
		sb.append(secondRet.getMessage());
		
		CollectionReturn collection = new CollectionReturn();
		collection.setMessage(sb.toString());
		collection.add(firstRet);
		collection.add(secondRet);
		return collection;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(first.toString());
		if (second instanceof NegativeExpression) {
			sb.append(" "); // a NegativeExpression already has an leading sign in its message
		} else {
			sb.append(" + ");
		}
		sb.append(second.toString());
		return sb.toString();
	}
	
	@Override
	public boolean isAddOrNegative() {
		return true;
	}

	/**
	 * @return the first addend
	 */
	public DiceExpression getFirst() {
		return first;
	}

	/**
	 * @param first the first addend to set
	 */
	public void setFirst(DiceExpression first) {
		this.first = first;
	}

	/**
	 * @return the second addend
	 */
	public DiceExpression getSecond() {
		return second;
	}

	/**
	 * @param second the second addend to set
	 */
	public void setSecond(DiceExpression second) {
		this.second = second;
	}
	

}
