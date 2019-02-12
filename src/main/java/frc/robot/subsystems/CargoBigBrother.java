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
    private double lowCargoPosition = 150;
    // Position to score in the mid rocket
    private double midCargoPosition = 100;
    // Position to allow the escalator to feed a ball into the trolley
    private double intakeCargoPosition = 150;
    // Position to eject the cargo ball (if applicable) - to be used if we are mid
    // and need to eject the ball, it would be faster than to go through the robot
    private double ejectCargoPosition = 200;

    // Determines how the command will run when there is a ball in the trolley
    public boolean startWithCargo;

    public CargoBigBrother() {
        cargoIntakeSensor = new DigitalInput(0);
        cargoEscalatorSensor = new DigitalInput(1);
        cargoTrolleySensor = new DigitalInput(2);

        // With preloaded cargo, this would set startWithCargo accordingly
        startWithCargo = cargoTrolleySensor.get();
    }

    public void initDefaultCommand() {

    }

    public void intake(double speed) {
        // If any of the sensors are triggered
        if (cargoIntakeSensor.get() || cargoEscalatorSensor.get() || cargoTrolleySensor.get()) {

        }
    }

    // Run the cargo system backwards
    public void eject(double speed) {
        // if the lift is in the position to just eject straight from the trolley (such
        // as the top), then do so.
        if(Robot.Lift.getSetpoint() >= midCargoPosition){
            Robot.Lift.setSetpoint(ejectCargoPosition);
        }

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