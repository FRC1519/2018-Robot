package org.mayheminc.robot2018.commands;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;

/**
 *
 */
public class DriveRotate extends Command {
	
	final static double DEFAULT_TIME_LIMIT_SEC = 2.5;
	final static double FINAL_HEADING_TOLERANCE = 8.0;
	double timeLimit = 0;
	double degrees = 0;
	Timer timer = new Timer();
	
	public enum DesiredHeadingForm { ABSOLUTE, RELATIVE };
	private boolean isAbsolute;
	
    public DriveRotate(double arg, DesiredHeadingForm form) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(arg, form, DEFAULT_TIME_LIMIT_SEC);
    }
    
    public DriveRotate(double arg, DesiredHeadingForm form, double limitForTime){
//    	requires(Robot.drive);
    	
    	if (form == DesiredHeadingForm.ABSOLUTE) {
    		isAbsolute = true;
    	} else {
    		isAbsolute = false;
    	}
    	degrees = arg;
    	timeLimit = limitForTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (isAbsolute) {
    		Robot.drive.rotateToHeading(degrees);
    	} else {
    		Robot.drive.rotate(degrees);
    	}
    	
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//throttle must be nonzero to engage the maintainHeading() code
    	Robot.drive.speedRacerDrive(-0.05, 0, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    public boolean isFinished() {
        double actual = Robot.drive.getHeading();
        double desired = Robot.drive.getDesiredHeading();
        double diff = actual - desired;
        diff = Math.abs(diff);
        
        return ((diff < FINAL_HEADING_TOLERANCE) || (timer.get() > timeLimit));
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("Completed RotateDegrees Command", false);
    	// tell the robot to stop turning
    	Robot.drive.speedRacerDrive(0, 0, false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Interrupted RotateDegrees Command", false);
    	// tell the robot to stop turning
    	Robot.drive.speedRacerDrive(0, 0, false);
    }
}
