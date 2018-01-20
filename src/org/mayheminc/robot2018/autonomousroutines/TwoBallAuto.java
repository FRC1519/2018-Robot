package org.mayheminc.robot2018.autonomousroutines;

import edu.wpi.first.wpilibj.command.*;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.*;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.Rotate.DesiredHeadingForm;
import org.mayheminc.robot2018.subsystems.Arm;

/**
 *
 */
public class TwoBallAuto extends CommandGroup {
    
    public  TwoBallAuto() {
        
    	
    	//dump pre-loaded boulder
    	
    	//claw down
    	addParallel(new CloseClaw());
    	//harvester on
    	addParallel(new HarvesterRollersForward());
    	//claw to pickup position
    	addParallel(new SetArmPosition(Robot.arm.FLOOR_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	//back away from midline
    	addSequential(new DriveStraight(0.25, DistanceUnits.INCHES, 0.1));
    	
    	addSequential(new Delay(1000));
    	
    	//approach midline boulder
    	//centering pistons together
    	addSequential(new SetCenteringPistons(Robot.claw.CENTERING_PISTONS_TOGETHER));
    	//rollers off
    	addSequential(new HarvesterRollersSlow());
    	
    	//tilt slightly towards low bar
//    	addSequential(new Rotate(350, DesiredHeadingForm.ABSOLUTE));
    	addSequential(new ArcingTurn(1.5, 0.5, -1.0));
    	addSequential(new DriveStraight(0.8, DistanceUnits.INCHES, 27.0));
    	addSequential(new Rotate(0, DesiredHeadingForm.ABSOLUTE));

    	addSequential(new DriveStraight(1.0, DistanceUnits.INCHES, 138.0));
    	addSequential(new SetShifter(Robot.drive.LOW_GEAR));
    	//we are now throught the low bar
    	//arm fire position
    	addParallel(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	//drive along gaurdrail until robot is next to gate 
    	

    	addSequential(new DriveStraight(1.0, DistanceUnits.INCHES, 24.0));
    	//coarse-align robot to tower
    	addSequential(new Rotate(40.0, DesiredHeadingForm.ABSOLUTE));
    	//auto-target and fire
    	addSequential(new AlignToTargetAndShootWithRewind(0.2, 1.0));
    	addSequential(new Delay(100));
    	//Heading of zero
    	addParallel(new CloseClaw());
    	addParallel(new SetArmPosition(Robot.arm.LOW_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	//go to zero heading, then go to zero heading again.  This needs to be perfect
    	addSequential(new Rotate(0, DesiredHeadingForm.ABSOLUTE));
    	
    	addParallel(new SetArmPosition(Robot.arm.FLOOR_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));

    	addSequential(new ArcingTurn(128.0, -0.8, 0.01));
    	addParallel(new HarvesterRollersForward());
    	addSequential(new DriveStraight(-1.0, DistanceUnits.INCHES, 20.0));
    	addParallel(new HarvesterRollersSlow());
    	addParallel(new SetCenteringPistons(Robot.claw.CENTERING_PISTONS_TOGETHER));
    	addSequential(new DriveStraight(0.9, DistanceUnits.INCHES, 133));
    	addParallel(new SetArmPosition(Robot.arm.FIRE_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new Rotate(40.0, DesiredHeadingForm.ABSOLUTE));
    	//auto-target and fire
    	addSequential(new AlignToTargetAndShoot(0.2));
    	
    }
}
