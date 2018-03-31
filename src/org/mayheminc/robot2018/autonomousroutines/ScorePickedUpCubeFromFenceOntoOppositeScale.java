package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.PivotMove;
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
public class ScorePickedUpCubeFromFenceOntoOppositeScale extends CommandGroup {

    public ScorePickedUpCubeFromFenceOntoOppositeScale(Autonomous.StartOn startSide) {

    	// raise the elevator and turn the turret
    	addParallel(new ElevatorSetPosition(Elevator.SCALE_HIGH));
    	addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 205.0)));
    	addSequential(new Wait(0.5));    // give the above commands a little time to get started before driving
    	
    	// drive to the scale to deliver the cube
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 55.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	
    	// wait for the robot to stop moving before spitting out the cube
    	addSequential(new Wait(1.0)); 

    	// spit out the the cube and open the arms, too - belt and suspenders!
    	addSequential(new ElevatorArmSetMotorAuto(-0.5));
    	addSequential(new ElevatorArmOpen());

    	// wait for the robot to fully eject cube before we back up
    	addSequential(new Wait(0.4)); 
    	
    	// drive forward a bit to disengage the scale
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0)));
    	addSequential(new SafePosition());
    }
}