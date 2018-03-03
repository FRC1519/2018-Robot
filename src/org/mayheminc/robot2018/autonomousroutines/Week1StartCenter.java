package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Week1StartCenter extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	if (ourSwitchOnRight && scaleOnRight) {  // RRR
    		return new StartCenterRightSwitch();
    	} else if (ourSwitchOnRight && !scaleOnRight) {  // RLR
    		return new StartCenterRightSwitch();
    	} else if (!ourSwitchOnRight && scaleOnRight) {  // LRL
    		return new StartCenterLeftSwitch();
    	} else {  // LLL
    		return new StartCenterLeftSwitch();
    	}
	}
}
