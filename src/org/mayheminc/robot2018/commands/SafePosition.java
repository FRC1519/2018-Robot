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

    	addSequential(new ElevatorSetPosition(Elevator.SWITCH_HEIGHT));
    	addSequential(new TurretMoveTo(Turret.RIGHT_REAR));
    	addSequential(new PivotToUpright());
    }
}
