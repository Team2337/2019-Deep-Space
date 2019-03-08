package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import frc.robot.Robot;

/**
 * Controls the release the climber systems using a pneumatics
 */
public class ClimberDeploy extends Subsystem {

    public boolean readyToClimb;

    public int climberPhase;

    private Solenoid climberRelease;

    public DigitalInput climbLineSensor;

    /**
     * Controls the release the climber systems using a pneumatics
     */
    public ClimberDeploy() {
        climberRelease = new Solenoid(Robot.Constants.climberReleasePort);
        climbLineSensor = new DigitalInput(Robot.Constants.climberLineSensor);
        this.climberPhase = 5;
        this.readyToClimb = false;
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
     * unReleases the climber systems
     */
    public void undeployClimber() {
        climberRelease.set(false);
    }

    /*
     * Boolean to track Black switch on drivers station, determining if climber is
     * active.
     */
    public void readyClimber() {
        this.readyToClimb = true;
        this.climberPhase = 0;
    }

    public void unreadyClimber() {
        this.readyToClimb = false;
        this.climberPhase = 5;
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