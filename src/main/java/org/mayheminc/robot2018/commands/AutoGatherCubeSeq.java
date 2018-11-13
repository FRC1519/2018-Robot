package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.commands.DriveStraight.DistanceUnits;
//import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.subsystems.Pivot;
import org.mayheminc.robot2018.subsystems.CubeDetector;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGatherCubeSeq extends CommandGroup {

    public AutoGatherCubeSeq() {
    	addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new IntakeInInstant());
    	addSequential(new AIGatherCube());
    	addSequential(new IntakeOff());
    	addSequential(new PivotMove(Pivot.UPRIGHT_POSITION));
    	addSequential(new WaitForever());
    }
}
