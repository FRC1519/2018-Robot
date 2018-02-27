package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Auto1CubeStartMidBackScale extends SmartAutoChooserBase {

    @Override
	protected Command GetAutoroutine() {
    	boolean scale = Robot.gameData.GetScale();
    	
    	// switch on right...
    	if( scale )
    	{
    		return new StartMidBackScaleRight();
    	}
    	// scale on left...
    	else
    	{
    		return new StartMidBackScaleLeft();
    	}
	}

}
