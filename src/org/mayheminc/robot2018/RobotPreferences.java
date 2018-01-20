package org.mayheminc.robot2018;

import edu.wpi.first.wpilibj.Preferences;

/**
 * @author Team1519
 */
public class RobotPreferences {
    
    // PIDF Settings for Drive Wheel control loops in TalonSRX
	
    static public void putWheelP(double p) {
        Preferences.getInstance().putDouble("WheelP", p);
    }
    static public void putWheelI(double i) {
        Preferences.getInstance().putDouble("WheelI", i);
    }
    static public void putWheelD(double d) {
        Preferences.getInstance().putDouble("WheelD", d);
    }
    static public void putWheelF(double f) {
        Preferences.getInstance().putDouble("WheelF", f);
    }    
    static public double getWheelP() {
        return Preferences.getInstance().getDouble("WheelP", 1.6);
    }
    static public double getWheelI() {
        return Preferences.getInstance().getDouble("WheelI", 0.0);
    }
    static public double getWheelD() {
        return Preferences.getInstance().getDouble("WheelD", 0.0);
    }
    static public double getWheelF() {
        return Preferences.getInstance().getDouble("WheelF", 1.8);
    }
    
    
    
    static public void putArmP(double p) {
        Preferences.getInstance().putDouble("ArmP", p);
    }
    static public void putArmI(double i) {
        Preferences.getInstance().putDouble("ArmI", i);
    }
    static public void putArmD(double d) {
        Preferences.getInstance().putDouble("ArmD", d);
    }
    static public void putArmF(double f) {
        Preferences.getInstance().putDouble("ArmF", f);
    }
    
    static public double getArmP() {
        return Preferences.getInstance().getDouble("ArmP", 4.8);    
    }
    static public double getArmI() {
        return Preferences.getInstance().getDouble("ArmI", 0.0);
    }
    static public double getArmD() {
        return Preferences.getInstance().getDouble("ArmD", 240.0);
    }
    static public double getArmF() {
        return Preferences.getInstance().getDouble("ArmF", 0.0);
    }
    
    
    static public void putLeftIRValue(double val) {
        Preferences.getInstance().putDouble("LeftIRValue", val);
    }
    static public void putRightIRValue(double val) {
        Preferences.getInstance().putDouble("RightIRValue", val);
    }   
    static public double getLeftIRValue() {
        return Preferences.getInstance().getDouble("LeftIRValue", 2.0);   
    }
    static public double getRightIRValue() {
        return Preferences.getInstance().getDouble("RightIRValue", 2.0);
    }
    static public double getIRThreshold(int IRChannel){
    	switch(IRChannel){
    	case 1: 
    		return getLeftIRValue();
    	case 2: 
			return getRightIRValue();
    	}
    	return 2.0;
    }
}
