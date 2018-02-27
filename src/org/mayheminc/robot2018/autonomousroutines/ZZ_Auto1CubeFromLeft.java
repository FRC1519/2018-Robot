package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

public class ZZ_Auto1CubeFromLeft extends SmartAutoChooserBase {

	@Override
	protected Command GetAutoroutine() {
    	boolean ourswitch = Robot.gameData.GetNearSwitch();
    	
    	// switch on right, scale on right...
    	if( !ourswitch )
    	{
    		return new ZZ_StartLeftLeftSwitch();
    	}
    	// switch on left, scale on right...
    	else
    	{
    		return new ZZ_StartLeftRightSwitch();
    	}
    }
}
