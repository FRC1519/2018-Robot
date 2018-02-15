package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class Auto1CubeFromRight extends Auto2CubeBase {

	@Override
	protected Command GetAutoroutine() {
    	boolean ourswitch = Robot.gameData.GetOurSwitch();
    	
    	// switch on right, scale on right...
    	if( ourswitch )
    	{
    		return new StartRightRightSwitch();
    	}
    	// switch on left, scale on right...
    	else
    	{
    		// if this is a qual match, get the switch first
    		if (DriverStation.getInstance().getMatchType() == DriverStation.MatchType.Qualification)
    		{
    			return new StartRightLeftSwitch();
    		}
    		else // this is elim match, get the scale first. 
    		{
    			return new StartRightRightScale();
    		}
    	}
	}

}
