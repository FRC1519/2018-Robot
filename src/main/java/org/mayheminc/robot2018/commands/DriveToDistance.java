package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.mayheminc.robot2018.Robot;

/**
 *
 */
public class DriveToDistance extends Command {
	
	private double stoppingDistance;

    public DriveToDistance(double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	stoppingDistance = distance;
    	
//    	requires(Robot.drive);
    	
    	SmartDashboard.putString("DriveToDistance", "const");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.driveStraightForward(0.3);
        SmartDashboard.putString("DriveToDistance", "init");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean b =  (stoppingDistance > Robot.drive.getUltrasonicDistance());
    	
        SmartDashboard.putString("DriveToDistance", "isFinished");
    	return b;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drive.driveStraightForward(0.0);
        SmartDashboard.putString("DriveToDistance", "end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drive.driveStraightForward(0.0);
        //SmartDashboard.putString("DriveToDistance", "interrupted");
    }
}
