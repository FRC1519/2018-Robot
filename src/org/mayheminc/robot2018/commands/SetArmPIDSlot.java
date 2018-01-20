package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.Command;

/**
 *TEMPPORARY COMMAND, TO BE USED ONLY UNTIL THE ARM CAN AUTOMATICALLY CHANGE PID SLOTS
 */
public class SetArmPIDSlot extends Command {
	private int slot;
    public SetArmPIDSlot(int arg_slot, boolean arg_requireArm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	if (arg_requireArm == Arm.REQUIRE_ARM_SUBSYSTEM) {
    		requires(Robot.arm);
    	}
    	slot = arg_slot;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.setPIDSlot(slot);
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
