
package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 * @author Team1519
 */
public class SelectAutonomousDelay extends Command {
    
    private int delta;

    public SelectAutonomousDelay(int delta) {
        requires(Robot.autonomous);
        setRunWhenDisabled(true);
        this.delta = delta;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        Robot.autonomous.adjustDelay(delta);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
