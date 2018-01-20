package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetArmPositionIgnoringBrake extends Command {

	int m_desiredPosition = 0;
	
    public SetArmPositionIgnoringBrake(int desiredPosition, boolean arg_requireArm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	if (arg_requireArm == Arm.REQUIRE_ARM_SUBSYSTEM) {
    		requires(Robot.arm);
    	}
    	m_desiredPosition = desiredPosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.setPosition(m_desiredPosition);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.arm.isAtPosition(m_desiredPosition);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
