package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.Rotate.DesiredHeadingForm;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class CrossLowBarAndScoreHigh extends CommandGroup {
    
    public CrossLowBarAndScoreHigh() {
    	addParallel(new CloseClaw());
    	addParallel(new SetCenteringPistons(Robot.claw.CENTERING_PISTONS_TOGETHER));
    	addParallel(new SetArmPosition(Robot.arm.FLOOR_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));   	
    	
    	// drive up to the outer works
    	addSequential(new ApproachOuterWorks());   	
    	
    	// drive through the low bar to have the robot be 1 foot beyond the outer works
    	addSequential(new DriveStraight(0.70, DistanceUnits.INCHES, 95.0));
    	
    	// should now be well through the low bar, okay to raise the arm
    	addParallel(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new ArcingTurn(40, 0.5, 0.15));
    	addSequential(new Rotate(45.0, DesiredHeadingForm.ABSOLUTE));
    	
    	addSequential(new AlignToTargetAndShootWithRewind(0.12, 0.5));
    }
   
}
