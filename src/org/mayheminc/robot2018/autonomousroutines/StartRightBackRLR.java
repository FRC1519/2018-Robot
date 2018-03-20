package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.BackupAndHandOff;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.ElevatorWaitUntilAtPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
//import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SafePosition;
import org.mayheminc.robot2018.commands.SetHeadingOffset180;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive to switch, drop off the side, go down the alley, pick up corner cube, score in scale.
 */
public class StartRightBackRLR extends CommandGroup {

	public StartRightBackRLR() {
			 	
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro() );
    	addSequential(new SetHeadingOffset180());
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height while turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));

    	// start the turret to shift to the right  (really want it to be off the back)
    	addParallel(new TurretMoveTo(Turret.RIGHT_POSITION));
    	
    	// drive by the switch and deliver the cube (total straight distance before turn is 175 inches)
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 80.0, 180.0));
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 70.0, 150.0));
    	addParallel(new ElevatorArmSetMotorAuto(-0.5));    // spit out the cube as driving by
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 30.0, 180.0));
    
    	// driving down the alley
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 105.0, 90.0));
    	
    	// prepare for getting a cube soon
    	addParallel(new ElevatorArmOpen());
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 92.5, 90.0));

    	// K-turn towards the corner cube
    	addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 20.0, 180.0)); // was -0.5
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 20.0, 180.0)); // was -0.5
    	
    	addSequential(new IntakeInAndLiftTheCube(true));
    	addSequential(new BackupAndHandOff());
    	
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	addParallel(new TurretMoveTo(Turret.RIGHT_REAR));
    	addSequential(new Wait(1.0));    // give the above commands a little time to get started before driving
    	
    	// drive to the scale to deliver the cube
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 55.0, 180.0)); // was -0.5
    	
    	// wait for the robot to fully eject cube before we back up
    	addSequential(new Wait(0.5)); 
    	// spit cube.
    	addSequential(new ElevatorArmSetMotorAuto(-0.5));
    	
    	// drive forward a bit to disengage the scale
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 20.0, 180.0));
    	addSequential(new SafePosition());
    	
    	
	}
}
