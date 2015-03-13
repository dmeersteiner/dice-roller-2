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

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import de.dm.dr2.data.diceexpressions.DiceExpression;

/**
 * This {@link TreeMap} holds the return values of a 
 * {@link DiceExpression#collect()}. It supplements the
 * {@code TreeMap} with methods for working like adding
 * or doing other mathematical operations on multiple
 * instances of {@code CollectionReturn}s.
 * @author dmeerste
 *
 */
public class CollectionReturn extends TreeMap<Double, Integer> {
	
	private static final long serialVersionUID = 7893989530063254622L;
	
	private String message;
	
	public CollectionReturn() {
		super();
		message = "";
	}
	
	public CollectionReturn(SortedMap<Double, Integer> map) {
		super(map);
		message = "";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Adds 1 to the given {@code key}, or creates the {@code key}
	 * with value 1, if it didn't exist before.
	 * @param key
	 * @see #add(Double, Integer)
	 */
	public void add(Integer key) {
		Double doubleKey = new Double(key);
		add(doubleKey);
	}
	
	/**
	 * Adds 1 to the given {@code key}, or creates the {@code key}
	 * with value 1, if it didn't exist before.
	 * @param key
	 * @see #add(Double, Integer)
	 */
	public void add(Double key) {
		add(key, 1);
	}
	
	/**
	 * Adds {@code value} to the given {@code key}, or creates the
	 * {@code key} with value {@code value}, if it didn't exist before.
	 * @param key
	 * @param value
	 */
	public void add(Double key, Integer value) {
		if (key == null || value == null) {
			return;
		}
		if (containsKey(key)) {
			Integer newValue = get(key) + value;
			put(key, newValue); 
		} else {
			put(key, value);
		}
	}
	
	/**
	 * Adds all Key/Value Pairs of {@code collectionReturn}
	 * to this. 
	 * @param coll
	 */
	public void add(CollectionReturn collectionReturn) {
		for (Map.Entry<Double, Integer> entry : collectionReturn.entrySet()) {
			add(entry.getKey(), entry.getValue());
		}
	}
	
	/**
	 * Adds all the values (={@code key}) for each time they were
	 * chosen (={@code value}) together 
	 * @return the sum of all {@code key} times {@code value}
	 */
	public double sum() {
		double sum = 0.;
		for (Map.Entry<Double, Integer> entry : entrySet()) {
			sum += entry.getKey() * entry.getValue();
		}
		return sum;
	}
	
	/**
	 * Sets all {@code value}s to their negative value
	 */
	public void negateValues() {
		for (Map.Entry<Double, Integer> entry : entrySet()) {
			put(entry.getKey(), -entry.getValue());
		}
	}
	
	/**
	 * Sets all {@code value}s to a multiple of their value
	 */
	public void mult(int multiplier) {
		for (Map.Entry<Double, Integer> entry : entrySet()) {
			put(entry.getKey(), entry.getValue()*multiplier);
		}
	}
	
	/**
	 * Creates a new {@code CollectionReturn} from this {@code CollectionReturn)
	 * with the keys being greater than {@code fromKey}.
	 * @param fromKey
	 * @param inclusive if true {@code fromKey} will be included in the new {@code CollectionReturn}
	 * @return a new {@code CollectionReturn} with keys greater than {@code fromKey}
	 */
	public CollectionReturn getCollectionAfter(Double fromKey, boolean inclusive) {
		return new CollectionReturn(super.tailMap(fromKey, inclusive));
	}

	/**
	 * Creates a new {@code CollectionReturn} from this {@code CollectionReturn)
	 * with the keys being smaller than {@code toKey}.
	 * @param toKey
	 * @param inclusive if true {@code toKey} will be included in the new {@code CollectionReturn}
	 * @return a new {@code CollectionReturn} with keys smaller than {@code toKey}
	 */
	public CollectionReturn getCollectionBefore(Double toKey, boolean inclusive) {
		return new CollectionReturn(super.headMap(toKey, inclusive));
	}
	
}
