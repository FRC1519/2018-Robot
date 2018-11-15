package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

public class StartLeftMultiScale extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	// for the multi-scale autos, we don't even care where the switch is
    	if (scaleOnRight) {
    		// scale is opposite (O)
    		return new MultiScaleO(Autonomous.StartOn.LEFT);
    	} else {
    		// scale is near (N)
    		return new MultiScaleN(Autonomous.StartOn.LEFT);
    	}
    	
	}
}
