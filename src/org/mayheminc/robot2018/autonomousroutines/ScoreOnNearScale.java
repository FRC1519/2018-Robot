package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmClose;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreOnNearScale extends CommandGroup {

    public ScoreOnNearScale(Autonomous.StartOn startSide) {
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro(180.0) );
    	
    	addParallel(new ElevatorArmClose());
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height before turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// drive straight backwards until near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 160.0,    // was 155 on Day of UNH; was 150 inches
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	// put the turret to the scoring angle (was Turret.RIGHT_ANGLED_BACK_POSITION)
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 170)));
    	
    	// raise elevator to scoring height on normal scale
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	
    	if( startSide == StartOn.LEFT) {
    		// continue driving backwards, angling towards the scale
    		addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 90.0,
    				Autonomous.chooseAngle(startSide, 145.0)));  // was 155.0 for pract robot 
    	} else {
    		// continue driving backwards, angling towards the scale
    		addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 70.0,   // was 75 on Day 1 of UNH; was 90 at start of UNH was 95.0
    				Autonomous.chooseAngle(startSide, 145.0)));
    	}
    	
    	// straighten out again to be perpendicular to side of scale
    	addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 55.0,      // was 60 on Day 1 of UNH; before was 45.0
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	// lower the intake arm to get ready to harvest a 2nd cube soon
       	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential (new Wait(0.4));  // pause briefly before placing cube
    	
    	// spit out the the cube and open the arms, too -- belt and suspenders!
    	addSequential(new ElevatorArmSetMotorAuto(-0.4));
    	addSequential(new ElevatorArmOpen());
    	
    	// wait for the robot to fully eject cube before we back up
    	addSequential(new Wait(0.4)); 
    }
}
