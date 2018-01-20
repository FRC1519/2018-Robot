package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDefenseRamparts extends CommandGroup {
    
    public  AutoDefenseRamparts() {
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
    	addParallel(new SetArmPosition(Robot.arm.UPRIGHT_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new DriveStraight(1.0, DistanceUnits.INCHES, 137.0));
    }
}
