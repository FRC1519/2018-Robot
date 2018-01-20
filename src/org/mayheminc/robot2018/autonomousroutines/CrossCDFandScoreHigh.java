package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.*;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.Rotate.DesiredHeadingForm;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossCDFandScoreHigh extends CommandGroup {
	
    public CrossCDFandScoreHigh() {
    	
    	addSequential(new SetHeadingOffset180());
    	
    	addParallel(new CloseClaw());
    	addParallel(new SetCenteringPistons(Robot.claw.CENTERING_PISTONS_TOGETHER));
    	addParallel(new SetArmPosition(Robot.arm.UPRIGHT_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	
    	// back up to outer works
    	addSequential(new DriveStraight(-0.75, DistanceUnits.INCHES, 43));  
    	
    	addSequential(new CrossDefenseChevalDeFrise(Arm.DONT_REQUIRE_ARM_SUBSYSTEM));

    	addSequential(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));    // Note:  TO-DO - may want command to wait until in position before finishing	
    	    	
    	addSequential(new LineUpForShotAndShoot());
    	
    	addSequential(new DriveStraight(-0.25, DistanceUnits.INCHES, 6));
    }
}
