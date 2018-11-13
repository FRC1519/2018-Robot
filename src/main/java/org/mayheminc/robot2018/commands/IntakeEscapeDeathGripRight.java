package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class IntakeEscapeDeathGripRight extends CommandGroup {

    public IntakeEscapeDeathGripRight() {
    	addSequential(new IntakeReverseRight(0.2));
    	addSequential(new IntakeInForTime(0.4));
    }
}
