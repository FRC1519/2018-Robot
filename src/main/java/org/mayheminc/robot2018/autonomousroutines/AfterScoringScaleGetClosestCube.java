package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.HandoffCubeToElevatorWithoutRaisingElevator;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmClose;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AfterScoringScaleGetClosestCube extends CommandGroup {

    public AfterScoringScaleGetClosestCube(Autonomous.StartOn startSide) {

    	// back away from the scale a bit and head towards the cube at the fence
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addParallel(new IntakeInInstant());
    	
    	// drive a little bit away from the scale before lowering the upper assembly
       	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 10.0,
       			Autonomous.chooseAngle(startSide, 180.0))); // was .5
       	
    	// prepare upper assembly for getting a cube soon
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorArmOpen());
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
       	
    	// drive the remaining distance while the upper assembly is going down
       	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 40.0,
       			Autonomous.chooseAngle(startSide, 180.0))); // was .5
       	
    	// drive the last little bit and engage the (second) cube
    	addSequential(new AIGatherCube());
    	addParallel(new IntakeOff());
    	
    	// back up a couple inches to avoid "stripping" cube when picking it up
//       	addParallel(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 2.0,
//       			Autonomous.chooseAngle(startSide, 180.0))); // was .5
       	
    	// do a handoff, but without setting the new elevator height
    	addSequential(new HandoffCubeToElevatorWithoutRaisingElevator());	
    }
}
