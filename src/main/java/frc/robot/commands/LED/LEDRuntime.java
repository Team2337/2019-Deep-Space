package frc.robot.commands.LED;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.commands.Auto.autoEndAuto;
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
		LED.setColor(LED.off);
	}

	protected void execute() {
			if(Robot.HatchBeak.status()) {
				LED.setColor(LED.white);
			} else {
				if(Robot.ClimberDeploy.climberPhase < 5) {
					if(autoEndAuto.endedAutoLED) {
						LED.setColor(LED.red);
					} 
					if(!Robot.ClimberDeploy.climberLineSensor.get() || Robot.ClimberDeploy.climberPhase == 3) {
						LED.setColor(LED.green);
					} else {
						if(Robot.ClimberDeploy.getServo() == 0.8) {
							LED.setColor(LED.darkBlue);
						} else {
							LED.setColor(LED.rainbow);
						}
					}
				} else {
					if(Robot.ClimberDeploy.getServo() == 0.8) {
						LED.setColor(LED.blue);
					} else {
						LED.setColor(LED.off);
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
