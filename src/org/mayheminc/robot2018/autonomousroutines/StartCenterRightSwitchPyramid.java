package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AllRollersOut;
import org.mayheminc.robot2018.commands.AutoGatherCubeSeq;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.GatherCube;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOut;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.PrintToDriverStation;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartCenterRightSwitchPyramid extends CommandGroup {

    public StartCenterRightSwitchPyramid() {
    	
    	
    	addSequential(new PrintToDriverStation("ZeroGyro"));
    	// presume that the robot is starting out forwards
    	addSequential(new ZeroGyro() );
    	   	
//    	addSequential(new PrintToDriverStation("Wait"));
//    	addSequential(new Wait(0.5));
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new PrintToDriverStation("ElevatorArmSetMotorAuto"));
//    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height while starting to drive
    	addSequential(new PrintToDriverStation("ElevatorSetPosition"));
//    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// go almost due east
    	addSequential(new PrintToDriverStation("DriveStraightOnHeading"));
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 5.0, 0.0));
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 40.0, 80.0));
    	// drive to near-side of fence
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 65.0, 0.0));
    	
    	// drive gently for a little longer to make sure we're against the fence    	
      	addSequential(new PrintAutonomousTimeRemaining("Spitting Out Cube"));

    	// spit out the the cube
//      	addSequential(new ElevatorArmOpen());
      		
       	// wait for the robot to fully stop before we back up
    	addSequential(new Wait(0.3)); 
    	
//    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	
    	// just delivered the first cube!!!
    	// turn off the T-Rex spit and back away from the fence a bit
//    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 20.0, 0.0)); // was .5

    	addSequential(new PrintAutonomousTimeRemaining("StartCenterRightSwitch Done"));

    	addSequential(new PrintToDriverStation("StartCenter Right switch done."));
    	
    	// prepare to go get a 2nd cube
    	// put the pivot down and backup K-turn face the pyramid
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 40.0, 300.0)); // was .5
    	 
    	// drive to the front cube of pyramid (a little more slowly to not crash into pyramid)
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0, 300.0)); // was .5
 	
    	// eat the cube
//    	addSequential(new AutoGatherCubeSeq());
    	addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new IntakeInInstant());
    	addSequential(new GatherCube());
    	
    	// backup with the cube
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 5.0, 300.0)); // was .5
    	
    	// turn off the intake and lift
    	addParallel(new IntakeOff());
    	addParallel(new PivotMove(Pivot.UPRIGHT_POSITION));
    	// TEMP: Spit the cube
    	addParallel(new IntakeOutForTime(1.0));
    	
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 25.0, 0.0)); // was .5

    	// drive forward to kiss the switch
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 50.0, 0.0)); // was .5
    	
    	// SHOULD DELIVER THE 2nd CUBE HERE
    	// spit out the the 2nd cube
//  	addSequential(new ElevatorArmOpen());
  		
    	// wait for the robot to fully stop before we back up
    	addSequential(new Wait(0.3)); 
    	
    	// back up
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 15.0, 0.0)); // was .5
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 40.0, 0.0)); // was .5

    	// attack the pyramid to get a 3rd cube (2nd from pyramid)
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 25.0, 315.0)); // was .5

    	// eat a cube
//    	addSequential(new AutoGatherCubeSeq());
    	addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new IntakeInInstant());
    	addSequential(new GatherCube());
    	
    	// backup with the cube
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 5.0, 300.0)); // was .5

    	// turn off the intake and lift
    	addParallel(new IntakeOff());
    	addParallel(new PivotMove(Pivot.UPRIGHT_POSITION));
    	
       	// TEMP: Spit the cube
    	addParallel(new IntakeOutForTime(1.0));
    	
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 25.0, 0.0)); // was .5

    	// drive forward to kiss the switch
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 50.0, 0.0)); // was .5

       	addSequential(new PrintAutonomousTimeRemaining("StartCenterRightSwitch Done"));

        
    }
}
