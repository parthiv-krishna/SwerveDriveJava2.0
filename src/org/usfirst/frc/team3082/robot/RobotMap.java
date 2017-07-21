package org.usfirst.frc.team3082.robot;

public class RobotMap {
	// Debug Mode
	public static int debugMode = 0; // 0 = none. 1 = limited. 2 = everything.
	
	// USB ports for joysticks
	public static int driverPort = 0;
	
	// Wheelbase Dimensions
	public static double wheelbaseLength = 18;
	public static double wheelbaseWidth = 18;
	
	// Drivetrain Talon SRX IDs
	public static int backRightSpeedID = 1;
	public static int backRightAngleID = 2;
	public static int backLeftSpeedID = 3;
	public static int backLeftAngleID = 4;
	public static int frontRightSpeedID = 5;
	public static int frontRightAngleID = 6;
	public static int frontLeftSpeedID = 7;
	public static int frontLeftAngleID = 8;
	
	// Steering tuning constants 
	public static double steeringCountsPerRev = 415; // approximation. True value is 7 * 71.165 / 1.2 = 415.129
	public static double steeringP = 1;
	public static double steeringI = 0.0;
	public static double steeringD = 0.0;
}
