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
import de.dm.dr2.data.util.Constants;
import de.dm.dr2.data.util.RollReturn;
import de.dm.dr2.data.util.UtilFunction;

/**
 * This {@code DiceExpression} is an implementation of 
 * a n-sided die that will be rolled x times.
 * <p>
 * n is determined by the {@code sides} variable. x is determined by
 * the {@code times} variable.
 * 
 * @author D. Meersteiner
 * @see DiceExpression
 * @see #getSides()
 * @see #getTimes()
 */
public class DiceRollExpression extends DiceExpression {
	
	/**
	 * determines if the count begins with zero instead of one
	 */
	protected boolean startFromZero;

	/**
	 * how often a die with {@code sides} sides should be rolled
	 */
	protected DiceExpression times;
	/**
	 * sides of the die that gets rolled {@code times} times
	 */
	protected DiceExpression sides;
	
	/**
	 * The start of the {@link RollReturn} message.
	 * <p>
	 * Default is {@code "["} 
	 */
	protected static String _messageStarttag = "[";
	/**
	 * The end of the {@link RollReturn} message.
	 * <p>
	 * Default is {@code "]"}
	 */
	protected static String _messageEndtag = "]";
	
	/**
	 * Creates a new {@code DiceRollExpression}.
	 */
	public DiceRollExpression() {
		this(0,0);
	}
	
	/**
	 * Creates a new {@code DiceRollExpression} with set values.
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 */
	public DiceRollExpression(int times, int sides) {
		this(times, sides, false);
	}
	
	/**
	 * Creates a new {@code DiceRollExpression} with set values.
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 */
	public DiceRollExpression(DiceExpression times, DiceExpression sides) {
		this(times, sides, false);
	}
	
	/**
	 * Creates a new {@code DiceRollExpression} with set values.
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 * @param startFromZero should the die count from zero instead of one
	 */
	public DiceRollExpression(int times, int sides, boolean startFromZero) {
		this(new ConstantExpression(times), new ConstantExpression(sides), startFromZero);
	}
	
	/**
	 * Creates a new {@code DiceRollExpression} with set values.
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 * @param startFromZero should the die count from zero instead of one
	 */
	public DiceRollExpression(DiceExpression times, DiceExpression sides, boolean startFromZero) {
		this.times = times;
		this.sides = sides;
		this.startFromZero = startFromZero;
	}
	
	@Override
	public RollReturn roll() {
		//Generate Values
		CollectionReturn collection = collect();
		
		RollReturn roll = new RollReturn();
		roll.setMessage(collection.getMessage());
		roll.setValue(collection.sum());
		return roll;
	}
	
	@Override 
	public CollectionReturn collect() {
		CollectionReturn collection = new CollectionReturn();
		StringBuilder sb = new StringBuilder();
		
		sb.append(_messageStarttag);
		for (int i = 0; i <times.roll().getValueAsFloorInt(); ++i) {
			int specificValue = UtilFunction.roll(sides.roll().getValueAsFloorInt(), startFromZero);
			
			//Add rolled Value to Collection
			collection.add(specificValue);
			
			//Build message
			if (i != 0) {
				sb.append(" + ");
			}	
			sb.append(specificValue);
		}
		sb.append(_messageEndtag);
		
		collection.setMessage(sb.toString());
		return collection;
	}
	
	@Override
	public String toString() {	
		return times.toString()+(startFromZero? Constants._zeroDiceDivider : Constants._standardDiceDivider)+sides.toString();
	}

	/**
	 * gets the how often a die with {@link #getSides()} sides should be rolled
	 * @return the times 
	 */
	public DiceExpression getTimes() {
		return times;
	}

	/**
	 * sets how often a die with {@link #getSides()} sides should be rolled
	 * @param times the times to set
	 */
	public void setTimes(DiceExpression times) {
		this.times = times;
	}

	/**
	 * gets the sides of the die that gets rolled {@link #getTimes()} times
	 * @return the sides
	 */
	public DiceExpression getSides() {
		return sides;
	}

	/**
	 * sets the sides of the die that gets rolled {@link #getTimes()} times
	 * @param sides the sides to set
	 */
	public void setSides(DiceExpression sides) {
		this.sides = sides;
	}

	/**
	 * determines if the count begins with zero instead of one
	 * @return the startFromZero
	 */
	public boolean isStartFromZero() {
		return startFromZero;
	}

	/**
	 * determines if the count begins with zero instead of one
	 * @param startFromZero the startFromZero to set
	 */
	public void setStartFromZero(boolean startFromZero) {
		this.startFromZero = startFromZero;
	}
	
}
