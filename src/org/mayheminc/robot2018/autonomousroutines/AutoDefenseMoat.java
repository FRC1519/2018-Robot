package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDefenseMoat extends CommandGroup {
    
    public  AutoDefenseMoat() {
        
    	addParallel(new SetArmPosition(Robot.arm.UPRIGHT_POSITION_COUNT, Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new DriveStraight(1.0, DistanceUnits.INCHES, 123.0));
    }
}
