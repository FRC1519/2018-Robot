package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGatherCubeSeq extends CommandGroup {

    public AutoGatherCubeSeq() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));// PivotToFloor());
    	addSequential(new IntakeInInstant());
    	addSequential(new GatherCube());
//    	addSequential(new IntakeEscapeDeathGripLeft());
    	addSequential(new IntakeOff());
//    	addSequential(new PivotToUpright());
    	addSequential(new WaitForever());
    }
}
