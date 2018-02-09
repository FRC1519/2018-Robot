package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossBaseline extends CommandGroup {

    public CrossBaseline() {
    	// drive forward for ~100 inches, nice and slow.
    	addSequential(new DriveStraightOnHeading(0.3, DistanceUnits.INCHES, 100.0, 0.0));
    }
}
