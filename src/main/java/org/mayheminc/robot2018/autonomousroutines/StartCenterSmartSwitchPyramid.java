package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StartCenterSmartSwitchPyramid extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	if (ourSwitchOnRight && scaleOnRight) {  // RRR
    		return new StartCenterRightSwitchPyramid();
    	} else if (ourSwitchOnRight && !scaleOnRight) {  // RLR
    		return new StartCenterRightSwitchPyramid();
    	} else if (!ourSwitchOnRight && scaleOnRight) {  // LRL
    		return new StartCenterLeftSwitchPyramid();
    	} else {  // LLL
    		return new StartCenterLeftSwitchPyramid();
    	}
    }
}
