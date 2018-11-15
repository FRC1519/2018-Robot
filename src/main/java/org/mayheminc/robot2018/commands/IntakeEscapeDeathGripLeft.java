package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.*;

/**
 *
 */
public class IntakeEscapeDeathGripLeft extends CommandGroup {	
    public IntakeEscapeDeathGripLeft() {
    	addSequential(new IntakeReverseLeft(0.2));
    	addSequential(new IntakeInForTime(0.4));
    }
}
