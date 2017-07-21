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
	
	private int outputSpeed;
	private int outputEncPos;
	
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
		steerMotor.setPID(RobotMap.steeringP, RobotMap.steeringI, RobotMap.steeringD);
		steerMotor.enableControl();
	}
	
	private void write(double speed, double angle) {
		
	}

	private double findNearestSetpoint(double currentAngle, double newAngle){
		double setpoint = 0;
	}
	
	private void setMotors() {
		speedMotor.set(outputSpeed);
		steerMotor.set(outputEncPos);
	}
}
