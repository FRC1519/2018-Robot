package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotor;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
//import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.TurretMoveTo;
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
 *  Start Right
 *  Score on Right Scale
 *  Grab Right Corner Cube
 *  Score Right Switch
 */
public class ScaleAndSwitchONO extends CommandGroup {

    public ScaleAndSwitchONO(Autonomous.StartOn startSide) {
    	    	
    	// Used the "shared routine" for scoring on the near scale
    	// this routine ends up with the cube scored and ready to drive away...
    	addSequential(new ScoreOnNearScale(startSide));
    	
    	// drive away from the scale a bit and then head down the alley (total alley distance is about 230 inches)
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 5.0,
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new ElevatorArmSetMotorAuto(0.0));
    	
    	if( startSide == StartOn.LEFT) {
        	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 75.0,
        			Autonomous.chooseAngle(startSide, 280.0)));
    	} else {
    		// prior to out-of-bag on comp. robot, was one drive at 0.9 of 80.0 inches
        	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 30.0,
        			Autonomous.chooseAngle(startSide, 280.0)));
        	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 50.0,
        			Autonomous.chooseAngle(startSide, 280.0)));
    	}
    	
    	// prepare upper assembly to get cube.
    	////  NOTE  SHOULD GO TO DOWNWARD POSITION
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));    // PivotToFloor());
    	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 60.0,   // was 55 inches on OOB#1; 40 inches on pract robot
    			Autonomous.chooseAngle(startSide, 280.0)));
    	
    	// drive last 20 inches down alley at a slower speed to get ready for the turn
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 270.0)));
    	
    	// turn intake on to get ready to take in the cube
    	addSequential(new IntakeInInstant());
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 40.0,   // was 0.5 and 50.0 on pract robot
    			Autonomous.chooseAngle(startSide, 180.0)));
 
    	// drive the last little bit and engage the cube
    	// engage the cube and lift it up.
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new IntakeOff());
    	addSequential(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));
   
    	// drive a little closer to the switch and spit it over the fence
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 170.0)));
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));
    	addSequential(new Wait(0.7));  // wait for a bit to get the cube out
    	
       	addSequential(new PrintAutonomousTimeRemaining("Just delivered the 2nd cube!"));
    	// back away from the switch two feet
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 24.0,
    			Autonomous.chooseAngle(startSide, 170.0)));
    	
    }
}
