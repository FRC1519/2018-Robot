package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Wait extends Command {
	Timer m_Timer = new Timer();
	double m_endTime;
    public Wait(double endTime) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_endTime = endTime;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_Timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_Timer.hasPeriodPassed(m_endTime);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
