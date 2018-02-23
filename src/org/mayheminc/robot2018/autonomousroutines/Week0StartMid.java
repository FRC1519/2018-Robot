package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Week0StartMid extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	boolean ourswitch = Robot.gameData.GetOurSwitch();
    	
    	// switch on left...
    	if( !ourswitch )
    	{
    		return new StartMidLeftSwitch();
    	}
    	// switch on right...
    	else
    	{
  			return new StartMidRightSwitch();
    	}
	}
}
