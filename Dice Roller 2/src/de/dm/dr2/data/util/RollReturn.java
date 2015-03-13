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
package de.dm.dr2.data.util;

import de.dm.dr2.data.diceexpressions.DiceExpression;

/**
 * This class holds the return values of a
 * {@link DiceExpression#roll()}.
 * @author dmeerste
 *
 */
public class RollReturn {
	
	private String message;
	private double value;
	
	public RollReturn() {
		this.message = "";
		this.value = 0;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public int getValueAsFloorInt() {
		return (int) Math.floor(value);
	}
	
	@Override
	public String toString() {
		return "RollReturn [message=" + message + ", value=" + value + "]";
	}
	
	
}
