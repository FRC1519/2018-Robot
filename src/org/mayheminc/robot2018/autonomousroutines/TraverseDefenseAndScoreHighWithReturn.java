package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.SetArmPosition;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TraverseDefenseAndScoreHighWithReturn extends CommandGroup {
    
    public  TraverseDefenseAndScoreHighWithReturn() {
       addSequential(new TraverseDefenseAndScoreHigh());
       addSequential(new DriveStraight(-0.80, DistanceUnits.INCHES, 100));
       addParallel(new SetArmPosition(Robot.arm.FLOOR_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
       addSequential(new DriveStraight(-0.80, DistanceUnits.INCHES, 14));
    }
}
