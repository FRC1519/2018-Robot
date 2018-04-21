package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

public class StartLeftStayLeftUnlessRRRAlley extends SmartAutoChooserBase{

	protected Command GetAutoroutine() {
    	boolean ourSwitchOnRight = Robot.gameData.getNearSwitchOnRight();
    	boolean scaleOnRight = Robot.gameData.getScaleOnRight();
    	
    	if (ourSwitchOnRight && scaleOnRight) {  // RRR
    		return new HalfWayThroughAlley(Autonomous.StartOn.LEFT);
    	} else if (ourSwitchOnRight && !scaleOnRight) {  // RLR
    		return new MultiScaleN(Autonomous.StartOn.LEFT);
    	} else if (!ourSwitchOnRight && scaleOnRight) {  // LRL
    		return new MultiSwitchN(Autonomous.StartOn.LEFT);
    	} else {  // LLL
    		return new ScaleAndSwitchNNN(Autonomous.StartOn.LEFT);
    	}
	}
}
