package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveSetShifter;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.TurretZero;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmClose;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Shifter;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScoreOnNearScale extends CommandGroup {

    public ScoreOnNearScale(Autonomous.StartOn startSide) {
    	// presume that the robot is starting out backwards
    	// the turret is rotated towards the center of the field to face down field.
    	addParallel(new TurretZero((startSide == Autonomous.StartOn.RIGHT) ? Turret.RIGHT_REAR : Turret.LEFT_REAR));
    	addSequential(new ZeroGyro(180.0) );  	

    	// ensure we're holding the cube firmly by closing the arms and running the T-rex motors inwards
    	addParallel(new ElevatorArmClose());    	
    	addParallel(new ElevatorArmSetMotorAuto(0.2));
    	
    	// drive straight backwards 10 inches in low gear to get rolling
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 10.0,  // was 175.0 on Day 1 of UNH
    			Autonomous.chooseAngle(startSide, 180.0))); // was -.5
    	
    	// shift into high gear to go FAST!
    	addParallel(new DriveSetShifter(Shifter.HIGH_GEAR)); 
    	
    	// prepare for scoring by 
    	// 1 - putting the turret to the scoring angle (was Turret.RIGHT_ANGLED_BACK_POSITION)
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 170)));
    	// 2 - raising elevator to scoring height for potentially raised scale
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	
    	// continue driving to near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 135.0,    // was 155 on Day of UNH; was 150 inches
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	if( startSide == StartOn.LEFT) {
    		// continue driving backwards, angling towards the scale
    		addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 90.0,
    				Autonomous.chooseAngle(startSide, 150.0)));  // was 140.0 before NECMP 
    	} else {
    		// continue driving backwards, angling towards the scale
    		addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 70.0,   // was 75 on Day 1 of UNH; was 90 at start of UNH was 95.0
    				Autonomous.chooseAngle(startSide, 150.0)));  // was 140.0 before NECMP 
    	}
    	
    	// straighten out again to be perpendicular to side of scale; do first part in high gear
    	addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 40.0,      // was 60 on Day 1 of UNH; before was 45.0
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	// do final approach to scale in low gear to shed some speed
    	addParallel(new DriveSetShifter(Shifter.LOW_GEAR)); 
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 27.0,      // was 25.0 before NECMP; was 30.0 at CMP
    			Autonomous.chooseAngle(startSide, 180.0)));
    	
    	// lower the intake arm to get ready to harvest a 2nd cube soon
       	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential (new Wait(0.9));  // pause briefly before placing cube (was 0.5 for "FAST!!!")
    	
    	// spit out the the cube and open the arms, too -- belt and suspenders!
    	addParallel(new PrintAutonomousTimeRemaining("Spitting out 1st cube."));
    	addSequential(new ElevatorArmSetMotorAuto(-0.6));
    	addSequential(new ElevatorArmOpen());
    	
    	// wait for the robot to fully eject cube before we change to low gear to back up
    	addSequential(new Wait(0.2));
    }
}
