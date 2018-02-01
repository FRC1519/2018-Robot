package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartMidLeftSwitchRightScale extends CommandGroup {

    public StartMidLeftSwitchRightScale() {
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
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 300.0, 0.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 20.0, 90.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 500.0, 0.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 20.0, 90.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, -20.0, 90.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 500.0, 0.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 20.0, 90.0));
    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 20.0, 0.0));
    }
}
