package org.usfirst.frc.team3082.robot.subsystems;

import org.usfirst.frc.team3082.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

public class SteerableWheel {
	
	double steeringCountsPerRev = RobotMap.steeringCountsPerRev;
	public double steeringCountsPerDegree = steeringCountsPerRev / 360; 
	
	private CANTalon speedMotor;
	private CANTalon steerMotor;
	
	private double outputSpeed = 0;
	private double outputEncPos = 0;
	
	// Instantiation   
	public SteerableWheel(int speedMotorID, int steerMotorID, boolean isInverted) {
		// Initialize speed Talon SRX with the given ID
		this.speedMotor = new CANTalon(speedMotorID);
		speedMotor.changeControlMode(TalonControlMode.PercentVbus);
		speedMotor.setInverted(isInverted);
		
		// Initialize angle Talon SRX with the given ID 
		this.steerMotor = new CANTalon(steerMotorID);
		steerMotor.changeControlMode(TalonControlMode.Position); // setup position control
		steerMotor.setFeedbackDevice(FeedbackDevice.QuadEncoder); 
		steerMotor.reverseSensor(true);
		steerMotor.setPID(RobotMap.steeringP, RobotMap.steeringI, RobotMap.steeringD);
		steerMotor.enableControl();
	}
	
	public void write(double speed, double angle) {
		outputSpeed = speed;
		if (speed > 0.05) { // doesn't zero each time throttle is released
			//double outputEncPos = findNearestSetpoint(steerMotor.getEncPosition(), angle);
			outputEncPos = getBasicSetpoint(angle);
		}
		setMotors();
	}
	
	private double getBasicSetpoint(double newAngle) {
		double setpoint = newAngle/Math.PI * (RobotMap.steeringCountsPerRev/2);
		return setpoint;
	}
	
	private double findNearestSetpoint(double currentEncPos, double newAngle) {
		double setpoint = 0;
		return setpoint;
	}
	
	
	private void setMotors() {
		speedMotor.set(outputSpeed);
		steerMotor.set(outputEncPos);
	}
}
