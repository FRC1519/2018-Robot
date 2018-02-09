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
		OurSwitch = (gameData.charAt(0) == 'R');
		Scale = (gameData.charAt(1) == 'R');
		OpposingSwitch = (gameData.charAt(2) == 'R');
	}

	public boolean GetOurSwitch() 
	{
		Read();
		return OurSwitch;
	}
	public boolean GetScale()
	{
		Read();
		return Scale;
	}
	public boolean GetOpposingSwitch()
	{
		Read();
		return OpposingSwitch;
	}

	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append(gameData);
		str.append(" ");
		str.append( (GetOurSwitch()?"Right":"Left"));
		str.append(" ");
		str.append( (GetScale()?"Right":"Left"));
		str.append(" ");
		str.append( (GetOpposingSwitch()?"Right":"Left"));
		return str.toString();
	}

}

