package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.Rotate;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightRightSwitchLeftScale extends CommandGroup {

    public StartRightRightSwitchLeftScale() {
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
    	addSequential(new StartRightRightSwitch());

    	addSequential(new DriveStraightOnHeading(1.0, DistanceUnits.INCHES, 80.0, 0.0));
    	addSequential(new Rotate(-120, Rotate.DesiredHeadingForm.ABSOLUTE, 5.0));
    	addSequential(new DriveStraightOnHeading(0.75, DistanceUnits.INCHES, 20.0, -120.0));
    	addSequential(new Rotate(-90, Rotate.DesiredHeadingForm.ABSOLUTE, 5.0));
    	addSequential(new DriveStraightOnHeading(0.75, DistanceUnits.INCHES, 100, -90.0));
    }
}
