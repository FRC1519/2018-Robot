package org.mayheminc.robot2018.autonomousroutines;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.mayheminc.robot2018.subsystems.*;

/**
 *
 */
public class auto1 extends CommandGroup {

    public auto1() {
    	addSequential( new ZeroGyro() );

    	addSequential(new DriveStraight(-.2, DriveStraight.DistanceUnits.INCHES, 100.0));
    	addSequential(new Wait(1.0));
    	addSequential(new DriveStraightOnHeadingForTime(-.02,100,0));
    	addSequential(new Wait(1.0));
    	addSequential(new DriveStraightOnHeadingForTime(-.02,100,0));
    	addSequential(new Wait(1.0));
    	addSequential(new DriveStraightOnHeadingForTime(-.02,100,0));
    	addSequential(new Wait(1.0));
    }
}
