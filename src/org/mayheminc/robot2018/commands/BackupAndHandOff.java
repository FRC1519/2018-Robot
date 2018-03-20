package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveStraight.DistanceUnits;
/**
 *
 */
public class BackupAndHandOff extends CommandGroup {

    public BackupAndHandOff() {
    	addSequential(new DriveStraight(-0.5, DistanceUnits.INCHES, 2.0, 1.0));
    	addSequential(new HandoffCubeToElevator());
    }
}
