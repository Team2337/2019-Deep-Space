package frc.robot.commands.LED;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.Auto.autoEndAuto;

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
		Robot.LED.setColor(Robot.LED.off);
	}

	protected void execute() {
			if(Robot.HatchBeak.status()) {
				Robot.LED.setColor(Robot.LED.green);
			} else {
				if(Robot.ClimberDeploy.climberPhase < 5) {
					if(autoEndAuto.endedAutoLED) {
						Robot.LED.setColor(Robot.LED.red);
					} 
					if(!Robot.ClimberDeploy.climberLineSensor.get() || Robot.ClimberDeploy.climberPhase == 3) {
						Robot.LED.setColor(Robot.LED.white);
					} else {
						if(Robot.ClimberDeploy.getServo() == 0.8) {
							Robot.LED.setColor(Robot.LED.darkBlue);
						} else {
							Robot.LED.setColor(Robot.LED.rainbow);
						}
					}
				} else {
					if(Robot.ClimberDeploy.getServo() == 0.8) {
						Robot.LED.setColor(Robot.LED.blue);
					} else {
						Robot.LED.setColor(Robot.LED.off);
					}
				}
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
