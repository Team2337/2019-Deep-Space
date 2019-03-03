package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Controls how the cargo balls move throughout the robot
 */
public class CargoBigBrother extends Subsystem {

    // Enable/disable debug mode for CargoBigBrother
    boolean cargoBigBrotherDebug = false;

    public DigitalInput cargoIntakeSensor;
    public DigitalInput cargoEscalatorSensor;
    public DigitalInput cargoTrolleySensor;

    // Determines how the command will run when there is a ball in the trolley
    public boolean inDeadzone;

    public CargoBigBrother() {

        // Cargo system sensors to help know where the ball is within the robot
        cargoIntakeSensor = new DigitalInput(Robot.Constants.cargoIntakeSensorID);
        cargoEscalatorSensor = new DigitalInput(Robot.Constants.cargoEscalatorSensorID);
        cargoTrolleySensor = new DigitalInput(Robot.Constants.cargoTrolleySensorID);

        // By default, the ball is not in the deadzone (if it is, then it will simply be
        // moved out of it)
        inDeadzone = false;
    }

    public void initDefaultCommand() {

    }

    /**
     * Stop all systems involved in the movement of cargo
     */
    public void stop() {
        Robot.CargoIntake.rollIn(0);
        Robot.CargoEscalator.rollUp(0);
        Robot.CargoScore.rollForwards(0);
    }

    /**
     * Determine the position of the cargo within the robot
     * 
     * @return The position of the cargo within the robot:
     *         <ol>
     *         <li>No ball - Automatically set as the default value</li>
     *         <li>At intake sensor - Set when the cargo reaches the intake
     *         sensor</li>
     *         <li>Between escalator sensors - Set when inDeadzone is true</li>
     *         <li>Top of escalator - Set when the escalator sensor is true</li>
     *         <li>Fully in the cargo scoring mechanism - Set when the trolley
     *         sensor is true</li>
     *         </ol>
     */
    public int cargoLevel() {
        if (cargoIntakeSensor.get()) {
            return 1;
        } else if (inDeadzone) {
            return 2;
        } else if (!cargoEscalatorSensor.get()) {
            return 3;
        } else if (!cargoTrolleySensor.get()) {
            return 4;
        } else {
            return 0;
        }
    }

    public void periodic() {
        if (cargoBigBrotherDebug) {
            SmartDashboard.putNumber("Cargo level", cargoLevel());
            SmartDashboard.putBoolean("Intake sensor", cargoIntakeSensor.get());
            SmartDashboard.putBoolean("Escalator sensor", !cargoEscalatorSensor.get());
            SmartDashboard.putBoolean("Trolley sensor", !cargoTrolleySensor.get());
            SmartDashboard.putNumber("Lift setpoint", Robot.Lift.getSetpoint());
            SmartDashboard.putBoolean("Lift is at position", Robot.Lift.atPosition(10));
        }
    }
}