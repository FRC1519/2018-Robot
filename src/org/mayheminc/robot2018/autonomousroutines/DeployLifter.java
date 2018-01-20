package org.mayheminc.robot2018.autonomousroutines;
import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DeployLifter extends CommandGroup {
    
    public  DeployLifter(boolean arg_requireArm) {
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
    	addSequential(new SetArmPosition(Robot.arm.PORTCULLIS_POSITION_COUNT, arg_requireArm));
    	addSequential(new CloseClaw());
    	addParallel(new MoveLifter(Robot.lifter.LIFTER_POWER_DEPLOY));
    }
}
