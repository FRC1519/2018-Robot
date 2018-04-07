package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.ElevatorArmOpen;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotor;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.commands.ElevatorSetPosition;
import org.mayheminc.robot2018.commands.AIGatherCube;
import org.mayheminc.robot2018.commands.HandoffCubeToElevator;
import org.mayheminc.robot2018.commands.IntakeInAndLiftTheCube;
import org.mayheminc.robot2018.commands.IntakeInForTime;
import org.mayheminc.robot2018.commands.IntakeInInstant;
import org.mayheminc.robot2018.commands.IntakeOff;
import org.mayheminc.robot2018.commands.PivotMove;
import org.mayheminc.robot2018.commands.SafePosition;
//import org.mayheminc.robot2018.commands.PivotToFloor;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.TurretMoveToDegree;
import org.mayheminc.robot2018.commands.Wait;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Autonomous;
import org.mayheminc.robot2018.subsystems.Autonomous.StartOn;
import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *  
 */
public class MultiScaleN extends CommandGroup {

    public MultiScaleN(Autonomous.StartOn startSide) {
    	
    	// Used the "shared routine" for scoring on the near scale
    	// this routine ends up with the cube scored and ready to drive away...
    	addSequential(new ScoreOnNearScaleAndGetNextCube(startSide));
    	
    	// Score the just-picked up cube onto the scale
    	addSequential(new ScorePickedUpCubeFromFenceOntoNearScale(startSide));
    	
    	// Either disengage from the scale, or get a third cube
    	//addSequential(new DisengageFromScale());
    	addSequential(new AfterScoringScaleGetThirdCube(startSide));

    }
}
