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
 * This {@code DiceExpression} is an implementation of 
 * a constant number of the integer type.
 * 
 * @author D. Meersteiner
 * @see DiceExpression
 */
public class ConstantExpression extends DiceExpression {
	
	private int value;

	/**
	 * Creates a new {@code ConstantExpression}.
	 */
	public ConstantExpression() {}
	
	/**
	 * Creates a new {@code ConstantExpression} with a set value.
	 * @param value the value of the constant
	 */
	public ConstantExpression(int value) {
		this.value = value;
	}
	
	@Override
	public RollReturn roll() {
		RollReturn roll = new RollReturn();
		roll.setMessage(String.valueOf(value));
		roll.setValue(value);
		return roll;
	}
	
	@Override
	public CollectionReturn collect() {
		CollectionReturn coll = new CollectionReturn();
		coll.add(value);
		coll.setMessage(String.valueOf(value));
		return coll;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	@Override
	public boolean isConstant() {
		return true;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
}
