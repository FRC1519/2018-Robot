package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Week5StartRight extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	if (ourSwitchOnRight && scaleOnRight) {  // RRR
    		return new StartRightBackRRR();
    	} else if (ourSwitchOnRight && !scaleOnRight) {  // RLR
    		return new StartRightBackRLR();
    	} else if (!ourSwitchOnRight && scaleOnRight) {  // LRL
    		return new StartRightBackLRL();
    	} else {  // LLL
    		return new StartRightBackLLL();
    	}
	}
}
