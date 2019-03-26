package frc.robot.commands.Auto;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Drives the robot to a distance in encoder ticks using a PID
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoDriveToEncoderTickPID extends PIDCommand {
    
    int forwardDistance;
    int inchesToTicks = 686;
    int direction;
    
    //max speed forward and reverse
    // double maxSpeed = 0.5;

    /**
     * Drives the robot to a distance in encoder ticks
     * The command ends once the robot is within 100 ticks of the setpoint
     * @param direction - use positive or negative value to deterimine the direction of the drive (positive forward, negative backwards)
     * @param forwardDistance - forward/reverse distance in inches - will convert to ticks
     */
	public autoDriveToEncoderTickPID(int direction, int forwardDistance, double maxDriveSpeed) {
        super(0.025, 0, 0);
        getPIDController().setAbsoluteTolerance(0.1);
        getPIDController().setInputRange(-maxDriveSpeed, maxDriveSpeed);
        // this.maxSpeed = maxDriveSpeed;
        this.forwardDistance = (forwardDistance * inchesToTicks);
        this.direction = (direction/Math.abs(direction));
		requires(Robot.Chassis);
	}

	protected void initialize() {
        Robot.Chassis.resetEncoders();
        Robot.Pigeon.resetPidgey();
        this.setSetpoint(forwardDistance * direction);
    }
    
    @Override
    protected double returnPIDInput() {
        return Robot.Chassis.getAverageEncoderPosition();
    }

    @Override
    protected void usePIDOutput(double output) {
        /*
        if(output > maxSpeed) {
            output = maxSpeed;
        }

        if(output < -maxSpeed) {
            output = -maxSpeed;
        }
        */

        Chassis.neoArcade(output, 0, false);
    }


	protected void execute() {

	}


	protected boolean isFinished() {
        return Math.abs(Robot.Chassis.getAverageEncoderPosition()) > Math.abs(Robot.Chassis.getAverageEncoderPosition()) - 100;
	}

	protected void end() {
        
	}


	protected void interrupted() {
		this.end();
	}
	
}