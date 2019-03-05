package frc.robot.commands.ClimberDeploy;

import edu.wpi.first.wpilibj.command.Command;

/**
 * never end ClimbCG
 */
public class neverEndCG extends Command {

	@Override
	protected boolean isFinished() {
		return false;
	}
}