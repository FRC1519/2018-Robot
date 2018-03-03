package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class ZZ_Auto1CubeFromRight extends SmartAutoChooserBase {

	@Override
	protected Command GetAutoroutine() {
    	boolean ourswitch = Robot.gameData.getNearSwitchOnRight();
    	
    	// switch on right, scale on right...
    	if( ourswitch )
    	{
    		return new ZZ_StartRightRightSwitch();
    	}
    	// switch on left, scale on right...
    	else
    	{
    		// if this is a qual match, get the switch first
    		if (DriverStation.getInstance().getMatchType() == DriverStation.MatchType.Qualification)
    		{
    			return new ZZ_StartRightLeftSwitch();
    		}
    		else // this is elim match, get the scale first. 
    		{
    			return new ZZ_StartRightRightScale();
    		}
    	}
	}

}
