package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.ElevatorWaitUntilAtPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
//import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SafePosition;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive by the switch, drop off the side, go down the alley, pick up corner cube, score in scale.
 */
public class ScaleAndSwitchNON extends CommandGroup {

	public ScaleAndSwitchNON(Autonomous.StartOn startSide) {
			 	
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro(180.0) );
  
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// drive by the switch and deliver the cube (total straight distance before turn is 175 inches)
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 80.0,
    			Autonomous.chooseAngle(startSide, 180.0)));

    	// turn the turret to the scoring angle
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 90.0)));    	
    	
    	if ( startSide == StartOn.LEFT) {
    		addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 70.0,
    			Autonomous.chooseAngle(startSide, 160.0)));
    	} else {
        	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 70.0,
        			Autonomous.chooseAngle(startSide, 150.0)));		
    	}
    	addParallel(new ElevatorArmSetMotorAuto(-0.5));    // spit out the cube as driving by
    	if( startSide == StartOn.LEFT) {
	    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 40.0,
	    			Autonomous.chooseAngle(startSide, 180.0)));
    	} else {
	    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 30.0,
	    			Autonomous.chooseAngle(startSide, 180.0)));
    	}
    	
    	// driving down the alley
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	
    	if( startSide == StartOn.LEFT) {
        	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 100.0,
        			Autonomous.chooseAngle(startSide, 90.0)));
    	} else {
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 105.0,
    			Autonomous.chooseAngle(startSide, 90.0)));
    	}
    	
    	// prepare for getting a cube soon
    	addParallel(new ElevatorArmOpen());
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 92.5,
    			Autonomous.chooseAngle(startSide, 90.0)));

    	// K-turn towards the cube that we want to pick up
    	addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    
    	// drive the last little bit and engage the (second) cube
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new IntakeOff());

    	addSequential(new HandoffCubeToElevator());
    	
    	// we just picked up a cube -- now score the cube onto the scale...
    	addSequential(new ScorePickedUpCubeFromFenceOntoOppositeScale(startSide));
    	
    	
	}
}
