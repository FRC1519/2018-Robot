package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
//import org.mayheminc.robot2018.subsystems.Intake;

public class AllRollersOut extends Command {

    public AllRollersOut() {
        super();
    }

    protected void initialize() {
    	Robot.intake.spitOutCube();
    	Robot.elevatorArms.setMotor(-1.0);
    }
    protected boolean isFinished() {return false;}
    protected void end()
    {
    	Robot.intake.stop();
    	Robot.elevatorArms.setMotor(0.0);
    }
    protected void interrupt()
    {
    	Robot.intake.stop();
    	Robot.elevatorArms.setMotor(0.0);
    }
}
