package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;
import org.mayheminc.robot2018.commands.SetArmPosition;
import org.mayheminc.robot2018.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDefensePortcullis extends CommandGroup {
    
    public  AutoDefensePortcullis() {
    	addSequential(new DeployLifter(Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    	addSequential(new DriveStraight(0.70, DistanceUnits.INCHES, 108.0));
    	addSequential(new RetractLifter(Arm.DONT_REQUIRE_ARM_SUBSYSTEM));
    }
}
