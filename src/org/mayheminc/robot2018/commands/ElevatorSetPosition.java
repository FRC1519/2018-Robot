package org.mayheminc.robot2018.commands;

import javax.lang.model.element.ElementKind;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Elevator.ElevatorPositions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorSetPosition extends Command {

	ElevatorPositions m_position;
	
    public ElevatorSetPosition(ElevatorPositions position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.elevator.setElevatorPosition(m_position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.elevator.IsElevatorAtPosition();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
