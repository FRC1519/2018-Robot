package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SetHeadingOffset180;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightBackLLL extends CommandGroup {

	public StartRightBackLLL() {
		
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro() );
    	addSequential(new SetHeadingOffset180());
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height before turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));

    	// start the turret to shift to the right  (really want it to be off the back)
//    	addParallel(new TurretMoveTo(Turret.LEFT_POSITION));
    	
    	// drive straight backwards until near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 175.0, 180.0)); // was -.5
    	addParallel(new TurretMoveTo(Turret.LEFT_ANGLED_BACK_POSITION));
   	
    	// driving down the alley
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 120.0, 90.0)); // was -0.5
    	
    	// raise elevator to scoring height on normal scale
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 105.0, 90.0)); // was -0.5

    	// turn towards the scale
    	addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 53.0, 180.0)); // was -0.5
  	  
    	// spit out the the cube
//    	addSequential(new ElevatorArmSetMotorAuto(-0.3));
    	addSequential(new ElevatorArmOpen());

    	// wait for the robot to fully stop before we back up (don't fall over)
    	addSequential(new Wait(0.5)); 
    	
    	// back away from the scale a bit and head towards the cube for the switch
    	addSequential(new DriveStraightOnHeading(0.6, DistanceUnits.INCHES, 20.0, 180.0)); // was .5
   	
    	// drive to get the cube
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	// put the turret to the front position
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new PivotToFloor());
    	
    	// engage the cube and lift it up.
    	addParallel(new ElevatorSetPosition(Elevator.HANDOFF_HEIGHT));
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 15.0, 180.0));
    	addSequential(new IntakeInForTime(1.5));     // had been 1.0 at practice field
    	addSequential(new PivotMove(Pivot.SPIT_POSITION));
    	
    	// drive a little closer and spit it out
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 10.0, 180.0));
    	addSequential(new IntakeOutForTime(1.0));
    	
    	addSequential(new Wait(10.0));
    	
    	
    	// back away and handoff to elevator
    	addSequential(new DriveStraightOnHeading(-0.6, DistanceUnits.INCHES, 5.0, 180.0)); // was .5
    	addSequential(new HandoffCubeToElevator());

    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 30.0, 200.0)); // was .3

    	// spit out the the cube
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));

//		// start the turret to shift to the rear
//		addSequential(new TurretMoveTo(Turret.RIGHT_REAR));
//		addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
//
//		// drive backwards
//		addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 150.0, 180.0));
//
//		// make the turn slower than full speed
//		addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 75.0, 90.0));
//		// drive down the alley.
//		addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 200.0, 90.0));
//
//		addSequential(new ElevatorSetPosition(Elevator.SCALE_HIGH));
//		// drive to the scale
//		addSequential(new DriveStraightOnHeading(-0.3, DistanceUnits.INCHES, 50.0, 180.0));
//
//		// wait for the robot to stop swaying
//		addSequential(new Wait(0.5));
//
//		// spit the cube 
//		addParallel(new ElevatorArmSetMotorAuto(0.4));
	}
}
