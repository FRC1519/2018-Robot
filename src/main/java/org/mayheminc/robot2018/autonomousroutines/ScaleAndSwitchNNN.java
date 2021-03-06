package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.IntakeCloseJaw;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Elevator;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  Start Right
 *  Score on Right Scale
 *  Grab Right Corner Cube
 *  Score Right Switch
 */
public class ScaleAndSwitchNNN extends CommandGroup {

    public ScaleAndSwitchNNN(Autonomous.StartOn startSide) {
    	
    	// Used the "shared routine" for scoring on the near scale
    	// this routine ends up with the 1st cube scored and the 2nd cube picked up...
    	addSequential(new ScoreOnNearScaleAndGetNextCube(startSide));
    	
    	// after getting cube, need to raise it to switch height
    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	addParallel(new IntakeCloseJaw());
    	
    	// drive forward again to the fence so we can score into the switch
    	addSequential(new DriveStraightOnHeading(0.5, DistanceUnits.INCHES, 20.0, 
    			Autonomous.chooseAngle(startSide, 200.0)));

    	// spit out the the cube
    	addSequential(new ElevatorArmSetMotorAuto(-1.0));
    	addSequential(new Wait(0.7));   // spit out the cube forcefully!
    	
    	// back away from the switch two feet
    	addParallel(new ElevatorArmSetMotorAuto(0.0));
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 24.0,
    			Autonomous.chooseAngle(startSide, 200.0)));

    }
}
