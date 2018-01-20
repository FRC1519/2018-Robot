package org.mayheminc.robot2018.autonomousroutines;
import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossDefenseChevalDeFrise extends CommandGroup {
    
    public  CrossDefenseChevalDeFrise(boolean arg_requireArm) {
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
    	addSequential(new SetShifter(Robot.drive.LOW_GEAR));
    	addSequential(new SetArmPosition(Robot.arm.FLOOR_POSITION_COUNT, arg_requireArm));
    	// now the CDF teeter totters are on the floor so we can drive up them
    	addSequential(new Delay(50));
    	addSequential(new DriveStraight(-0.60, DistanceUnits.INCHES, 8));
    	addParallel(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, arg_requireArm));
    	addSequential(new DriveStraight(-0.80, DistanceUnits.INCHES, 79));  // was 71 before parallelizing arm movement
    	
    }
}
