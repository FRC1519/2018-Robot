package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import edu.wpi.first.wpilibj.DriverStation;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunSelectedDefense extends Command {
	boolean hasStarted = false;
	Command defense;
    public RunSelectedDefense() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	hasStarted = false;
    	defense = Robot.autonomous.getSelectedDefense();
    	defense.start();
    	DriverStation.reportError("Starting appropriate defense command: " + defense.getName(), false);
   
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(defense.isRunning()){
    		hasStarted = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (hasStarted && !defense.isRunning());
    }

    // Called once after isFinished returns true
    protected void end() {
    	DriverStation.reportError("Finished crossing Selected Defense: " + defense.getName(), false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	DriverStation.reportError("Selected Defense Routine interrupted", false);
    }
}
