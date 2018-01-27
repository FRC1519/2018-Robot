package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class HarvesterRollersReverse extends Command {

    public HarvesterRollersReverse() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.claw); 
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.claw.setHarvester(-1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.claw.slowHarvesterRollers();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	Robot.claw.slowHarvesterRollers();
    }
}
