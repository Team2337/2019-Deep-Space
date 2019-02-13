package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.subsystems.CargoEscalator;

/**
 * Controls the escalator/conveyor for cargo
 */
public class CargoBigBrother extends Subsystem {

    public DigitalInput cargoIntakeSensor;
    public DigitalInput cargoEscalatorSensor;
    public DigitalInput cargoTrolleySensor;

    // Position to score in the low rocket
    public double lowCargoPosition = 150;
    // Position to score in the mid rocket
    public double midCargoPosition = 120;
    // Position to allow the escalator to feed a ball into the trolley
    public double intakeCargoPosition = 150;
    // Position to eject the cargo ball (if applicable) - to be used if we are mid
    // and need to eject the ball, it would be faster than to go through the robot
    public double ejectCargoPosition = 200;

    // Determines how the command will run when there is a ball in the trolley
    public boolean passedIntakeSensor;

    public CargoBigBrother() {
        cargoIntakeSensor = new DigitalInput(0);
        cargoEscalatorSensor = new DigitalInput(1);
        cargoTrolleySensor = new DigitalInput(2);

        passedIntakeSensor = false;
    }

    public void initDefaultCommand() {

    }

    public void stop() {
    }

    public int cargoLevel() {
        if (cargoIntakeSensor.get()) {
            return 1;
        } else if (cargoEscalatorSensor.get()) {
            return 2;
        } else if (cargoTrolleySensor.get()) {
            return 3;
        }
        return 0;
    }
}