package org.mayheminc.robot2018.commands;

import org.mayheminc.robot2018.Robot;
import org.mayheminc.robot2018.subsystems.Pivot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PivotTest extends CommandGroup {

    public PivotTest() {
    	if( Robot.pivot.getPosition() > 1400 )
    	{
    		addSequential(new PivotMove(Pivot.DOWNWARD_POSITION));
    	}
    	else
    	{
    		addSequential(new PivotMove(Pivot.UPRIGHT_POSITION));
    	}
    }
}
