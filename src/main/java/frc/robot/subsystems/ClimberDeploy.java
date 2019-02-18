package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * 
 */
public class ClimberDeploy extends Subsystem {

    private Solenoid pinRelease;

    public ClimberDeploy() {
        pinRelease = new Solenoid(5);
    }

    @Override
    public void initDefaultCommand() {

    }

    public void deployClimber() {
        pinRelease.set(true);
    }

    public void retractClimber() {
        pinRelease.set(false);
    }
}