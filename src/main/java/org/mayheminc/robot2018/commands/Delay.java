
package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Team1519
 */
public class Delay extends Command {
    private long m_delay;
    private long m_endTime;
    
    public Delay(long delay) {
        super("Delay");
        m_delay = delay;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_endTime = System.currentTimeMillis() + m_delay;
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
