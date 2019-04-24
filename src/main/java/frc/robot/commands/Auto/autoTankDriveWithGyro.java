package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Drives the robot using tank drive to a specified encoder tick
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoTankDriveWithGyro extends Command {
	double rightSpeed, leftSpeed, rightDist, leftDist, ta, angle, initialAngle;
	String side;
	IdleMode brakeMode;

	/**
	 * 
	 * @param angle
	 * @param leftSpeed
	 * @param rightSpeed
	 * @param side
	 * @param brakeMode
	 */
	public autoTankDriveWithGyro(double angle, double leftSpeed, double rightSpeed, String side, IdleMode brakeMode) {
		this.angle = angle;
		this.rightSpeed = rightSpeed;
		this.leftSpeed = leftSpeed;
		this.side = side;
		this.brakeMode = brakeMode;
		requires(Robot.Chassis);
	}

	protected void initialize() {
		initialAngle = Robot.Pigeon.getYaw();
	}

	protected void execute() {
		ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
		Chassis.neoTank(leftSpeed, rightSpeed, false);
	}

	protected boolean isFinished() {
		if(initialAngle > 0) {
			return ((Robot.Pigeon.getYaw() >= angle + 5) && (Robot.Pigeon.getYaw() <= angle - 5) || ta > 0);
		} 
		return ((Robot.Pigeon.getYaw() >= angle - 5) && (Robot.Pigeon.getYaw() <= angle + 5) || ta > 0);
	}

	protected void end() {
		Robot.Chassis.setAllNeoBrakeMode(brakeMode);
		Chassis.neoTank(0, 0, false);
	}

	protected void interrupted() {
		this.end();
	}

}