package org.mayheminc.robot2018.commands;
import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;

/**
 *
 */
public class RotateDegrees extends Command {
	final static double DEFAULT_TIME_LIMIT_SEC = 2.0;
	final static double FINAL_HEADING_TOLERANCE = 2.0;
	double timeLimit = 0;
	double degrees = 0;
	Timer timer = new Timer();
	
	
    public RotateDegrees(double arg) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this(arg, DEFAULT_TIME_LIMIT_SEC);
    }
    
    public RotateDegrees(double arg, double limitForTime){
//    	requires(Robot.drive);
    	degrees = arg;
    	timeLimit = limitForTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drive.rotate(degrees);
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
    	System.out.println("Completed RotateDegrees Command");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
