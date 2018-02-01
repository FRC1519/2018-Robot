package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Message extends Command {
	String text;
    public Message(String arg_text) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	text = arg_text;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	DriverStation.reportError(text, false);
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
