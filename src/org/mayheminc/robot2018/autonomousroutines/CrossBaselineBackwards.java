package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.TurretZero;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossBaselineBackwards extends CommandGroup {

    public CrossBaselineBackwards(Autonomous.StartOn startSide) {    	
    	// presume that the robot is starting out backwards
    	// the turret is rotated towards the center of the field to face down field.
    	addParallel(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
    	addSequential(new ZeroGyro(180.0) );  
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// drive backwards for ~100 inches, nice and slow.
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 100.0, 180.0));
    	
    	// raise the elevator to switch height and then center the turret
    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
    }
}
