package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartMidLeftSwitchRightScale extends CommandGroup {

    public StartMidLeftSwitchRightScale() {
    	addSequential(new StartMidLeftSwitch());
    }
}
