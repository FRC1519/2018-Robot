package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartLeftBackSwitchLeft extends CommandGroup {

    public StartLeftBackSwitchLeft() {
    	// start the turret to shift to the right
    	addSequential(new TurretMoveTo(Turret.LEFT_POSITION));
    	// drive backwards, angling towards the switch
    	addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 100.0, 190.0));
    	// spit the cube while we are driving
    	addParallel(new ElevatorArmSetMotorAuto(0.4));
    }
}
