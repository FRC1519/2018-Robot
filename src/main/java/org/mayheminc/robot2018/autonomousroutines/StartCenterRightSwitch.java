package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.PrintToDriverStation;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmClose;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.commands.PivotMove;


import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartCenterRightSwitch extends CommandGroup {

    public StartCenterRightSwitch() {
    	
    	// presume that the robot is starting out forwards
    	addSequential(new ZeroGyro() );
    	   	
    	// gently run the T-Rex motor inwards to hold cube better
      	addSequential(new ElevatorArmClose());
    	addSequential(new ElevatorArmSetMotorAuto(0.2));    
    	
    	// ensure the turret is pointed straight forward
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));

    	// go straight a little, and then almost due east
    	addSequential(new PrintToDriverStation("DriveStraightOnHeading"));
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 5.0, 0.0));
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 45.0, 80.0));
    	// drive to near-side of fence
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 65.0, 0.0));

    	// spit out the first (starting) cube
      	addSequential(new ElevatorArmOpen());
      		
       	// wait for the robot to fully stop before we back up
    	addSequential(new Wait(0.3)); 

    	// just delivered the first cube!!!
    	// turn off the T-Rex spit and back away from the fence a bit
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 20.0, 0.0)); // was .5

    	addSequential(new PrintAutonomousTimeRemaining("StartCenterRightSwitch Done"));
    	
    }
}
