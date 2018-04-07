package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
//import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScaleAndSwitchOOO extends CommandGroup {

	public ScaleAndSwitchOOO(Autonomous.StartOn startSide) {
		
    	// Used the "shared routine" for scoring on the near scale
    	// this routine ends up with the 2nd cube picked up and handed off.
    	addSequential(new ScoreOnOppositeScaleAndGetNextCube(startSide));
    	
    	// after getting cube, need to raise it to switch height
    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	addParallel(new IntakeCloseJaw());
    	
    	// drive a little closer to the switch and spit it over the fence
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 170.0)));
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));
    	addSequential(new Wait(0.7));  // wait for a bit to get the cube out
    	
    	// back away from the switch two feet
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 24.0,
    			Autonomous.chooseAngle(startSide, 170.0)));

	}
}
