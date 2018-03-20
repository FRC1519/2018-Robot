package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.BackupAndHandOff;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotor;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
//import org.mayheminc.robot2018.commands.PivotToFloor;
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
 *  Start Right
 *  Score on Right Scale
 *  Grab Right Corner Cube
 *  Score Right Switch
 */
public class StartRightBackLRL extends CommandGroup {

    public StartRightBackLRL() {
    	
    	// presume that the robot is starting out backwards
    	addSequential(new ZeroGyro() );
    	addSequential(new SetHeadingOffset180());
    	
    	// gently run the T-Rex motor inwards to hold cube better
    	addSequential(new ElevatorArmSetMotorAuto(0.2));
    	
    	// raise cube to a good carrying height before turning turret
    	addParallel(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	
    	// drive straight backwards until near the end of the switch
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 150.0, 180.0));
    	
    	// put the turret to the scoring angle
    	addParallel(new TurretMoveTo(Turret.RIGHT_ANGLED_BACK_POSITION));
    	
    	// raise elevator to scoring height on normal scale
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	
    	// continue driving backwards, angling towards the scale
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 95.0, 145.0));
    	
    	// straighten out again to be perpendicular to side of scale
    	addSequential(new DriveStraightOnHeading(-0.7, DistanceUnits.INCHES, 45.0, 180.0));
    	
    	addSequential (new Wait(0.5));  // pause briefly before placing cube
    	
    	// spit out the the cube onto the scale
    	addSequential(new ElevatorArmSetMotorAuto(-0.5));
//    	addSequential(new ElevatorArmOpen());
    	
    	// wait for the robot to fully eject cube before we drive away from scale
    	addSequential(new Wait(0.5)); 
    	
    	// drive away from the scale a bit and then head down the alley (total alley distance is about 230 inches)
    	addSequential(new DriveStraightOnHeading(0.8, DistanceUnits.INCHES, 5.0, 180.0));
    	
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 80.0, 280.0));
    	
    	// prepare upper assembly to get cube.
    	////  NOTE  SHOULD GO TO DOWNWARD POSITION
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));    // PivotToFloor());
    	addSequential(new DriveStraightOnHeading(0.9, DistanceUnits.INCHES, 40.0, 280.0));
    	
    	// drive last 30 inches down alley at a slower speed to get ready for the turn
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0, 270.0));
    	
    	addSequential(new PrintAutonomousTimeRemaining("Ready to turn for 2nd cube."));
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 50.0, 180.0));
 
    	// drive the last little bit and engage the cube
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 5.0, 180.0));
    	addSequential(new IntakeInAndLiftTheCube(true));
    	addSequential(new BackupAndHandOff());

    	// drive forward again to the fence so we can score into the switch
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 15.0, 170.0)); // was .3

    	// spit out the the cube
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));
    	addSequential(new Wait(1.0));
    	
    	// back away from the switch two feet
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 24.0, 170.0)); // was .3
    	
    }
}
