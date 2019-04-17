package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import org.opencv.features2d.FastFeatureDetector;

import edu.wpi.first.wpilibj.command.Command;

import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Drives forward/reverse at the given speed to a given encoder tick target
 * (reset to 0) for distance and gyro to drive straight. Can also inout a
 * timeout, otherwise it will default to 5 seconds.
 */
public class autoDriveWithPitch extends Command {

    public double Kp = .05;
    public double yaw;
    public double pitch;
    public int m_target;
    public double m_speed;
    public double m_timeout;
    public double m_rollAngle;
    public boolean level = false;
    public boolean above3Degrees = false;
    public double initialRoll = 0;

    /**
     * Drive until Timeout or pitch increases
     * 
     * @param speed
     * @param timeout
     * @param rollAngle
     */
    public autoDriveWithPitch(double speed, int encoderTarget, double timeout, double rollAngle) {
        requires(Robot.Chassis);
        m_timeout = timeout;
        m_speed = speed;
        m_rollAngle = rollAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);

        setTimeout(m_timeout);

        if (m_target > 0) {
            m_speed = -m_speed;
        }

        initialRoll = Robot.Pigeon.getRoll();
        m_rollAngle = initialRoll + 3;
        above3Degrees = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        yaw = -Robot.Pigeon.getYaw();
        pitch = Robot.Pigeon.getRoll();

        if (Math.abs(initialRoll - pitch) > 6) {
            above3Degrees = true;
        }
        Chassis.neoArcade(m_speed, yaw * Kp, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        if (above3Degrees) {
            return (pitch < m_rollAngle || isTimedOut());
        }

        else {
            return (false);
        }
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.Chassis.setAllNeoBrakeMode(IdleMode.kCoast);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}