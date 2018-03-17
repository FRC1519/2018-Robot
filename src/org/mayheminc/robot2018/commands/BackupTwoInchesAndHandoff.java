package org.mayheminc.robot2018.commands;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.mayheminc.robot2018.subsystems.*;

/**
 *
 */
public class BackupTwoInchesAndHandoff extends CommandGroup {

    public BackupTwoInchesAndHandoff() {
    	// Backup for two inches
    	addSequential(new DriveStraight(-0.5, DriveStraight.DistanceUnits.INCHES, 2.0));
    	
    	// perform a handoff
    	addSequential( new HandoffCubeToElevator());
    }
}
