package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Drive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AlignToTargetAndShoot extends Command {

    private long m_earliestLaunchTime;
    private static final long MIN_ALIGN_TIME_MS = 1250;
    private double m_targetSpeed;
    private double m_startPos;
    
    public AlignToTargetAndShoot(double arg_targetSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_targetSpeed = arg_targetSpeed;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	m_earliestLaunchTime = System.currentTimeMillis() + MIN_ALIGN_TIME_MS;
    	// reset the number of loops on target
    	Robot.drive.resetLoopsOnTarget();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// align the robot drive base to the target
    	Robot.drive.autoTarget(m_targetSpeed);
    	
		// grant the launcher permission to launch when alignment is okay
    	if (System.currentTimeMillis() >= m_earliestLaunchTime) {
    		Robot.launcher.autonomousPermissionToLaunch(true);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.launcher.shotJustFired();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.launcher.autonomousPermissionToLaunch(false);
    	Robot.drive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.launcher.autonomousPermissionToLaunch(false);
    	Robot.drive.stop();
    }
}
