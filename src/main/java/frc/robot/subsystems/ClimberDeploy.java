package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import frc.robot.Robot;

/**
 * Controls the release the climber systems using a pneumatics
 */
public class ClimberDeploy extends Subsystem {

    public boolean readyToClimb;

    public int climberPhase;

    private Solenoid climberRelease;

    private Servo climberReleaseServo;

    public DigitalInput climberLineSensor;

    /**
     * Controls the release the climber systems using a pneumatics
     */
    public ClimberDeploy() {
        climberReleaseServo = new Servo(Robot.Constants.servoPort);
        climberRelease = new Solenoid(Robot.Constants.climberReleasePort);
        climberLineSensor = new DigitalInput(Robot.Constants.climberLineSensor);
        this.climberPhase = 5; // 5
        this.readyToClimb = false;

        LiveWindow.addActuator("servo", 1, climberReleaseServo);
    }

    public void servoSet(double pos) {
        climberReleaseServo.set(pos);
    }

    public void servoSetPosition(double pos) {
        climberReleaseServo.setPosition(pos);
    }

    public void servoSetAngle(double pos) {
        climberReleaseServo.setAngle(pos);
    }

    @Override
    public void initDefaultCommand() {

    }

    /**
     * Releases the climber systems
     */
    public void deployClimber() {
        servoSet(0.8);
    }

    /**
     * unReleases the climber systems
     */
    public void undeployClimber() {
        servoSet(0.4);
    }

    /*
     * Boolean to track Black switch on drivers station, determining if climber is
     * active.
     */
    public void readyClimber() {
        this.readyToClimb = true;
        this.climberPhase = 1; // 0
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