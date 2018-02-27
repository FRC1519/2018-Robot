package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This is the base class of all Auto 2 Cube routines.
 * This gets an Auto routine from the subclass and runs it until it completes.
 */
public abstract class SmartAutoChooserBase extends Command {
    boolean hasStarted = false;
    Command autoRoutine;

    public SmartAutoChooserBase() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    protected abstract Command GetAutoroutine();
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	hasStarted = false;
    	autoRoutine = GetAutoroutine();
    	autoRoutine.start();
    	DriverStation.reportWarning("Starting command: " + autoRoutine.getName(), false);
}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(autoRoutine.isRunning()){
    		hasStarted = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (hasStarted && !autoRoutine.isRunning());
   }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Finished auto routine.");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("interrupted auto routine.");
    }
}
