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
		
	}
	
}