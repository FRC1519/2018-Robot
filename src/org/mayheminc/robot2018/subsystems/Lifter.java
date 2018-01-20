package org.mayheminc.robot2018.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.mayheminc.robot2018.RobotMap;
import org.mayheminc.util.MayhemTalonSRX;

import com.ctre.phoenix.motorcontrol.ControlMode;

/**
 *
 */
public class Lifter extends Subsystem {
    public static double LIFTER_POWER_RETRACT = -0.6;
    public static double LIFTER_POWER_DEPLOY = 0.6;
    public static double LIFTER_OFF = 0.0;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private static MayhemTalonSRX m_lifterTalon = new MayhemTalonSRX(RobotMap.LIFTER_TALON);
	
	public Lifter() {
		m_lifterTalon.configNominalOutputVoltage(+0.0f, -0.0f);
		m_lifterTalon.configPeakOutputVoltage(+12.0, -12.0);
		
		m_lifterTalon.changeControlMode(ControlMode.PercentOutput);
		m_lifterTalon.enableControl();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setLifterPower(double power){
    	m_lifterTalon.set((int) power);
    }

    public void stopLifter(){
    	setLifterPower(0.0);
    }    
    
}