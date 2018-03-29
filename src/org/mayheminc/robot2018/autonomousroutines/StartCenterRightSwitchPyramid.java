package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AllRollersOut;
import org.mayheminc.robot2018.commands.AutoGatherCubeSeq;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOut;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.IntakeOutInstant;
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
    	
    	// start by putting one cube into the right switch
    	addSequential(new StartCenterRightSwitch());
    	
    	// above routine just delivered the first cube!!!
    	
    	// pivot is already down from end of 1st cube; prepare to go get a 2nd cube
    	// put the pivot down and backup K-turn face the pyramid
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 40.0, 300.0)); // was .5
    	 
    	// drive to the front cube of pyramid (a little more slowly to not crash into pyramid)
    	addSequential(new Wait(0.3));
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0, 300.0)); // was .5
 	
    	// eat the cube
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	
    	// backup with the cube a little to get out of pyramid before doing handoff
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 5.0, 300.0)); // was .5
    	addSequential(new HandoffCubeToElevator());
    	
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 25.0, 0.0)); // was .5

    	// drive forward to kiss the switch
    	addSequential(new Wait(0.3));
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 55.0, 0.0)); // was .5
    	
    	// SHOULD DELIVER THE 2nd CUBE HERE
    	// spit out the the 2nd cube
    	addSequential(new ElevatorArmOpen());

    	// wait for the robot to fully stop before we back up
    	addSequential(new Wait(0.3)); 
       	addSequential(new PrintAutonomousTimeRemaining("Just delivered the 2nd cube!"));
    	
    	// back up after delivering the 2nd cube
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 15.0, 0.0)); // was .5
    	
    	// prepare to get a third cube
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 30.0, 320.0)); // was .5

    	// attack the pyramid to get a 3rd cube (2nd from pyramid)
    	addSequential(new Wait(0.3));
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 25.0, 315.0)); // was .5

    	// eat the cube (the 3rd one total)
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	
    	// backup with the cube a little to get out of pyramid before doing handoff
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 5.0, 315.0)); // was .5
    	addSequential(new HandoffCubeToElevator());

//    	
//    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 25.0, 0.0)); // was .5
//
//    	// drive forward to kiss the switch
//    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 55.0, 0.0)); // was .5
//
//    	// SHOULD DELIVER THE 3rd CUBE HERE
//    	// spit out the the 3rd cube
//    	addSequential(new ElevatorArmOpen());

       	addSequential(new PrintAutonomousTimeRemaining("StartCenterRightSwitchPyramid Done"));

        
    }
}
