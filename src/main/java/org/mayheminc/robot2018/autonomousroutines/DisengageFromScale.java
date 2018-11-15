package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SafePosition;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DisengageFromScale extends CommandGroup {

    public DisengageFromScale() {
    	
    	// drive forward a bit to disengage the scale
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 10.0, 180.0));
    	addSequential(new PrintAutonomousTimeRemaining("Disengaging from scale."));
    	addSequential(new SafePosition());

    }
}
