package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossBaselineBackwards extends CommandGroup {

    public CrossBaselineBackwards() {    	
    	// presume that the robot is starting out backwards
    	addSequential( new ZeroGyro(180.0) );
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// drive backwards for ~100 inches, nice and slow.
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 100.0, 180.0));
    }
}
