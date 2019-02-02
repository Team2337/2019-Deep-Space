package frc.robot.commands.LED;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.nerdyfiles.wrappers.AutoCommandManager;

/**
 * Setting the color when it is in teleop
 * @author Zayd 
 */
public class LEDRuntime extends Command {

  int rightBumper = 6;
	int timesThrough = 0;
	int blinkNum = 5;
	
	public LEDRuntime() {
		requires(Robot.LED);
	}

	protected void initialize() {

	}

	protected void execute() {
		if (AutoCommandManager.getInstance().state.equals("teleop")) {
			Robot.LED.blinkin.setColor(0.93);
	}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		this.end();
	}
}