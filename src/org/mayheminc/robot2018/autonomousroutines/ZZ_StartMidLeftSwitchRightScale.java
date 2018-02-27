package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ZZ_StartMidLeftSwitchRightScale extends CommandGroup {

    public ZZ_StartMidLeftSwitchRightScale() {
    	addSequential(new ZZ_StartMidLeftSwitch());
    }
}
