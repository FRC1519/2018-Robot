package org.mayheminc.robot2018.autonomousroutines;
import org.mayheminc.robot2018.commands.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class auto1 extends CommandGroup {

    public auto1() {
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
    	
    	addSequential(new DriveToDistance(10.0));//bring the robot just before the edge of the switch fence.
    	addSequential(new ArcingTurn(1.0, 1.0, -.5));//this makes the robot arc into the fence on the switch.
    	addSequential(new ArcingTurn(-1.0, 1.0, -.5));//does the same thing as the last line but backwards?
    	addSequential(new ArcingTurn(1.0, 1.0, -.5));//needs to find cube while curving
    	//hopefully find a cube?
    	addSequential(new ArcingTurn(1.0, 1.0, .5));//curve towards the "NULL ZONE"
    	addSequential(new DriveToDistance(10.0));//drive into the "NULL ZONE"
    	
    	
    }
}
