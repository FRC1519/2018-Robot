package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotor;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.SetHeadingOffset180;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Start Right
 *  Score on Right Scale
 *  Grab Right Corner Cube
 *  Score Right Switch
 */
public class StartRightBackScaleRight extends CommandGroup {

    public StartRightBackScaleRight() {
    	
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro() );
    	addSequential(new SetHeadingOffset180());
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height before turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));

    	// start the turret to shift to the right  (really want it to be off the back)
    	addParallel(new TurretMoveTo(Turret.RIGHT_POSITION));
    	
    	// drive straight backwards until near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 145.0, 180.0)); // was -.5
    	
    	// put the turret to the scoring angle
    	addParallel(new TurretMoveTo(Turret.RIGHT_ANGLED_BACK_POSITION));
    	
    	// raise elevator to scoring height on normal scale
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	
    	// continue driving backwards, angling towards the scale
    	addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 90.0, 150.0)); // was -0.5
    	
    	// straighten out again to be perpendicular to side of scale
    	addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 35.0, 180.0)); // was -.5
    	
    	// spit out the the cube
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));

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
    	
    	// engage the cube
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 15.0, 180.0));
    	addSequential(new IntakeInForTime(1.0));
    	
    	// back away and handoff to elevator
    	addSequential(new DriveStraightOnHeading(-0.6, DistanceUnits.INCHES, 5.0, 180.0)); // was .5
    	addSequential(new HandoffCubeToElevator());

    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 30.0, 200.0)); // was .3

    	// spit out the the cube
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));

//    	addParallel(new ElevatorArmSetMotor(0.4));
//    	
//    	// start the turret to shift to the front
//    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
//    	addSequential(new ElevatorSetPosition(Elevator.CEILING));
//    	
//    	// drive backwards
//    	addSequential(new DriveStraightOnHeading(-1.0, DistanceUnits.INCHES, 200.0, 180.0));
//    	
//    	// wait for the robot to stop swaying
//    	addSequential(new Wait(0.5));
//    	
//    	// spit the cube 
//    	addParallel(new ElevatorArmSetMotorAuto(0.4));
    }
}
