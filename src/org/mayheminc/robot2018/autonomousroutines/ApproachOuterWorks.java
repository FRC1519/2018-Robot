package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ApproachOuterWorks extends CommandGroup {
    
    public  ApproachOuterWorks() {
    	addSequential(new DriveStraight(0.75, DistanceUnits.INCHES, 40));
    }
}
