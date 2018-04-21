package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.TurretZero;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HalfWayThroughAlley extends CommandGroup {
    
    public HalfWayThroughAlley(Autonomous.StartOn startSide) {
    	
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro(180.0) );
    	  	
    	// the turret is rotated towards the center of the field to face down field.
    	addSequential(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
  
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height before turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// drive straight backwards until near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 182.0,  // was 180.0 before NECMP; was 175.0 on Day 1 of UNH
    			Autonomous.chooseAngle(startSide, 180.0))); // was -.5

    	// put the turret to the scoring angle (was Turret.LEFT_ANGLED_BACK_POSITION)
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 180.0)));
   	
    	// driving down the alley
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 100.0,  
    			Autonomous.chooseAngle(startSide, 90.0))); // was -0.5
    	
    	// raise elevator to scoring height on normal scale
    	addSequential(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    }
}
