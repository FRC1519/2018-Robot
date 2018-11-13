package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

public class StartRightStayRightUnlessLLLSwitch extends SmartAutoChooserBase{

	protected Command GetAutoroutine() {
    	boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	if (ourSwitchOnRight && scaleOnRight) {  // RRR
    		return new ScaleAndSwitchNNN(Autonomous.StartOn.RIGHT);
    	} else if (ourSwitchOnRight && !scaleOnRight) {  // RLR
    		return new MultiSwitchN(Autonomous.StartOn.RIGHT);
    	} else if (!ourSwitchOnRight && scaleOnRight) {  // LRL
    		return new MultiScaleN(Autonomous.StartOn.RIGHT);
    	} else {  // LLL
    		return new ScoreOnOppositeSwitchEnd(Autonomous.StartOn.RIGHT);
    	}
	}
}
