package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.subsystems.Elevator;
import org.mayheminc.robot2018.subsystems.Turret;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Safe Position is:
 * - Elevator at switch height.
 * - Turret backwards
 * - Pivot up
 */
public class SafePosition extends CommandGroup {

    public SafePosition() {
    	addParallel(new ElevatorSetPosition(Elevator.REST_NEAR_BOTTOM));
    	addSequential(new TurretMoveTo(Turret.FRONT_POSITION));
    	addSequential(new ElevatorSetPosition(Elevator.PICK_UP_CUBE));
    }
}
