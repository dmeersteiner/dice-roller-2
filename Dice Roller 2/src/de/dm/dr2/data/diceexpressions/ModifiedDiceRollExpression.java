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

import java.util.ArrayList;
import java.util.Collections;

import de.dm.dr2.data.util.CollectionReturn;
import de.dm.dr2.data.util.RollReturn;
import de.dm.dr2.data.util.UtilFunction;

/**
 * This {@code DiceExpression} is an extension of the
 * {@link DiceRollExpression}. It allows certain modifications
 * to the roll. Some modifications use {@code modifier} (called 'x' below)
 * for further altering. 
 * <ul>
 * <li>
 * {@linkplain Modification#LARGEST} will select the x highest rolled values.
 * </li>
 * <li>
 * {@linkplain Modification#MAX} will select the 1 highest rolled values.
 * </li>
 * <li>
 * {@linkplain Modification#LEAST} will select the x lowest rolled values.
 * </li>
 * <li>
 * {@linkplain Modification#MIN} will select the 1 lowest rolled values.
 * </li>
 * <li>
 * {@linkplain Modification#EXPL} will roll the die again, for each rolled value that was at least x. 
 * </li>
 * </ul>
 * <p>
 * New extension to {@code ModifiedDiceRollExpression} must override {@link #roll()}
 * and {@link #toString()} and should include the Modifications supported by this class.
 * </p>
 * @author D. Meersteiner
 * @see DiceExpression
 * @see Modification
 */
public class ModifiedDiceRollExpression extends DiceRollExpression {

	/**
	 * The {@code Modification} this dice roll should use.
	 */
	protected Modification modification;
	/**
	 * A value used by some Modifications
	 */
	protected DiceExpression modifier = new ConstantExpression(0);
	
	/**
	 * The separator between the rolled values in the {@link RollReturn} message.
	 * <p>
	 * Default is {@code ", "}
	 */
	protected static String _rollSeperatortag = ", ";
	
	/**
	 * The separator between the selected and rolled values in the {@link RollReturn} message.
	 * <p>
	 * Default is {@code " | "}
	 */
	protected static String _selectionSeperatortag = " | ";
	
	/**
	 * The start of the exploded rolls in the {@link RollReturn} message.
	 * <p>
	 * Default is {@link DiceRollExpression#_messageStarttag}
	 */
	protected static String _explodingStarttag = _messageStarttag;
	
	/**
	 * The end of the exploded rolls in the {@link RollReturn} message.
	 * <p>
	 * Default is {@link DiceRollExpression#_messageEndtag}
	 */
	protected static String _explodingEndtag = _messageEndtag;
	
