package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartCenterLeftSwitchPyramid extends CommandGroup {

    public StartCenterLeftSwitchPyramid() {
   	
    	// start by putting one cube into the left switch
    	addSequential(new StartCenterLeftSwitch());
    	
    	// prepare to go get a 2nd cube, by going a little further back
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 25.0, 0.0)); // was .5
    	// put the pivot down and backup K-turn face the pyramid
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addParallel(new IntakeInInstant());
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 40.0, 60.0)); // was .5 // was 300 for right
    	 
    	// drive to the front cube of pyramid (a little more slowly to not crash into pyramid)
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 45.0, 50.0)); // was .5 // was 300 for right
 	
    	// eat the cube
    	addSequential(new AIGatherCube());
    	addParallel(new IntakeOff());
    	
    	// backup with the cube a little to get out of pyramid before doing handoff
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 5.0, 60.0)); // was .5
    	addSequential(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));
    	
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 25.0, 0.0)); // was .5

    	// drive forward to kiss the switch
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 55.0, 0.0)); // was .5
    	
    	// SHOULD DELIVER THE 2nd CUBE HERE
    	// spit out the the 2nd cube
    	addSequential(new ElevatorArmOpen());
    	
    	addSequential(new Wait(1.0));
    	// backup 
 	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 40.0, 0.0)); 

  		// commented out the below so we only do 2 cubes.
    	
    	
    	
//    	// wait for the robot to fully stop before we back up
//    	addSequential(new Wait(0.3)); 
//       	addSequential(new PrintAutonomousTimeRemaining("Just delivered the 2nd cube!"));
//    	
//    	// back up after delivering the 2nd cube
//    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 15.0, 0.0)); // was .5
//
//    	// TEMPORARILY COMMENT OUT THE THIRD CUBE FOR NOW
//    	
//    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
//    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 30.0, 0.0)); // was .5
//
//    	// attack the pyramid to get a 3rd cube (2nd from pyramid)
//    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 25.0, 45.0)); // was .5// was 315 for right
//
//    	// eat a cube
//    	addSequential(new IntakeInInstant());
//    	addSequential(new AIGatherCube());
//    	
//    	// backup with the cube a little to get out of pyramid before doing handoff
//    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 5.0, 45.0)); // was .5
//    	addSequential(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));
//
////    	// K-turn towards the switch
////    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 25.0, 0.0)); // was .5
////
////    	// drive forward to kiss the switch
////    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 55.0, 0.0)); // was .5
//
//       	addSequential(new PrintAutonomousTimeRemaining("StartCenterRightSwitchPyramid Done"));

    }
}
