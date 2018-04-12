package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SafePosition;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmClose;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScorePickedUpCubeFromFenceOntoNearScale extends CommandGroup {

    public ScorePickedUpCubeFromFenceOntoNearScale(Autonomous.StartOn startSide) {

    	// raise the elevator and turn the turret
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
//    	addSequential(new Wait(0.4));    // give the above commands a little time to get started before driving

    	// start driving to the scale a bit before turning the turret
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 5.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 155.0)));
    	addParallel(new IntakeCloseJaw());
    	addSequential(new DriveStraightOnHeading(-0.8, DistanceUnits.INCHES, 45.0,   // was 50.0 before NECMP
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	
    	// wait for the robot to stop before spitting out the cube
    	addSequential(new Wait(1.0 /* 0.8 */ /* 0.3 */ ));    // was 0.3 for "FAST"; was 0.8 before NECMP
    	
    	// spit out the the cube and open the arms, too - belt and suspenders!
    	addSequential(new ElevatorArmSetMotorAuto(-0.5));
    	addSequential(new ElevatorArmOpen());
    	
    	addSequential(new PrintAutonomousTimeRemaining("Dropping the 2nd Cube"));

    	// wait for the robot to fully eject cube before we back up
    	addSequential(new Wait(0.2)); 

    }
}
