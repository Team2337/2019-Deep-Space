<<<<<<< HEAD
package frc.robot.commands.LED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.CargoIntake;
import frc.robot.subsystems.LED;

/**
 * Sets the color according to various factors, such as if the robot was
 * intaking, climbing, etc.
 * 
 * @author Zayd A. Jack E.
 */
public class LEDRuntime extends Command {

	public LEDRuntime() {
		requires(Robot.LED);
	}

	protected void initialize() {

	}

	protected void execute() {
		if (DriverStation.getInstance().isAutonomous()) {
			LED.setColor(LED.off);
		} else if (DriverStation.getInstance().isOperatorControl()) {
			if (CargoIntake.isRunning()) {
				LED.setColor(LED.rainbow);
			}
			LED.setColor(LED.red);
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
=======
package frc.robot.commands.LED;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * An example command. You can replace me with your own command.
 */
public class LEDRuntime extends Command {



  // CONSTRUCTOR
  public LEDRuntime() {

    requires(Robot.LED);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    this.end();
  }
}
>>>>>>> 79e120810e68181690a04b0c98741d218ab88e5a
