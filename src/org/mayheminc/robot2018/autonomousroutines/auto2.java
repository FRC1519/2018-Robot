package org.mayheminc.robot2018.autonomousroutines;
import org.mayheminc.robot2018.commands.*;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.mayheminc.robot2018.subsystems.*;

/**
 *
 */
public class auto2 extends CommandGroup {

    public auto2() {
    	addSequential( new BackupTwoInchesAndHandoff() );
    }
}
