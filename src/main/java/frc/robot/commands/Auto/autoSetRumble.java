package frc.robot.commands.Auto;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Chassis;

/**
 * Drives until the line sensor sees a line
 * 
 * @category AUTO
 * @author Bryce G.
 */
public class autoSetRumble extends Command {

    public autoSetRumble() {

		requires(Robot.Chassis);
	}

	protected void initialize() {
		Robot.Chassis.print = true;
	}


	protected void execute() {
		
	}


	protected boolean isFinished() {
		return true;
	}

	protected void end() {
		
	}


	protected void interrupted() {
		this.end();
	}
	
}