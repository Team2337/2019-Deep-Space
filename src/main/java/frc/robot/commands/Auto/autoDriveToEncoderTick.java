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
public class autoDriveToEncoderTick extends Command {

        int distanceForward;
        int inchesToTicks = 582;
        int direction = 0;
        double maxDriveSpeed = 0;
        double turnSpeed = 0;

        // max speed forward and reverse
        // double maxSpeed = 0.5;

        /**
         * Drives the robot to a distance in encoder ticks The command ends once the
         * robot is within 100 ticks of the setpoint
         * 
         * @param direction       - use positive or negative value to deterimine the direction of the drive (positive forward, negative backwards)
         * @param forwardDistance - forward/reverse distance in inches - will convert to ticks
         * @param maxDriveSpeed - maximum drive speed
         * @param turnSpeed - turn speed
         */
        public autoDriveToEncoderTick(int direction, int forwardDistance, double maxDriveSpeed, double turnSpeed) {
                this.distanceForward = forwardDistance;
                this.maxDriveSpeed = maxDriveSpeed;
                this.turnSpeed = turnSpeed;
                this.direction = direction;
                requires(Robot.Chassis);
        }

        protected void initialize() {
                Robot.Chassis.resetEncoders();
        }

        protected void execute() {
                Chassis.neoArcade(maxDriveSpeed * direction, turnSpeed, false);
        }

        protected boolean isFinished() {
                return Math.abs(Robot.Chassis.getAverageEncoderPosition()) > Math.abs(distanceForward);
        }

        protected void end() {
        }

        protected void interrupted() {
                this.end();
        }

}