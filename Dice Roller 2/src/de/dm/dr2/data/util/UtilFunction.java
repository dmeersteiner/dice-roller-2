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

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

/**
 * This class holds various utility methods
 * used in DiceRoller2
 * @author dmeerste
 *
 */
public class UtilFunction {
	
	private static java.util.Random r = new java.util.Random();

	/**
	 * 
	 * @param d
	 * @return
	 */
	public static String doubleToStringWithMinimumPrecision(double d) {
		return d == (int) d ? String.format("%d", (int) d) : String.format("%s", d);
	}
	
	/**
	 * Used by Swing GUI
	 * @return the Icons of DR2
	 */
	public static ArrayList<Image> getDiceRollerIconImages() {
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(Toolkit.getDefaultToolkit().getImage(UtilFunction.class.getResource("/de/dm/dr2/images/logo_20.png")));
		images.add(Toolkit.getDefaultToolkit().getImage(UtilFunction.class.getResource("/de/dm/dr2/images/logo_40.png")));
		images.add(Toolkit.getDefaultToolkit().getImage(UtilFunction.class.getResource("/de/dm/dr2/images/logo_512.png")));
		return images;
	}

	public static int roll(int sides) {
		return roll(sides, false);
	}
	
	public static int roll(int sides, boolean startFromZero) {
		return startFromZero ? (r.nextInt(sides)) : (r.nextInt(sides)+1);
	}
	
	public static java.util.Random getRandom() {
		return r;
	}
	
	/**
	 * Appends a {@code String} to the end of the document
	 * of the {@code JTextComponent}
	 * @param textComp
	 * @param s
	 */
	public static void appendToText(JTextComponent textComp, String s) {
		Document doc = textComp.getDocument();
		try {
			doc.insertString(doc.getLength(), s, null);
			textComp.scrollRectToVisible(textComp.modelToView(doc.getLength()));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> Vector<T> toVector(Collection<T> arrayList) {
		return new Vector<T>(arrayList);
	}

	/**
	 * 
	 * @param stringToTest
	 * @param keyword
	 * @return the {@code String} after {@code keyword}+" ", or null if {@code stringToTest} doesn't start with {@code keyword}
	 */
	public static String stringAfter(String stringToTest, String keyword) {
		if (stringToTest.toLowerCase().startsWith(keyword.toLowerCase()+" ")) {
			return stringToTest.substring(keyword.length()+1); // added space
		}
		return null;
	}

	/**
	 * 
	 * @param location
	 * @return true if the point is visible
	 */
	public static boolean isLocationInScreenBounds(Point location) { 
		GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		Rectangle rect = new Rectangle();

		for (int j = 0; j < screens.length; j++) {
			GraphicsDevice screen = screens[j];
			rect.setRect(screen.getDefaultConfiguration().getBounds());
			if (rect.contains(location.x, location.y)) {
				return true;
			}

		}
		return false;
	}

}
