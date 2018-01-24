package org.mayheminc.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;

public class MayhemTalonSRX extends TalonSRX{

	ControlMode controlMode;
	
	public MayhemTalonSRX(int deviceNumber) {
		super(deviceNumber);
		// TODO Auto-generated constructor stub
	}

	public void changeControlMode(ControlMode mode)
	{
		controlMode = mode;
	}

	public void set(int deviceID) {
		// TODO Auto-generated method stub
		this.set(controlMode, deviceID);
	}
	
	public void setFeedbackDevice(FeedbackDevice feedback)
	{
		this.configSelectedFeedbackSensor(feedback, 0, 1000);
	}

	public void reverseSensor(boolean b) {
		// TODO Auto-generated method stub
		
	}

	public void configNominalOutputVoltage(float f, float g) {
		// TODO Auto-generated method stub

		this.configNominalOutputForward(f/12.0,  1000);
		this.configNominalOutputReverse(g/12.0,  1000);
	}

	public void configPeakOutputVoltage(double d, double e) {
		// TODO Auto-generated method stub
		this.configPeakOutputForward(d/12.0 * 100.0,  1000);
		this.configPeakOutputReverse(e/12.0 * 100.0, 1000);

	}

	public void setPID(double wheelP, double wheelI, double wheelD, double wheelF, int i, double m_voltageRampRate,
			int j) {
		// TODO Auto-generated method stub
		this.config_kP(0, wheelP ,  1000);
		this.config_kI(0, wheelI ,  1000);
		this.config_kD(0, wheelD ,  1000);
		this.config_kF(0, wheelF ,  1000);
		
	}

	public double getSetpoint() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double getError() {
		// TODO Auto-generated method stub
		return this.getClosedLoopError(0);
	}

	public float getOutputVoltage() {
		// TODO Auto-generated method stub
		return (float) this.getMotorOutputVoltage();
	}

	public void setProfile(int downPidSlot) {
		// TODO Auto-generated method stub
		//this.pro
		
	}

	public void setPID(double pDown, double iDown, double dDown) {
		// TODO Auto-generated method stub
	 setPID(pDown, iDown, dDown, 0.0, 0, 0.0, 0);	
	}

	public void setVoltageRampRate(double d) {
		// TODO Auto-generated method stub
		
		// Need to convert volts per second to time
		this.configClosedloopRamp(1.0,  1000);
	
	}

	public void enableControl() {
		// TODO Auto-generated method stub
		
	}

	public void setPosition(int zeroPositionCount) {
		// TODO Auto-generated method stub
		this.setSelectedSensorPosition(zeroPositionCount,  0,  1000);
	}

	public double getPosition() {
		// TODO Auto-generated method stub
		return this.getSelectedSensorPosition(0);
	}

	public double getSpeed() {
		// TODO Auto-generated method stub
		return this.getSelectedSensorVelocity(0);
	}

	public void setEncPosition(int i) {
		// TODO Auto-generated method stub
		setPosition(i);
	}

	public double get() {
		// TODO Auto-generated method stub
		return this.getOutputCurrent();
	}
}