package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ElevatorWaitUntilAtPosition extends Command {
	int m_position;
    public ElevatorWaitUntilAtPosition(int position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	
    	double diff = Math.abs(Robot.elevator.getCurrentPosition() - m_position);
    	
        return diff < 750;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
