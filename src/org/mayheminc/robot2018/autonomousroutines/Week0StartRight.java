package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Week0StartRight extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	boolean ourswitch = Robot.gameData.GetNearSwitch();
    	
    	// switch on left...
    	if( !ourswitch )
    	{
    		return new CrossBaseline();
    	}
    	// switch on right...
    	else
    	{
  			return new ZZ_StartRightRightSwitch();
    	}
	}
}
