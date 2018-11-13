package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GameData extends Subsystem {

	boolean OurSwitch;
	boolean Scale;
	boolean OpposingSwitch;
	String gameData;

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
    void Read()
	{
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if( gameData.length() < 3 )
		{
			return;
		}
		try
		{
		OurSwitch = (gameData.charAt(0) == 'R');
		Scale = (gameData.charAt(1) == 'R');
		OpposingSwitch = (gameData.charAt(2) == 'R');
		}
		catch(Exception e)
		{}
	}

	public boolean getNearSwitchOnRight() 
	{
		Read();
		return OurSwitch;
	}
	public boolean getScaleOnRight()
	{
		Read();
		return Scale;
	}
//	public boolean GetFarSwitch()
//	{
//		Read();
//		return OpposingSwitch;
//	}

	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(gameData);
		str.append(" ");
		str.append( (getNearSwitchOnRight()?"Right":"Left"));
		str.append(" ");
		str.append( (getScaleOnRight()?"Right":"Left"));
//		str.append(" ");
//		str.append( (GetFarSwitch()?"Right":"Left"));
		return str.toString();
	}

}

