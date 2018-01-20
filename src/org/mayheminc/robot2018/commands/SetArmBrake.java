package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetArmBrake extends Command {
	private boolean m_position;
    private long m_endTime;
    private static final long DEFAULT_DURATION_MS = 200;
	private long m_timeToWaitToEngageMs = DEFAULT_DURATION_MS;
	
    public SetArmBrake(boolean arg_position, boolean arg_requireArm ) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	if (arg_requireArm == Arm.REQUIRE_ARM_SUBSYSTEM) {
    		requires(Robot.arm);
    	}
    	m_position = arg_position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.arm.getArmBrake() == m_position) {
    		// if brake already set as desired, no need to wait!
    		m_timeToWaitToEngageMs = 0;
    	} else {
    		m_timeToWaitToEngageMs = DEFAULT_DURATION_MS;
    	}
    	DriverStation.reportError("SetArmBrake: initialize()", false);
        m_endTime = System.currentTimeMillis() + m_timeToWaitToEngageMs;
        Robot.arm.setArmBrake(m_position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= m_endTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
