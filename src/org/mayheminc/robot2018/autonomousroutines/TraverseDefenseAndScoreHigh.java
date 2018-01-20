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
public class TraverseDefenseAndScoreHigh extends CommandGroup {
	
    public  TraverseDefenseAndScoreHigh() {
    	
    	addSequential(new TraverseDefense());

    	addParallel(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	
    	addSequential(new LineUpForShotAndShoot());
    	
    	// RJD Rotate back to original 0 heading, put the arm back to upright, and back up.
    	addParallel(new SetArmPosition(Robot.arm.UPRIGHT_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new Rotate(0,  DesiredHeadingForm.ABSOLUTE));

    }
}
