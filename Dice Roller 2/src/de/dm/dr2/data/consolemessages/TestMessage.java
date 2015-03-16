package de.dm.dr2.data.consolemessages;

import de.dm.dr2.data.diceexpressions.DiceExpression;
import de.dm.dr2.data.util.Constants;
import de.dm.dr2.data.util.RollReturn;
import de.dm.dr2.data.util.UtilFunction;

public class TestMessage extends ConsoleMessage {

	private String value;
	private String message;
	
	public TestMessage(DiceExpression first, DiceExpression second) {
		RollReturn firstReturn = first.roll();
		RollReturn secondReturn = second.roll();
		double trueValue = firstReturn.getValue()-secondReturn.getValue();
		if (trueValue >= 0) {
			value = UtilFunction.doubleToStringWithMinimumPrecision(trueValue)+" under"; 
		} else {
			value = UtilFunction.doubleToStringWithMinimumPrecision(trueValue)+" over";
		}
		
		message = "Testing "+first.toString()+" against "+second.toString()+":"
				+Constants.NEW_LINE
				+firstReturn.getMessage()
				+Constants.NEW_LINE
				+" against "
				+Constants.NEW_LINE
				+secondReturn.getMessage()
				+Constants.NEW_LINE
				+" = "
				+value
				+Constants.NEW_LINE+Constants.NEW_LINE;
	}
	
	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public String getValue() {
		return value;
	}

}
