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
import de.dm.dr2.data.util.UtilFunction;

/**
 * This {@code DiceExpression} is an extension of the
 * {@link DiceRollExpression}. It allows custom sides for die.
 * Only {@code double} sides are allowd, though {@code int}s will
 * be displayed as such.
 * <p>
 * {@code sides} and {@code startFromZero} are not used in this subclass
 * @author D. Meersteiner
 * @see DiceExpression
 *
 */
public class CustomDiceRollExpression extends DiceRollExpression {

	/**
	 * The custom values of the die
	 */
	protected double[] customValues;
	
	/**
	 * Creates a new {@code CustomDiceRollExpression}.
	 */
	public CustomDiceRollExpression() {}
	
	/**
	 * Creates a new {@code CustomDiceRollExpression} with set values.
	 * @param times the times the die gets rolled
	 * @param values the custom values of the die
	 */
	public CustomDiceRollExpression(int times, double[] values) {
		this(new ConstantExpression(times), values);
	}
	
	/**
	 * Creates a new {@code CustomDiceRollExpression} with set values.
	 * @param times the times the die gets rolled
	 * @param values the custom values of the die
	 */
	public CustomDiceRollExpression(DiceExpression times, double[] values) {
		this.times = times;
		this.customValues = values;
	}
	
	@Override
	public CollectionReturn collect() {
		CollectionReturn collection = new CollectionReturn();
		StringBuilder sb = new StringBuilder();
		
		//Build the RollReturn
		sb.append(_messageStarttag);
		for (int i = 0; i < times.roll().getValueAsFloorInt(); ++i) {
			int specificIndex = UtilFunction.getRandom().nextInt(customValues.length);
			double specificValue = customValues[specificIndex];
			
			//Build value
			collection.add(specificValue);
			
			//Build message
			if (specificValue < 0.) {
				sb.append(" - ");
				sb.append(UtilFunction.doubleToStringWithMinimumPrecision(-specificValue));
			} else {
				if (i != 0) {
					sb.append(" + ");
				}
				sb.append(UtilFunction.doubleToStringWithMinimumPrecision(specificValue));
			}
		}
		sb.append(_messageEndtag);
		
		collection.setMessage(sb.toString());
		return collection;
	}
	
	@Override
	public String toString() {	
		StringBuilder sb = new StringBuilder();
		sb.append(Constants._customStartKeyword);
		for (int i = 0; i < customValues.length; ++i) {
			if (i != 0) {
				sb.append(Constants._customSeparatorKeyword);
			}
			sb.append(UtilFunction.doubleToStringWithMinimumPrecision(customValues[i]));
		}
		sb.append(Constants._customEndKeyword);
		return String.valueOf(times)+(Constants._standardDiceDivider)+sb.toString();
	}

	/**
	 * @return the customValues
	 */
	public double[] getCustomValues() {
		return customValues;
	}

	/**
	 * @param customValues the customValues to set
	 */
	public void setCustomValues(double[] customValues) {
		this.customValues = customValues;
	}
	
	public void setCustomValuesToFudgeDie() {
		this.customValues = new double[] {-1,0,1};
	}
}
