package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartMidRightSwitch extends CommandGroup {

    public StartMidRightSwitch() {
    	// go almost due east
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 80.0));
    	// drive down field
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 0.0));
    	// drive into the switch
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 45.0, 270.0));
    	// back up and face down field
    	addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 45.0, 0.0));
    }
}
