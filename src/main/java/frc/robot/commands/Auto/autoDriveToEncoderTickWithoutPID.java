package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Drives the robot to a distance in encoder ticks
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoDriveToEncoderTickWithoutPID extends Command {
    
    int forwardDistance;
    int inchesToTicks = 686;
    int direction;
    double maxDriveSpeed = 0;
    
    //max speed forward and reverse
    // double maxSpeed = 0.5;

    /**
     * Drives the robot to a distance in encoder ticks
     * The command ends once the robot is within 100 ticks of the setpoint
     * @param direction - use positive or negative value to deterimine the direction of the drive (positive forward, negative backwards)
     * @param forwardDistance - forward/reverse distance in inches - will convert to ticks
     */
	public autoDriveToEncoderTickWithoutPID(int direction, int forwardDistance, double maxDriveSpeed) {
        this.forwardDistance = (forwardDistance * inchesToTicks);
        this.maxDriveSpeed = maxDriveSpeed;
		requires(Robot.Chassis);
	}

	protected void initialize() {
        Robot.Chassis.resetEncoders();
        Robot.Pigeon.resetPidgey();
    }

	protected void execute() {
        Chassis.neoArcade(maxDriveSpeed, 0, false);
	}


	protected boolean isFinished() {
        return Math.abs(Robot.Chassis.getAverageEncoderPosition()) > Math.abs(forwardDistance);
	}

	protected void end() {
	}


	protected void interrupted() {
		this.end();
	}
	
}