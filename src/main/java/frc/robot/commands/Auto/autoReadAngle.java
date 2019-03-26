package frc.robot.commands.Auto;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;

/**
 * Reads the current angle the robot is at
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoReadAngle extends InstantCommand {

	protected void initialize() {
		Robot.autonAngle = Robot.Pigeon.getYaw();
	}
	
}