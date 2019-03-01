package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;

/**
 * 
 */
public class ClimberDeploy extends Subsystem {

    private Solenoid pinRelease;

    public ClimberDeploy() {
        pinRelease = new Solenoid(Robot.Constants.climberPort);
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