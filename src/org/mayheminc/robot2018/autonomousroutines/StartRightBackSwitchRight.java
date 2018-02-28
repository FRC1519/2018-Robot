package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.commands.SetHeadingOffset180;
import org.mayheminc.robot2018.commands.ZeroGyro;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading;
import org.mayheminc.robot2018.commands.TurretMoveTo;
import org.mayheminc.robot2018.commands.DriveStraightOnHeading.DistanceUnits;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotor;
import org.mayheminc.robot2018.commands.ElevatorArmSetMotorAuto;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartRightBackSwitchRight extends CommandGroup {

    public StartRightBackSwitchRight() {
    	addSequential( new ZeroGyro() );
    	addSequential(new SetHeadingOffset180());
    	
    	// start the turret to shift to the right
//    	addSequential(new TurretMoveTo(Turret.RIGHT_POSITION));
    	// drive backwards, angling towards the switch
    	addSequential(new DriveStraightOnHeading(-0.5, DistanceUnits.INCHES, 200.0, 170.0));
    	// spit the cube while we are driving
    	addParallel(new ElevatorArmSetMotor(0.4));
    }
}
