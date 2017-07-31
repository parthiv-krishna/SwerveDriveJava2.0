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
		speedMotor.setVoltageRampRate(RobotMap.speedRampRate);
		speedMotor.setCurrentLimit(RobotMap.speedCurrentLimit);
		
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
			//outputEncPos = findNearestSetpoint(steerMotor.getEncPosition(), Math.PI);
			outputEncPos = getBasicSetpoint(angle);
		}
		setMotors();
	}
	
	public void test(double vX, double vY) {
		double speed = Math.hypot(vY, vX);
		double angle = Math.atan2(vY, vX);
		write(speed, angle);
	}
	
	private double getBasicSetpoint(double newAngle) {
		double setpoint = newAngle/Math.PI * (RobotMap.steeringCountsPerRev/2);
		return setpoint;
	}
	
	private double findNearestSetpoint(double currentEncPos, double newAngle) {
		
		/* Setpoint calculation with relative encoders (partially for anyone reading this but mostly for my own sanity)
		 * 
		 * 1. Convert the target angle to a number of counts above a complete rotation.
		 * 
		 * 2. Get the current position of the wheel, in complete rotations (numCompleteRotations) + extra counts (currentCounts). 
		 * 	   
		 * 3. Find all possible target positions (there are three). Those would be, along with examples where they would be correct:
		 *    a) The previous complete rotation + targetCounts. (potentialTarget1)
		 *       Example: Current angle pi/10 rad, target 19pi/10 rad (change of -pi/5 rad). 
		 *	  b) The current complete rotation + targetCounts. (potentialTarget2)
		 *       Example: Current angle pi/10 rad, target 3pi/10 rad (change of +pi/5 rad).
		 *    c) The next complete rotation + targetCounts. (potentialTarget3)
		 *    	 Example: Current angle 19pi/10 rad, target pi/10 rad (change of +pi/5 rad).
		 *    Note that a) results in a decrease in the angle, b) could be an increase or decrease, and c) results in an increase.      
		 *    
		 * 4. Calculate the distance to each potential target. 
		 * 
		 * 5. Find the nearest potential target and command the angleMotor Talon SRX to go to that position. 
		 * 
		 * TODO: If the opposite angle to the target is closer, go there and run the motor backwards. 
		 *       Such as if current angle is pi/4 and target is pi@0.50, go to 0@-.50 as it is equivalent but closer.
		 */   

		// Step 1. 
		double targetCounts = newAngle / Math.PI * (RobotMap.steeringCountsPerRev/2);

		// Step 2.
		int numCompleteRevs = (int) (currentEncPos / steeringCountsPerRev);
		double currentCounts = currentEncPos % steeringCountsPerRev;
		
		// Step 3.
		double potentialSetpoint1 = ((numCompleteRevs - 1) * steeringCountsPerRev) + targetCounts;
		double potentialSetpoint2 = ((numCompleteRevs) * steeringCountsPerRev) + targetCounts;
		double potentialSetpoint3 = ((numCompleteRevs + 1) * steeringCountsPerRev) + targetCounts;
		
		// Step 4.
		double distanceToPS1 = Math.abs(currentEncPos - potentialSetpoint1);
		double distanceToPS2 = Math.abs(currentEncPos - potentialSetpoint2);
		double distanceToPS3 = Math.abs(currentEncPos - potentialSetpoint3);
		
		// Step 5. 
		double setpoint = 0;
		if (distanceToPS1 <= distanceToPS2 && distanceToPS1 <= distanceToPS3) {
			setpoint = potentialSetpoint1;
		}
		else if (distanceToPS2 <= distanceToPS1 && distanceToPS2 <= distanceToPS3) {
			setpoint = potentialSetpoint2;
		}
		else if (distanceToPS3 <= distanceToPS1 && distanceToPS3 <= distanceToPS2) {
			setpoint = potentialSetpoint3;
		}
		return setpoint;
	}
	
	
	private void setMotors() {
		speedMotor.set(outputSpeed);
		steerMotor.set(outputEncPos);
	}
}
