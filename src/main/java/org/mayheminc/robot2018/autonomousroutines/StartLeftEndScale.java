package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartLeftEndScale extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	//boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	// for the multi-scale autos, we don't even care where the switch is
    	if (scaleOnRight) {
    		// scale is opposite (O)
    		return new ScoreOnOppositeScaleEnd(Autonomous.StartOn.LEFT);
    	} else {
    		// scale is near (N)
    		return new ScoreOnNearScaleEnd(Autonomous.StartOn.LEFT);
    	}
    	
	}
}
