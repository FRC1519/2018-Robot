package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZZ_StartRightRightScale extends CommandGroup {

    public ZZ_StartRightRightScale() {
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 0.0));
    }
}
