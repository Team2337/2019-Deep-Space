package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;

/**
 * Controls the release the climber systems using a pneumatics
 */
public class ClimberDeploy extends Subsystem {

    private Solenoid climberRelease;

    /**
     * Controls the release the climber systems using a pneumatics
     */
    public ClimberDeploy() {
        climberRelease = new Solenoid(Robot.Constants.climberReleasePort);
    }

    @Override
    public void initDefaultCommand() {

    }

    /**
     * Releases the climber systems
     */
    public void deployClimber() {
        climberRelease.set(true);
    }

    /**
     * Releases the climber systems
     */
    public void retractClimber() {
        climberRelease.set(false);
    }

    /**
     * Returns whether or not the climber has been deployed
     * 
     * @return Whether or not the climber has been deployed
     */
    public boolean status() {
        return climberRelease.get();
    }
}