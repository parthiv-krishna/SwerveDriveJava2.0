package org.usfirst.frc.team3082.robot.subsystems;

import org.usfirst.frc.team3082.robot.RobotMap;
import org.usfirst.frc.team3082.robot.subsystems.SteerableWheel;

public class DriveTrain {
	public final double Length = RobotMap.wheelbaseLength;
	public final double Width = RobotMap.wheelbaseWidth;
	
	private SteerableWheel backRightSW;
	private SteerableWheel backLeftSW;
	private SteerableWheel frontRightSW;
	private SteerableWheel frontLeftSW;
	
	public DriveTrain(SteerableWheel backRight, SteerableWheel backLeft, SteerableWheel frontRight, SteerableWheel frontLeft) {
	    this.backRightSW = backRight;
	    this.backLeftSW = backLeft;
	    this.frontRightSW = frontRight;
	    this.frontLeftSW = frontLeft;
	}
	
	public void crabRobotOriented(double vX, double vY) {
		double speed = Math.hypot(vY, vX);
		double angle = Math.atan2(vY, vX);
		if (speed > 1) {
			speed = 1;
		}
		System.out.print("speed: ");
		System.out.print(speed);
		System.out.print("angle: ");
		System.out.println(angle);
		backRightSW.write(speed, angle);
		backLeftSW.write(speed, angle);
		frontRightSW.write(speed, angle);
		frontLeftSW.write(speed, angle);
	}
	
	public void swerveRobotOriented(double vX, double vY, double rot) {
		double radius = Math.hypot(Length, Width);
	    
	    // Define these variables to make the program cleaner later on
	    double a = vX - rot * (Length / radius); 
	    double b = vX + rot * (Length / radius);
	    double c = vY - rot * (Width / radius); 
	    double d = vY + rot * (Width / radius);
	    
	    // Calculate wheel speeds
	    double backRightSpeed = Math.hypot(a,d);
	    double backLeftSpeed = Math.hypot(a,c);
	    double frontRightSpeed = Math.hypot(b,d);
	    double frontLeftSpeed = Math.hypot(b,c);
	    
	    // Normalize speeds in case one or more is too large (greater than 1 or less than -1) in order to preserve speed ratios. 
	    double maxBackWheelSpeed = Math.max(backRightSpeed, backLeftSpeed); // 
	    double maxFrontWheelSpeed = Math.max(frontRightSpeed, frontLeftSpeed); 
	    double maxWheelSpeed = Math.max(maxBackWheelSpeed, maxFrontWheelSpeed); 
	    if (maxWheelSpeed > 1.0) {
	    	backRightSpeed /= maxWheelSpeed;
	    	backLeftSpeed /= maxWheelSpeed;
	    	frontRightSpeed /= maxWheelSpeed;
	    	frontLeftSpeed /= maxWheelSpeed;
	    }
	    
	    // Calculate steering angles
	    double backRightAngle = Math.atan2(a, d);
	    double backLeftAngle = Math.atan2(a, c);
	    double frontRightAngle = Math.atan2(b, d);
	    double frontLeftAngle = Math.atan2(b, c);
	 
	    backRightSW.write(backRightSpeed, backRightAngle);
	    backLeftSW.write(backLeftSpeed, backLeftAngle);
	    frontRightSW.write(frontRightSpeed, frontRightAngle);
	    frontLeftSW.write(frontLeftSpeed, frontLeftAngle);
	}
}