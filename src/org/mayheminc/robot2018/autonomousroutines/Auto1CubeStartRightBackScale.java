package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Auto1CubeStartRightBackScale extends SmartAutoChooserBase {

    @Override
	protected Command GetAutoroutine() {
    	boolean ourScale = Robot.gameData.GetScale();
    	
    	// scale on right...
    	if( ourScale )
    	{
    		return new StartRightBackScaleRight();
    	}
    	// scale on left...
    	else
    	{
    		return new StartRightBackScaleLeft();
    	}
	}
}
