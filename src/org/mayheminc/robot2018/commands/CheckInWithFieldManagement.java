package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
/**
 *
 */
public class CheckInWithFieldManagement extends Command {

    public CheckInWithFieldManagement() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	// when a joystick button is triggered, tell the FRC 
    	// Field Management system that we are ready.
    	
    	Robot.oi.CheckInWithFieldManagement();
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
    }
}
