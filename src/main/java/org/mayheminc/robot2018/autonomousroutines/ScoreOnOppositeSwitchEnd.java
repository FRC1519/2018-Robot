package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveRotate.DesiredHeadingForm;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.*;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreOnOppositeSwitchEnd extends CommandGroup {

	/**
	 * does the scale with another robot.
	 * @param startSide
	 */
	public ScoreOnOppositeSwitchEnd(Autonomous.StartOn startSide)
	{

		// presume that the robot is starting out backwards
		// the turret is rotated towards the center of the field to face down field.
		addParallel(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
		addSequential(new ZeroGyro(180.0) );  	

		// ensure we're holding the cube firmly by closing the arms and running the T-rex motors inwards
		addParallel(new ElevatorArmClose());    	
		addParallel(new ElevatorArmSetMotorAuto(0.2));

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
		
		// 2 - raising elevator to scoring height for switch
				addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
		
		// make the turn to head downfield and continue driving to near the end of the switch
		addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 115.0,
				Autonomous.chooseAngle(startSide, 175.0)));
				
		// shift into low gear to slow down as approaching switch
		addParallel(new DriveSetShifter(Shifter.LOW_GEAR)); 
		addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 45.0,      
				Autonomous.chooseAngle(startSide, 180.0)));

		// turn to deliver the cube into the end of the switch
		addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 40.0,      
				Autonomous.chooseAngle(startSide, 270.0)));
		
		// deliver the cube into the switch
		addSequential(new Wait(0.5));
		addSequential(new ElevatorArmSetMotorAuto(-1.0));
		addSequential(new Wait(1.0));
		
		// drive away from the switch
		addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 30.0,
				Autonomous.chooseAngle(startSide, 300.0)));

    	// prepare upper assembly for getting a cube soon
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorArmOpen());
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
		addSequential(new Wait(1.0));
			
		// turn to face towards the far fence.
    	addSequential(new DriveRotate(Autonomous.chooseAngle(startSide, 0.0), DesiredHeadingForm.ABSOLUTE));
		
    	addSequential(new PrintAutonomousTimeRemaining("All Done."));
		
	}


}


