package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class IntakeOutForTime extends Command {

	Timer m_timer;
	double m_timeout;
	
	/**
	 * Set the intake to out for a number of seconds.
	 */
    public IntakeOutForTime(double t) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	m_timeout = t;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_timer = new Timer();
    	Robot.intake.spitOutCube();
    	m_timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return m_timer.hasPeriodPassed(m_timeout);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.stop();
    }
    

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.stop();
    }
}
