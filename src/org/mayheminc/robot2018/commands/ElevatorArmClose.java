package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.ElevatorArms.JawPosition;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ElevatorArmClose extends InstantCommand {

    public ElevatorArmClose() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.elevatorArms.setJaw(JawPosition.CLOSE);
    }

}
