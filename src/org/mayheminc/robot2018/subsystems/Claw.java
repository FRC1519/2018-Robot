package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.mayheminc.robot2018.RobotMap;

//import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
//import edu.wpi.first.wpilibj.CANTalon.TalonControlMode;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;
/**
 *
 */
public class Claw extends Subsystem {
    public static double HARVESTER_SPEED_IN = 0.5;
    public static double HARVESTER_SPEED_OUT = -0.5;
    public static double HARVESTER_OFF = 0.0;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static MayhemTalonSRX rollerTalon = new MayhemTalonSRX(RobotMap.ROLLER_TALON);
	private Solenoid centeringPistons;
	private Solenoid clawPistons;
	
	public Claw() {
		
		rollerTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		rollerTalon.configPeakOutputVoltage(+12.0, -12.0);
		
		rollerTalon.changeControlMode(ControlMode.PercentOutput);
		rollerTalon.enableControl();
		
		centeringPistons = new Solenoid(RobotMap.CENTERING_PISTONS_SOLENOID);
		clawPistons = new Solenoid(RobotMap.CLAW_SOLENOID);
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setHarvester(double speed){
    	rollerTalon.set((int) speed);
    }
    
    public void slowHarvesterRollers(){
    	setHarvester(0.10);
    }

    public void stopHarvesterRollers(){
    	setHarvester(0.0);
    }
    
    public static final boolean CENTERING_PISTONS_TOGETHER = true;
    public static final boolean CENTERING_PISTONS_APART	= false;
    public void setCenteringPistons(boolean position){
    	centeringPistons.set(position);
    }
    
    public static final boolean CLAW_CLOSED = true;
    public static final boolean CLAW_OPEN = false;
    public void setClawPosition(boolean position){
    	clawPistons.set(position);
    }
    
    public boolean isClawOpen() {
    	return (clawPistons.get() == CLAW_OPEN);
    }
    
}