package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartMidLeftSwitch extends CommandGroup {

    public StartMidLeftSwitch() {
//    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 0.0));
    	// go almost due west
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 60.0, 280.0));
    	// drive down field
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 53.0, 0.0));
    	// drive into the switch
//    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 45.0, 90.0));
//    	// back up and face down field
//    	addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 45.0, 0.0));

    }
}