	/**
	 * Creates a new {@code ModifiedDiceRollExpression}.
	 */
	public ModifiedDiceRollExpression() {}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 */
	public ModifiedDiceRollExpression(Modification modification, int times, int sides) {
		super(times, sides);
		this.modification = modification;
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 */
	public ModifiedDiceRollExpression(Modification modification, DiceExpression times, DiceExpression sides) {
		super(times, sides);
		this.modification = modification;
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 * @param startFromZero should the die count from zero instead of one
	 */
	public ModifiedDiceRollExpression(Modification modification, int times, int sides, boolean startFromZero) {
		super(times, sides, startFromZero);
		this.modification = modification;
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 * @param startFromZero should the die count from zero instead of one
	 */
	public ModifiedDiceRollExpression(Modification modification, DiceExpression times, DiceExpression sides, boolean startFromZero) {
		super(times, sides, startFromZero);
		this.modification = modification;
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param modifier the modifier used by some modifications
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 */
	public ModifiedDiceRollExpression(Modification modification, int modifier, int times, int sides) {
		this(modification, times, sides);
		this.modifier = new ConstantExpression(modifier);
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param modifier the modifier used by some modifications
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 */
	public ModifiedDiceRollExpression(Modification modification, DiceExpression modifier, DiceExpression times, DiceExpression sides) {
		this(modification, times, sides);
		this.modifier = modifier;
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param modifier the modifier used by some modifications
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 * @param startFromZero should the die count from zero instead of one
	 */
	public ModifiedDiceRollExpression(Modification modification, int modifier, int times, int sides, boolean startFromZero) {
		this(modification, times, sides, startFromZero);
		this.modifier = new ConstantExpression(modifier);
	}

	/**
	 * Creates a new {@code ModifiedDiceRollExpression} with set values.
	 * @param modification the modification used
	 * @param modifier the modifier used by some modifications
	 * @param times the times the die gets rolled
	 * @param sides how many sides the die has
	 * @param startFromZero should the die count from zero instead of one
	 */
	public ModifiedDiceRollExpression(Modification modification, DiceExpression modifier, DiceExpression times, DiceExpression sides, boolean startFromZero) {
		this(modification, times, sides, startFromZero);
		this.modifier = modifier;
	}
	
	@Override
	public String toString() {
		if (modification != null) {
			return String.format(modification.getMessage(), modifier.toString(), super.toString());
		} else {
			return super.toString();
		}
		
	}
	
	@Override
	public CollectionReturn collect() {
		switch (modification) {
		case MAX:
			return rollLargest(1);
		case MIN:
			return rollLeast(1);
		case LARGEST:
			return rollLargest(modifier.roll().getValueAsFloorInt());
		case LEAST:
			return rollLeast(modifier.roll().getValueAsFloorInt());
		case EXPL:
			return rollExploding(modifier.roll().getValueAsFloorInt());
		default:
			return super.collect();
		}
	}
	
	/**
	 * 
	 * @param selectionMaximum the maximum count of values selected
	 * @return {@link CollectionReturn} with appropriate message and value
	 * @see ModifiedDiceRollExpression#rollLeastOrLargest(Modification, int)
	 */
	protected CollectionReturn rollLargest(int selectionMaximum) {
		return rollLeastOrLargest(Modification.LARGEST, selectionMaximum);
	}
	
	/**
	 * 
	 * @param selectionMaximum the maximum count of values selected
	 * @return {@link CollectionReturn} with appropriate message and value
	 * @see ModifiedDiceRollExpression#rollLeastOrLargest(Modification, int)
	 */
	protected CollectionReturn rollLeast(int selectionMaximum) {
		return rollLeastOrLargest(Modification.LEAST, selectionMaximum);
	}
	
	/**
	 * 
	 * Uses {@linkplain Collections#sort(java.util.List)} (and implicit {@linkplain Integer#compareTo(Integer)}) for sorting of the values.
	 * 
	 * @param modification {@code Modification.LARGEST} or {@code Modification.LEAST} should be used
	 * @param selectionMaximum the maximum of selected values
	 * @return {@link CollectionReturn} with appropriate message and value
	 * @throws IllegalArgumentException if {@code modification} is not {@code Modification#MAX},
	 * {@code Modification#LARGEST}, {@code Modification#MIN} or {@code Modification#LEAST}
	 * @throws IllegalArgumentException if {@code selectionMaximum} is greater than {@code times} the die gets rolled, 
	 * i.e. it wants to select more values than are given
	 */
	private CollectionReturn rollLeastOrLargest(Modification modification, int selectionMaximum) {
		//check if less dice than selectionMaximum are rolled
		int times = this.times.roll().getValueAsFloorInt();
		if (times < selectionMaximum) {
			throw new IllegalArgumentException("Can't select more values than are rolled. "
					+ "Values to be selected: "+selectionMaximum+", "
					+ "values generated: "+times);
		}
		
		//Get what is needed
		ArrayList<Integer> rolledValues = generatedRolledValuesList(times);
		ArrayList<Integer> valuesToControl = new ArrayList<Integer>(rolledValues);
		ArrayList<Integer> selectedValues = new ArrayList<Integer>(selectionMaximum);
		
		//Sort the values for easy selection
		Collections.sort(valuesToControl);
		
		//Select the wanted values
		while (selectedValues.size() < selectionMaximum) {
			Integer selection;
			switch (modification) {
			case MAX:
			case LARGEST:
				selection = valuesToControl.get(valuesToControl.size()-1);
				break;
			case MIN:
			case LEAST:
				selection = valuesToControl.get(0);
				break;
			default:
				throw new IllegalArgumentException("Only MAX, LARGEST, MIN and LEAST are allowed as an modification. Modification was: "+modification);
			}
			selectedValues.add(selection);
			valuesToControl.remove(selection);
		}
		
		//Get the selected values into the original sorting
		{
			ArrayList<Integer> sortedSelectedValues = new ArrayList<Integer>(selectedValues.size());
			//Iterate over the original values to get the original sorting
			for (Integer value : rolledValues) {
				if (selectedValues.contains(value)) {
					//if value is in selected values, add it to the sorted values
					sortedSelectedValues.add(value);
					//to not match more than there are, remove Value from selected values
					selectedValues.remove(value);
				}
			}
			selectedValues = sortedSelectedValues;
		}
		
		//Build CollectionReturn
		CollectionReturn collection = new CollectionReturn();
		{
			//Build value
			for (Integer value : selectedValues) {
				collection.add(value);
			}
			
			//Build message
			StringBuilder sb = new StringBuilder();
			sb.append(_messageStarttag);
			for (int i = 0; i < selectedValues.size(); ++i) {
				if (i != 0) {
					sb.append(" + ");
				}	
				sb.append(selectedValues.get(i));
			}
			sb.append(_selectionSeperatortag);
			for (int i = 0; i < rolledValues.size(); ++i) {
				if (i != 0) {
					sb.append(_rollSeperatortag);
				}	
				sb.append(rolledValues.get(i));
			}
			sb.append(_messageEndtag);
			collection.setMessage(sb.toString());
		}
		
		return collection;
	}
	
	/**
	 * 
	 * @param explodingMinimum
	 * @return {@link CollectionReturn} with appropriate message and value
	 * @throws IllegalArgumentException if {@code explodingMinimum} is greater than the
	 * highest possible rolled value, or at least as small as the lowest possible rolled value
	 */
	protected CollectionReturn rollExploding(int explodingMinimum) {
		//check if sides is a ConstantExpression
		if (!sides.isConstant()) {
			throw new IllegalArgumentException("Can't explode die with random sides");
		}
		int sides = ((ConstantExpression) this.sides).getValue();
		
		//get what is needed
		ArrayList<ArrayList<Integer>> rolledValuesMatrix = new ArrayList<ArrayList<Integer>>();
		int times = this.times.roll().getValueAsFloorInt();
		rolledValuesMatrix.add(generatedRolledValuesList(times));
		
		//check exploding minimum is within expected bounds
		int lowestDieValue = startFromZero ? 0 : 1;
		if (explodingMinimum > sides-1 + lowestDieValue) { //Highest possible value of Random.nextInt(6) is 5
			throw new IllegalArgumentException("Dice can't explode with the set values. "
					+ "Highest possible value: "+sides+lowestDieValue+", "
					+ "Minimum value to explode: "+explodingMinimum);
		} else if (explodingMinimum == lowestDieValue) {
			throw new IllegalArgumentException("Dice will always explode with the set values. "
					+ "Minimum value to explode: "+explodingMinimum);
		} else if (explodingMinimum < lowestDieValue) {
			throw new IllegalArgumentException("Dice can't explode with the set values. "
					+ "Minimum value to explode: "+explodingMinimum);
		}	
		
		//do exploding mechanism
		{
			boolean hasExploding;
			int i = 0;
			do {
				//prepare
				ArrayList<Integer> newValues = new ArrayList<Integer>();
				hasExploding = false;
				
				//do exploding
				for (Integer value : rolledValuesMatrix.get(i)) {
					if (value >= explodingMinimum) {
						hasExploding = true;
						newValues.add(UtilFunction.roll(sides, startFromZero));
					}
				}
				if (hasExploding) {
					rolledValuesMatrix.add(newValues);
				}
				
				++i;
			} while (hasExploding);
		}
		
		//Build RollReturn
		CollectionReturn collection = new CollectionReturn();
		{
			StringBuilder sb = new StringBuilder();
			
			//Build value
			for (ArrayList<Integer> list : rolledValuesMatrix) {
				for (Integer value : list) {
					collection.add(value);
				}
			}
			
			//Build message
			sb.append(_messageStarttag);
			int closingBracketsNeeded = 0;
			for (int i = 0; i < rolledValuesMatrix.size(); ++i) {			
				if (i==0) { // the original values
					for (int j = 0; j < rolledValuesMatrix.get(i).size(); ++j) {
						if (j != 0){
							sb.append(" + ");
						}
						sb.append(rolledValuesMatrix.get(i).get(j));
					}
				} else { // all exploded values
					sb.append(_explodingStarttag); // open tag
					closingBracketsNeeded++; // recall brackets needed
					for (Integer value : rolledValuesMatrix.get(i)) {
						sb.append(" + ");
						sb.append(value);
					}
					
				}
			}
			for (int i = 0; i < closingBracketsNeeded; ++i) {
				sb.append(_explodingEndtag); // add tags as needed
			}
			sb.append(_messageEndtag);
			collection.setMessage(sb.toString());
		}
		
		return collection;
	}
	
	/**
	 * Rolls a {@code sides}-sided die {@code times} times and saves the individual values
	 * in an {@code ArrayList}. 
	 * @return An {@code ArrayList} of type {@code Integer} with the rolled values
	 */
	private ArrayList<Integer> generatedRolledValuesList(int times) {
		ArrayList<Integer> collection = new ArrayList<Integer>(times);
		for (int i = 0; i < times; ++i) {
			collection.add(UtilFunction.roll(sides.roll().getValueAsFloorInt(), startFromZero));
		}
		return collection;
	}
	
	/**
	 * @return the modification
	 */
	public Modification getModification() {
		return modification;
	}

	/**
	 * @param modification the modification to set
	 */
	public void setModification(Modification modification) {
		this.modification = modification;
	}

	/**
	 * @return the modifier, a value used by some modifications
	 */
	public DiceExpression getModifier() {
		return modifier;
	}

	/**
	 * @param modifier the modifier, a value used by some modifications, to set
	 */
	public void setModifier(DiceExpression modifier) {
		this.modifier = modifier;
	}

	/**
	 * Modifications supported by {@code ModifiedDiceRollExpression}.
	 * @author D. Meersteiner
	 * @see ModifiedDiceRollExpression
	 */
	public enum Modification {
		/**
		 * Selects the 1 highest value rolled.
		 */
		MAX("maximum of %2$s"),
		/**
		 * Selects the 1 lowest value rolled.
		 */
		MIN("minimum of %2$s"),
		/**
		 * Selects the x highest value rolled. Uses the modifier as x.
		 */
		LARGEST("largest %1$s of %2$s"),
		/**
		 * Selects the x lowest value rolled. Uses the modifier as x.
		 */
		LEAST("least %1$s of %2$s"),
		/**
		 * Rolls the die again, if its value is at least x. Uses the modifier as x.
		 */
		EXPL("%2$s exploding on %1$s");
		
		private String message;
		
		private Modification(String message) {
			this.message = message;
		}
		
		/**
		 * A string formatted in a way that
		 * {@code String.format(getMessage(), modifier, expression)}, with
		 * modifier being the modifier of the Modification of type {@code int}
		 * and expression being the {@code DiceRollExpression.toString()} of
		 * type {@code String}, that must be readable by the user, as it is used by
		 * {@code ModifiedDiceRollExpression.toString()}.
		 *  
		 * @return a String
		 */
		public String getMessage() {
			return message;
		}
	}
}
