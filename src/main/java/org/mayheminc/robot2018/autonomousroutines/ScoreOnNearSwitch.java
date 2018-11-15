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
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class ScoreOnNearSwitch extends CommandGroup{
	
	public ScoreOnNearSwitch(Autonomous.StartOn startSide)
	{
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro(180.0) );
    	
    	// the turret is rotated towards the center of the field to face down field.
    	addSequential(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
  
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
 
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 10.0,  // was 80 mid-way through UNH
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
//    	addParallel(new DriveSetShifter(Shifter.HIGH_GEAR));
    	
    	// drive by the switch and deliver the cube (total straight distance before turn is 175 inches)
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 50.0,  // was 80 mid-way through UNH
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	// turn the turret to the scoring angle
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 90.0)));    	
    	
    	if ( startSide == StartOn.LEFT) {
    		addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 70.0,  // was 65 mid-way through UNH
     			Autonomous.chooseAngle(startSide, 160.0)));
    	} else {
        	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 70.0,
        			Autonomous.chooseAngle(startSide, 160.0)));		// was 150.00 before UNH 
    	}
    	addParallel(new ElevatorArmSetMotorAuto(-1.0));    // spit out the cube as driving by
    	if( startSide == StartOn.LEFT) {
	    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 60.0,
	    			Autonomous.chooseAngle(startSide, 180.0)));
    	} else {
	    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 60.0,   // was 30.0 before UNH
	    			Autonomous.chooseAngle(startSide, 180.0)));
    	}	
    	
	}

}
