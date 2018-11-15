package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.*;
//import org.mayheminc.robot2018.subsystems.Intake;

public class AllRollersOut extends Command {

    public AllRollersOut() {
        super();
    }

    protected void initialize() {
    	Robot.intake.spitOutCube();
    	
    	// we want a very forceful spit for into the switch, and a gentler spit for into the scale
    	// Accordingly, we want the spit strength to vary according to elevator height.

    	// if below the SWITCH_MAX_HEIGHT spit hard for the switch
    	if (Robot.elevator.getCurrentPosition() < Elevator.SWITCH_MAX_HEIGHT) {
    		Robot.elevatorArms.setMotor(-1.0);  // spit at full power for the switch.
    	} else {
    		Robot.elevatorArms.setMotor(-0.7);  // spit at 70% power for the scale.
		}
    }
    
    protected boolean isFinished() {return false;}
    protected void end()
    {
    	Robot.intake.stop();
    	Robot.elevatorArms.setMotor(0.0);
    }
    protected void interrupt()
    {
    	Robot.intake.stop();
    	Robot.elevatorArms.setMotor(0.0);
    }
}
