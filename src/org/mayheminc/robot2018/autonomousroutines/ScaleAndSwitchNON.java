package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.ElevatorWaitUntilAtPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.IntakeOutForTime;
import org.mayheminc.robot2018.commands.PivotMove;
//import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.SafePosition;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive by the switch, drop off the side, go down the alley, pick up corner cube, score in scale.
 */
public class ScaleAndSwitchNON extends CommandGroup {

	public ScaleAndSwitchNON(Autonomous.StartOn startSide) {
			 	
		// drive forward, drive-by-spit cube, drive past switch
		addSequential(new ScoreOnNearSwitch(startSide));
    	
    	// driving down the alley
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	
    	if( startSide == StartOn.LEFT) {
        	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 100.0,
        			Autonomous.chooseAngle(startSide, 90.0)));
    	} else {
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 105.0,
    			Autonomous.chooseAngle(startSide, 90.0)));
    	}
    	
    	// prepare for getting a cube soon
    	addParallel(new ElevatorArmOpen());
    	addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new DriveStraightOnHeading(-0.9, DistanceUnits.INCHES, 95.0,
    			Autonomous.chooseAngle(startSide, 90.0)));

    	// K-turn towards the cube that we want to pick up
    	addSequential(new DriveStraightOnHeading(-0.4, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    	addSequential(new DriveStraightOnHeading(0.4, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0))); // was -0.5
    
    	// drive the last little bit and engage the (second) cube
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new IntakeOff());

    	addSequential(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));
    	
    	// we just picked up a cube -- now score the cube onto the scale...
    	addSequential(new ScorePickedUpCubeFromFenceOntoOppositeScale(startSide));
    	
    	
	}
}
