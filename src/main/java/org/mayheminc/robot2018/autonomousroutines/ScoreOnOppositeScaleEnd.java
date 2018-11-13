package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveRotate.DesiredHeadingForm;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.*;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreOnOppositeScaleEnd extends CommandGroup {

	/**
	 * does the scale with another robot.
	 * @param startSide
	 */
	public ScoreOnOppositeScaleEnd(Autonomous.StartOn startSide)
	{

		// presume that the robot is starting out backwards
		// the turret is rotated towards the center of the field to face down field.
		addParallel(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
		addSequential(new ZeroGyro(180.0) );  	

		// ensure we're holding the cube firmly by closing the arms and running the T-rex motors inwards
		addParallel(new ElevatorArmClose());    	
		addParallel(new ElevatorArmSetMotorAuto(0.2));
    	addParallel(new TurretMoveTo((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));

		// drive straight backwards 5 inches in low gear to get rolling
		addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 5.0,  // was 175.0 on Day 1 of UNH
				Autonomous.chooseAngle(startSide, 180.0))); // was -.5
		
		// make a sharp turn to point along the alliance wall
		addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 30.0,
    			Autonomous.chooseAngle(startSide, 90.0)));
		
		// drive 10 inches at high power in low gear to get going...
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 10.0,
    			Autonomous.chooseAngle(startSide, 90.0))); // was -.5

		// shift into high gear to go FAST!
		addParallel(new DriveSetShifter(Shifter.HIGH_GEAR)); 
		
		// drive laterally across the field in front of the alliance wall
		addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 145.0,  // was 175 in low gear
    			Autonomous.chooseAngle(startSide, 90.0)));
		
		// turn downfield and drive to near the end of the switch
		addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 155.0,
				Autonomous.chooseAngle(startSide, 175.0)));

		// 2 - raising elevator to scoring height for potentially raised scale
		addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));

		// straighten out again to be perpendicular to side of scale; do first part in high gear
		addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 120.0,      // was 60 on Day 1 of UNH; before was 45.0
				Autonomous.chooseAngle(startSide, 180.0)));

		// shift into low gear to slow down as approaching scale
		addParallel(new DriveSetShifter(Shifter.LOW_GEAR)); 
		addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 45.0,      // was 60 on Day 1 of UNH; before was 45.0
				Autonomous.chooseAngle(startSide, 180.0)));

		addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 40.0,      // was 60 on Day 1 of UNH; before was 45.0
				Autonomous.chooseAngle(startSide, 270.0)));
		
		addSequential(new Wait(0.5));
		addSequential(new ElevatorArmSetMotorAuto(-0.4));
		addSequential(new ElevatorArmOpen());
		addSequential(new Wait(0.5));
		
		// drive away from the scale
		addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 30.0,
				Autonomous.chooseAngle(startSide, 240.0)));

    	// prepare upper assembly for getting a cube soon
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorArmOpen());
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
		addSequential(new Wait(1.0));
			
		// turn to face towards the far fence.
    	addSequential(new DriveRotate(Autonomous.chooseAngle(startSide, 30.0), DesiredHeadingForm.ABSOLUTE));
    	
    	// start driving towards the far fence
		addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 10.0,
				Autonomous.chooseAngle(startSide, 30.0)));
		
    	addSequential(new PrintAutonomousTimeRemaining("All Done."));
    	
		
	}


}


