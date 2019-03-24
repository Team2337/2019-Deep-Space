package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Waits a given amount of time until the next command is run
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoTankDrive extends Command {
	double rightSpeed, leftSpeed, rightDist, leftDist, ta;
	String side;
	IdleMode brakeMode;

	/**
	 * 
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param leftDist
	 * @param rightDist
	 * @param side
	 */
	public autoTankDrive(double leftSpeed, double rightSpeed, double leftDist, double rightDist, String side, IdleMode brakeMode) {
		this.rightSpeed = rightSpeed;
		this.leftSpeed = leftSpeed;
		this.rightDist = rightDist;
		this.leftDist = leftDist;
		this.side = side;
		this.brakeMode = brakeMode;
		requires(Robot.Chassis);
	}

	protected void initialize() {
		
	}

	protected void execute() {
		ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
		Chassis.neoTank(leftSpeed, rightSpeed, false);
	}

	protected boolean isFinished() {
		switch(side) {
			case "right":
				if(Math.abs(Robot.Chassis.getRightPosition()) >= Math.abs(rightDist)) return true;
			break;
			case "left":
				if(Math.abs(Robot.Chassis.getLeftPosition()) >= Math.abs(leftDist)) return true;
			break;
			case "rightVision":
				if(Math.abs(Robot.Chassis.getRightPosition()) >= Math.abs(rightDist) || ta > 0) return true;
			break;
			case "leftVision":
				if(Math.abs(Robot.Chassis.getLeftPosition()) >= Math.abs(leftDist) || ta > 0) return true;
			break;
		}
		return false;
	}

	protected void end() {
		Robot.Chassis.setAllNeoBrakeMode(brakeMode);
		Chassis.neoTank(0, 0, false);
	}

	protected void interrupted() {
		this.end();
	}

}