package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SelectAutonomousDefense extends Command {

	private int m_Offset;
	
    public SelectAutonomousDefense(int Offset) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.autonomous);
        setRunWhenDisabled(true);
        m_Offset = Offset;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	Robot.autonomous.adjustDefenseNumber(m_Offset);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
