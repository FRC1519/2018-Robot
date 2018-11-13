package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

//drives forward until the gyro detects a tilt

public class DriveUntilTilt extends Command {
	
	public static final double MAX_TILT = 2.0;

    public DriveUntilTilt() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	Robot.drive.positiveDriveStraight(0.25);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.drive.speedRacerDrive(0.25,  0,  false);
    	  	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.drive.getTilt() >= MAX_TILT);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.stop();
    }
}
