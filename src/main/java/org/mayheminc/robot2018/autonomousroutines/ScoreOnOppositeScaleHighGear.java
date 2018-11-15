package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveSetShifter;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.TurretZero;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Shifter;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreOnOppositeScaleHighGear extends CommandGroup {

    public ScoreOnOppositeScaleHighGear(Autonomous.StartOn startSide) {
    	
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro(180.0) );
    	
    	// the turret is rotated towards the center of the field to face down field.
    	addSequential(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
  
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height before turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// drive straight backwards until near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 10.0,  // was 175.0 on Day 1 of UNH
    			Autonomous.chooseAngle(startSide, 180.0))); // was -.5
    	
    	addParallel(new DriveSetShifter(Shifter.HIGH_GEAR));
    	
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 170.0,  // was 175.0 on Day 1 of UNH
    			Autonomous.chooseAngle(startSide, 180.0))); // was -.5
    	
    	// put the turret to the scoring angle (was Turret.LEFT_ANGLED_BACK_POSITION)
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 200.0)));
   	
    	// driving down the alley
    	addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 50.0,
    			Autonomous.chooseAngle(startSide, 90.0))); // was -0.5
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 65.0,  // was 75.0 before NECMP
    			Autonomous.chooseAngle(startSide, 90.0))); // was -0.5
    	
    	// raise elevator to scoring height on normal scale
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	if( startSide == StartOn.LEFT)
    	{
	    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 90.0,  // was 75.0
	    			Autonomous.chooseAngle(startSide, 90.0))); // was -0.5
    	}
    	else
    	{
	    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 90.0,   // was 95.0 at start of UNH
	    			Autonomous.chooseAngle(startSide, 90.0))); // was -0.5
    	}
    	addParallel(new DriveSetShifter(Shifter.LOW_GEAR));
    	// turn towards the scale
    	if( startSide == StartOn.LEFT)
    	{
    		addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 63.0,  // was 58.0 for right
    				Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	}
    	else
    	{
    		addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 73.0,  // was 63.0 at start of NECMP practice
    				Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	}
    	
    	// lower the intake arm to get ready to harvest a 2nd cube soon
       	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
       	addSequential (new Wait(0.4));  // pause briefly before placing cube
    	
    	// spit out the the cube and open the arms, too -- belt and suspenders!
    	addParallel(new PrintAutonomousTimeRemaining("Spitting out 1st cube."));
    	addSequential(new ElevatorArmSetMotorAuto(-0.5));
    	addSequential(new ElevatorArmOpen());

    	// wait for the robot to fully eject cube before we back up
    	addSequential(new Wait(0.4)); 
    	
    	///////////////////////////////////////////////////////////////////////
    	// just scored the 1st cube on the scale at the point above
    	
    }
}
