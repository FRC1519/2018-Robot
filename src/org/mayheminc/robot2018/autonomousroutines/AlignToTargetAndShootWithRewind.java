package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.RecordEncoderPosition;
import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight;
import org.mayheminc.robot2018.autonomousroutines.DriveStraight.DistanceUnits;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AlignToTargetAndShootWithRewind extends CommandGroup {

    public  AlignToTargetAndShootWithRewind(double targetSpeed, double rewindTargetSpeed) {
    	addSequential(new RecordEncoderPosition());
    	addSequential(new AlignToTargetAndShoot(targetSpeed));
    	addSequential(new AutoTargetRewind(-rewindTargetSpeed));
        
    }
}
