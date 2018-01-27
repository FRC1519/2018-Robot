package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */

public class SetArmPosition extends CommandGroup {
    public  SetArmPosition(int desiredPosition, boolean arg_requireArm) {
    	addSequential(new SetArmBrake(Arm.ARM_BRAKE_DISENGAGED, arg_requireArm));
    	addSequential(new SetArmPositionIgnoringBrake(desiredPosition, arg_requireArm));
    	if (desiredPosition == Arm.UPRIGHT_POSITION_COUNT) {
    		addSequential(new SetArmAutoRelaxedMode(arg_requireArm));
    		addSequential(new SetArmBrake(Arm.ARM_BRAKE_ENGAGED, arg_requireArm));
    	} else if (desiredPosition == Arm.FIRE_POSITION_COUNT) {
    		addSequential(new SetArmAutoRelaxedMode(arg_requireArm));
    	}
    }
}