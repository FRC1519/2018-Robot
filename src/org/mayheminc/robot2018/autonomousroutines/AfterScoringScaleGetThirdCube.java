package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AfterScoringScaleGetThirdCube extends CommandGroup {

    public AfterScoringScaleGetThirdCube(Autonomous.StartOn startSide) {

    	// back away from the scale a bit and head towards the cube at the fence
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addParallel(new IntakeInInstant());
    	
    	// drive a little bit away from the scale before lowering the upper assembly
       	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 30.0,
       			Autonomous.chooseAngle(startSide, 180.0))); // was .5
       	
    	// prepare upper assembly for getting a cube soon
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorArmOpen());
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
       	
    	// drive the remaining distance while the upper assembly is going down
       	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 50.0,
       			Autonomous.chooseAngle(startSide, 205.0))); // was .5
       	
    	// drive the last little bit and engage the (second) cube
    	addSequential(new AIGatherCube());
    	addParallel(new IntakeOff());
    	
    	// back up a couple inches to avoid "stripping" cube when picking it up
       	addParallel(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 2.0,
       			Autonomous.chooseAngle(startSide, 180.0))); // was .5
       	
    	// do a handoff, but without setting the new elevator height
//    	addSequential(new HandoffCubeToElevatorWithoutRaisingElevator());	
    	addSequential(new RunIfAutoTimeRemaining(2.0, HandoffCubeToElevatorWithoutRaisingElevator.class));
    }
}
