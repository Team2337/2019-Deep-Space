package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

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
		if(Robot.Chassis.linesCrossed > 0) {
			// Robot.Chassis.neoArcade(0.2, 0, false);
			Robot.Chassis.neoLeftFrontMotor.set(-0.1);
			Robot.Chassis.neoRightFrontMotor.set(-0.1);
		}
		if(Robot.Chassis.linesCrossed == 0) {
			// Robot.Chassis.neoArcade(-0.2, 0, false);
			Robot.Chassis.neoLeftFrontMotor.set(0.1);
			Robot.Chassis.neoRightFrontMotor.set(0.1);
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
		Robot.Chassis.neoLeftFrontMotor.set(0);
		Robot.Chassis.neoRightFrontMotor.set(0);
		// Robot.Chassis.setSetPoint(0);
		Robot.Chassis.setAllNeoBrakeMode(IdleMode.kBrake);
		Robot.Chassis.print = false;
	}


	protected void interrupted() {
		this.end();
	}
	
}