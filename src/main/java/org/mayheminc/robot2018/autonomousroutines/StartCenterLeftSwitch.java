package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmClose;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.commands.PivotMove;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartCenterLeftSwitch extends CommandGroup {

    public StartCenterLeftSwitch() {

    	// presume that the robot is starting out forwards
    	addSequential(new ZeroGyro() );
    	
    	// gently run the T-Rex motor inwards to hold cube better
      	addSequential(new ElevatorArmClose());
    	addSequential(new ElevatorArmSetMotorAuto(0.2));

    	// ensure the turret is pointed straight forward
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
    	
    	// start driving straight forwards
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 5.0, 0.0));
    	
    	// raise cube to a good carrying height while driving
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// go almost due west
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 50.0, 280.0));
    	// drive to near-side of fence
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 75.0, 0.0));  // was 70.0 before UNH
    	
      	addSequential(new PrintAutonomousTimeRemaining("Dropping the 1st Cube"));
      	addSequential(new ElevatorArmOpen());
      		
       	// wait for the robot to fully stop before we back up
    	addSequential(new Wait(0.5)); 
    	
    	// turn off the T-Rex, lower the intake, and back away from the fence a bit
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 20.0, 0.0)); // was .5

    	addSequential(new PrintAutonomousTimeRemaining("StartCenterLeftSwitch Done"));
    }
}
