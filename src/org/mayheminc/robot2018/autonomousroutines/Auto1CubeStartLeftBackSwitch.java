package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Auto1CubeStartLeftBackSwitch extends SmartAutoChooserBase {

    @Override
	protected Command GetAutoroutine() {
    	boolean ourSwitch = Robot.gameData.GetNearSwitch();
    	
    	// switch on right...
    	if( ourSwitch )
    	{
    		return new StartLeftBackSwitchRight();
    	}
    	// scale on left...
    	else
    	{
    		return new StartLeftBackSwitchLeft();
    	}
	}
}
