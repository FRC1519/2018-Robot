package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Week0StartLeft extends SmartAutoChooserBase {

   
	@Override
	protected Command GetAutoroutine() {
    	boolean ourswitch = Robot.gameData.GetOurSwitch();
    	
    	// switch on left...
    	if( !ourswitch )
    	{
    		return new StartLeftLeftSwitch();
    	}
    	// switch on right...
    	else
    	{
  			return new CrossBaseline();
    	}
	}
}
