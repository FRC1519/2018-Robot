package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightBackScaleRight extends CommandGroup {

    public StartRightBackScaleRight() {
    	// start the turret to shift to the front
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new ElevatorSetPosition(Elevator.CEILING));
    	
    	// drive backwards
    	addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 200.0, 180.0));
    	
    	// wait for the robot to stop swaying
    	addSequential(new Wait(0.5));
    	
    	// spit the cube 
    	addParallel(new ElevatorArmSetMotorAuto(0.4));
    }
}
