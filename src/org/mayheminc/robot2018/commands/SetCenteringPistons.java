package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;

/**
 *
 */
public class SetCenteringPistons extends Command {
	boolean position;
    public SetCenteringPistons(boolean arg_position) {
//    	requires(Robot.claw);
    	position = arg_position;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.claw.setCenteringPistons(position);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//open
    	Robot.claw.setCenteringPistons(false);
    }
}