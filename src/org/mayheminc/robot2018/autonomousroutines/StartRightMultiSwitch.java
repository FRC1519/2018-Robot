package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

public class StartRightMultiSwitch extends SmartAutoChooserBase {

	protected Command GetAutoroutine() {
    	//boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean switchOnRight = Robot.gameData.getNearSwitchOnRight();
    	
    	// for the multi-scale autos, we don't even care where the switch is
    	if (switchOnRight) {
    		// scale is near (N)
    		return new MultiSwitchN(Autonomous.StartOn.RIGHT);
    	} else {
    		// scale is opposite (O)
    		return new MultiSwitchO(Autonomous.StartOn.RIGHT);
    	}
    	
	}

}
