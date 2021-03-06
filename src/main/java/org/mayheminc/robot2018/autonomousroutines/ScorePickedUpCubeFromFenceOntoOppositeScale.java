package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SafePosition;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ScorePickedUpCubeFromFenceOntoOppositeScale extends CommandGroup {

    public ScorePickedUpCubeFromFenceOntoOppositeScale(Autonomous.StartOn startSide) {
    	
    	// raise the elevator and turn the turret
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	addSequential(new Wait(0.4));    // give the above commands a little time to get started before driving

    	// start driving to the scale a bit before turning the turret
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 5.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 205.0)));
    	addParallel(new IntakeCloseJaw());
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 50.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	
    	// wait for the robot to stop moving before spitting out the cube
    	addSequential(new Wait(0.8));    // was 0.5 for "FAST!" 

    	// spit out the the cube and open the arms, too - belt and suspenders!
    	addSequential(new ElevatorArmSetMotorAuto(-0.5));
    	addSequential(new ElevatorArmOpen());

    	addSequential(new PrintAutonomousTimeRemaining("Dropping the 2nd Cube"));
    	
    	// wait for the robot to fully eject cube before we back up
    	addSequential(new Wait(0.2));  // was 0.4 
    	
    	// drive forward a bit to disengage the scale
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0)));
    	addSequential(new SafePosition());
    	
    }
}
