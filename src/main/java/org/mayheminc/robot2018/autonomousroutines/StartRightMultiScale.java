package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightMultiScale extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	//boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	// for the multi-scale autos, we don't even care where the switch is
    	if (scaleOnRight) {
    		// scale is near (N)
    		return new MultiScaleN(Autonomous.StartOn.RIGHT);
    	} else {
    		// scale is opposite (O)
    		return new MultiScaleO(Autonomous.StartOn.RIGHT);
    	}
    	
	}
}
