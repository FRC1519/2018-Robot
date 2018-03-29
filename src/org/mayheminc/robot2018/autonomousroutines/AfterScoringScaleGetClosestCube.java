package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
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
       	addSequential(new DriveStraightOnHeading(0.6, DistanceUnits.INCHES, 35.0,
       			Autonomous.chooseAngle(startSide, 180.0))); // was .5
       	
    	// prepare for getting a cube soon
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorArmOpen());
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	
    	// drive the last little bit and engage the (second) cube
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new IntakeOff());

    	addSequential(new HandoffCubeToElevator());
    	
    }
}
