package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.DriveSetShifter;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.PrintAutonomousTimeRemaining;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Shifter;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class MultiSwitchN extends CommandGroup{

	public MultiSwitchN(Autonomous.StartOn startSide)
	{
		// drive by backwards, spit cube, drive past.
		addSequential(new ScoreOnNearSwitch(startSide));
		
    	addParallel(new DriveSetShifter(Shifter.LOW_GEAR));
		addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
		addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new ElevatorArmOpen());
		
		// drive and line up to cube.
		addSequential(new DriveStraightOnHeading(-0.8, 70, 150));
		addSequential(new DriveStraightOnHeading(0.4, 20, 180));
		
    	// drive the last little bit and engage the cube
    	// engage the cube and lift it up.
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new DriveStraightOnHeading(-0.3, 5, 180));
		addSequential(new IntakeOff());
    	addSequential(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));
   
		addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 15.0)));
    	// drive a little closer to the switch and spit it over the fence
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0)));
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));
    	addSequential(new Wait(0.7));  // wait for a bit to get the cube out 
    	
 		addParallel(new PivotMove(Pivot.DOWNWARD_POSITION));
		addParallel(new TurretMoveTo(Turret.FRONT_POSITION));
    	addParallel(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    	addParallel(new ElevatorArmOpen());

    	// backup and line up to next cube.      	
		addSequential(new DriveStraightOnHeading(-0.4, 20, 180));
		addSequential(new DriveStraightOnHeading(-0.4, 20, 90));
		addSequential(new DriveStraightOnHeading(-0.8, 50, 90));
		addSequential(new DriveStraightOnHeading(0.3, 10, 180));

     	
    	// drive the last little bit and engage the cube
    	// engage the cube and lift it up.
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new IntakeOff());
    	addSequential(new HandoffCubeToElevator(Elevator.SWITCH_HEIGHT));

		addParallel(new TurretMoveToDegree(Autonomous.chooseAngle(startSide, 15.0)));
    	// drive a little closer to the switch and spit it over the fence
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0,
    			Autonomous.chooseAngle(startSide, 180.0)));
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));
    	addSequential(new Wait(0.7));  // wait for a bit to get the cube out 

    	
    	addSequential(new PrintAutonomousTimeRemaining("Dropping the 2nd Cube"));

    	
	}
}
