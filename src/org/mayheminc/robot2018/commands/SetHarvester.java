package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This Command will set the rollers on forward full speed if 1 is passed in, will stop if 0 is passed in, 
 * and full speed reverse if -1 is passed in through the constructor.
 * 
 * If interrupted, this command will stop the rollers.
 * @author user
 *
 */
public class SetHarvester extends Command {
	double m_speed;

    public SetHarvester(double speed) {
    	m_speed = speed;
//    	requires(Robot.claw);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.claw.setHarvester(m_speed);
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
    	Robot.claw.setHarvester(0);    	
    }
}
