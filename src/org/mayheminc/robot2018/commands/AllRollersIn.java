package org.mayheminc.robot2018.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.mayheminc.robot2018.Robot;
//import org.mayheminc.robot2018.subsystems.Intake;

public class AllRollersIn extends Command {

    public AllRollersIn() {
        super();
    }

    protected void initialize() {
    	Robot.intake.takeInCube();
    	Robot.elevatorArms.setMotor(0.5);
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
