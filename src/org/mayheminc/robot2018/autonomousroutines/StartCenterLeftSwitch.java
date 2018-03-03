package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.PrintToDriverStation;
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
 *
 */
public class StartCenterLeftSwitch extends CommandGroup {

    public StartCenterLeftSwitch() {
    	
    	addSequential(new PrintToDriverStation("StartCenterLeftSwitch"));

    	// presume that the robot is starting out forwards
    	addSequential(new ZeroGyro() );
    	addSequential(new Wait(0.5));
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height while starting to drive
    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
//    	addSequential(new Wait(2.0)); 
    	
    	// go almost due east
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 45.0, 280.0));
    	// drive to near-side of fence
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 82.0, 0.0));
    	
    	// drive gently for a little longer to make sure we're against the fence
//    	addSequential(new DriveStraightOnHeadingForTime(0.4, DistanceUnits.INCHES, 60.0, 0.0));
    	
      	addSequential(new PrintAutonomousTimeRemaining("Spitting Out Cube"));
      	addSequential(new Wait(1.0));
    	    	// spit out the the cube
    	addSequential(new ElevatorArmSetMotorAuto(-0.8));
      		
       	// wait for the robot to fully stop before we back up
    	addSequential(new Wait(0.5)); 
    	
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	
    	// turn off the T-Rex spit and back away from the fence a bit
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 20.0, 0.0)); // was .5

    	addSequential(new PrintAutonomousTimeRemaining("StartCenterRightSwitch Done"));

    	
    }
}
