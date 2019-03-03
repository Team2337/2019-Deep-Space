package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoLineSensorDrive extends Command {
	
	private boolean finished = false;
	/**
	 * @param timeout
	 * Amount of time the command runs for until it is forced to terminate, in seconds
	 */
	public autoLineSensorDrive() {

		requires(Robot.Chassis);
	}

	protected void initialize() {
		Robot.Chassis.print = true;
	}


	protected void execute() {
		/*
		 * Checks to see if the robot is on the line 
		 * If it's over the line, back up
		 * If it hasn't made it to the line yet, go forward
		 * If it sees the line, end the command
		 */
		if(Robot.Chassis.linesCrossed == 2) {
			//TODO: Fix Neo Arcade drive
			// Chassis.neoDrive.arcadeDrive(0.2, 0, false);
			Robot.Chassis.neoLeftFrontMotor.set(0.1);
			Robot.Chassis.neoRightFrontMotor.set(0.1);
		}
		if(Robot.Chassis.linesCrossed == 1) {
			//TODO: Fix Neo Arcade drive
			// Chassis.neoDrive.arcadeDrive(-0.2, 0, false);
			Robot.Chassis.neoLeftFrontMotor.set(-0.1);
			Robot.Chassis.neoRightFrontMotor.set(-0.1);
		}
		if(Robot.Chassis.linesCrossed == 0) {
			// Chassis.neoDrive.arcadeDrive(-0.2, 0, false);
			Robot.Chassis.neoLeftFrontMotor.set(-0.1);
			Robot.Chassis.neoRightFrontMotor.set(-0.1);
		}
		if(!Robot.Chassis.autoLineSensor.get()) {
			Robot.Chassis.encoderTicks = (int)Robot.Chassis.getLeftPosition();
			finished = true;
		}
	}


	protected boolean isFinished() {
		return finished;
	}

	protected void end() {
		Chassis.neoDrive.arcadeDrive(0, 0, false);
		Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
		Robot.Chassis.print = false;
	}


	protected void interrupted() {
		this.end();
	}
	
}