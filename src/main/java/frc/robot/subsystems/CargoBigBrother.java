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

    public void eject(double speed) {

    }

    public void stop() {
    }
}