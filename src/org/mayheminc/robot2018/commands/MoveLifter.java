package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This Command will set the rollers on forward full speed if 1 is passed in, will stop if 0 is passed in, 
 * and full speed reverse if -1 is passed in through the constructor.
 * 
 * If interrupted, this command will stop the rollers.
 * @author user
 *
 */
public class MoveLifter extends Command {
	double m_power;
    private long m_duration;
    private long m_endTime;
    private static final long DEFAULT_DURATION_MS = 1400;
    
    public MoveLifter(double power) {
    	m_power = power;
    	m_duration = DEFAULT_DURATION_MS;
    }

    public MoveLifter(double power, long duration) {
    	m_power = power;
    	m_duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_endTime = System.currentTimeMillis() + m_duration;
    	Robot.lifter.setLifterPower(m_power);
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
    	Robot.lifter.stopLifter();   
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.lifter.stopLifter();    	
    }
}
