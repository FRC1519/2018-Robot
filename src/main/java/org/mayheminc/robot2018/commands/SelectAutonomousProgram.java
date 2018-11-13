
package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Team1519
 */
public class SelectAutonomousProgram extends Command {
    
    private int m_delta;

    public SelectAutonomousProgram(int delta) {
        requires(Robot.autonomous);
        setRunWhenDisabled(true);
        m_delta = delta;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
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
    	Robot.autonomous.adjustProgramNumber(m_delta);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
