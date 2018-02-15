package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightRightScale extends CommandGroup {

    public StartRightRightScale() {
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 0.0));
    }
}
