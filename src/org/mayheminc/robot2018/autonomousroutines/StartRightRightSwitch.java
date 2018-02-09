package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive straight, curve into the switch, drop cube, backup to 0 heading.
 */
public class StartRightRightSwitch extends CommandGroup {

    public StartRightRightSwitch() {
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 100.0, 0.0));
    	addSequential(new DriveStraightOnHeading(0.75, DistanceUnits.INCHES, 45.0, -90.0));
    	
    	addSequential(new PrintAutonomousTimeRemaining("Dropping Cube"));
    	
    	addSequential(new DriveStraightOnHeading(-0.75, DistanceUnits.INCHES, 45.0, 0.0));

    	addSequential(new PrintAutonomousTimeRemaining("Start Right Right Switch Done"));
    }
}
