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
	double rightSpeed, leftSpeed, rightDist, leftDist, ta, angle;
	String side;
	IdleMode brakeMode;

	/**
	 * 
	 * @param leftSpeed  - speed of the left side of the chassis (value between -1 &
	 *                   1)
	 * @param rightSpeed - speed of the right side of the chassis (value between -1
	 *                   & 1)
	 * @param leftDist   - distance in encoder ticks the left side will drive to
	 * @param rightDist  - distance in encoder ticks the right side will drive to
	 * @param side       - String value determining which side of the chassis to
	 *                   read to end the command ("right" or "left")
	 */
	public autoTankDriveWithGyro(double angle, double leftSpeed, double rightDist, String side,
			IdleMode brakeMode) {
		this.angle = angle;
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
		return ((Math.abs(Robot.Pigeon.getYaw()) >= angle - 10) && (Math.abs(Robot.Pigeon.getYaw()) <= angle + 10));
	}

	protected void end() {
		Robot.Chassis.setAllNeoBrakeMode(brakeMode);
		Chassis.neoTank(0, 0, false);
	}

	protected void interrupted() {
		this.end();
	}

}