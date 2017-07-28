package org.usfirst.frc.team3082.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team3082.robot.RobotMap;
import org.usfirst.frc.team3082.robot.subsystems.SteerableWheel;
import org.usfirst.frc.team3082.robot.subsystems.DriveTrain;

public class Robot extends IterativeRobot {

	public static OI oi;
	
	// Drivetrain 
	private SteerableWheel backRightWheel;
	private SteerableWheel backLeftWheel;
	private SteerableWheel frontRightWheel;
	private SteerableWheel frontLeftWheel;
	private DriveTrain driveTrain;
	
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();
	

	@Override
	public void robotInit() {
		oi = new OI();
		
		backRightWheel = new SteerableWheel(RobotMap.backRightSpeedID, RobotMap.backRightAngleID, false);
		backLeftWheel = new SteerableWheel(RobotMap.backLeftSpeedID, RobotMap.backLeftAngleID, false);
		frontRightWheel = new SteerableWheel(RobotMap.frontRightSpeedID, RobotMap.frontRightAngleID, false);
		frontLeftWheel = new SteerableWheel(RobotMap.frontLeftSpeedID, RobotMap.frontLeftAngleID, false);
		driveTrain = new DriveTrain(backRightWheel, backLeftWheel, frontRightWheel, frontLeftWheel);
	}

	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		driveTrain.crabRobotOriented(oi.driver.getRawAxis(0), -1*oi.driver.getRawAxis(1)); // y on most joysticks is inverted
		Timer.delay(0.005);
	}  

	@Override
	public void testPeriodic() {
		// backRightWheel.write(oi.driver.getRawAxis(0), -1*oi.driver.getRawAxis(1));
	}
}
