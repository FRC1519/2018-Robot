package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Auto1CubeStartLeftBackScale extends SmartAutoChooserBase {

    @Override
	protected Command GetAutoroutine() {
    	boolean ourScale = Robot.gameData.GetScale();
    	
    	// scale on right...
    	if( ourScale )
    	{
    		return new StartLeftBackScaleRight();
    	}
    	// scale on left...
    	else
    	{
    		return new StartLeftBackScaleLeft();
    	}
	}
}
