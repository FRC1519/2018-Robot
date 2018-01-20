package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.autonomousroutines.LineUpForShotCommandGroup;
import org.mayheminc.robot2018.Robot;

/**
 *THIS COMMAND WILL DRIVE THE ROBOT OUT A CERTAIN AMOUNT (BASED ON SLOT NUMBER) AND THEN
 *ROTATE TO FACE THE GOAL (ALSO BASED ON SLOT NUMBER)
 */
public class LineUpForShotAndShoot extends Command {
	private LineUpForShotCommandGroup driveOut;
	private int slot = 0;
	private boolean hasStarted = false;
	
	public LineUpForShotAndShoot() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
//		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		hasStarted = false;    // need to indicate not started until execute() sees it is running
		slot = Robot.autonomous.getSelectedSlot();
		
		String autoProgramName = Robot.autonomous.getSelectedProgram().getName();
		
		boolean robotFacingForward = true;
		if (autoProgramName.equals("CrossCDFandScoreHigh")){
			robotFacingForward = false;
		}
		driveOut = new LineUpForShotCommandGroup(slot, robotFacingForward);
		driveOut.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
    	if (driveOut.isRunning()) {
    		hasStarted = true;
    	}	
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return (hasStarted && !driveOut.isRunning());
	}

	// Called once after isFinished returns true
	protected void end() {
		DriverStation.reportError("  Completed LineUpForShot  ", false);
		driveOut.cancel();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		DriverStation.reportError("  Interrupted LineUpForShot  ", false);
		driveOut.cancel();
	}
}
