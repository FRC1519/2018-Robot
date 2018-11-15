package org.mayheminc.robot2018.autonomousroutines;

import org.mayheminc.robot2018.subsystems.Autonomous;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MultiSwitchO extends CommandGroup{

	public MultiSwitchO(Autonomous.StartOn startSide)
	{
		// drive by backwards, spit cube, drive past.
		addSequential(new ScoreOnNearScaleAndGetNextCube(startSide));
	}
}
