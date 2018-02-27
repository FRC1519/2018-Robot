package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto1CubeStartRightBackSwitch extends SmartAutoChooserBase {

    @Override
	protected Command GetAutoroutine() {
    	boolean ourSwitch = Robot.gameData.GetNearSwitch();
    	
    	// switch on right...
    	if( ourSwitch )
    	{
    		return new StartRightBackSwitchRight();
    	}
    	// switch on left...
    	else
    	{
    		return new StartRightBackSwitchLeft();
    	}
	}
}
