package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;

public class BackOffOuterWorks extends Command {
	public int startPosition;
	
	public static final int DESIRED_DISPLACEMENT = 255;

    public BackOffOuterWorks() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
//    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startPosition = Robot.drive.getRightEncoder();
//    	Robot.drive.positiveDriveStraight(-0.25);
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {   
    	Robot.drive.speedRacerDrive(-0.25,  0,  false);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	int displacement = Robot.drive.getRightEncoder() - startPosition;
    	displacement = Math.abs(displacement);
        return (displacement >= DESIRED_DISPLACEMENT);
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
