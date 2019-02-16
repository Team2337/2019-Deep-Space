package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.CargoIntake.cargoIntakeIn;

/**
 * Controls the escalator/conveyor for cargo
 */
public class CargoBigBrother extends Subsystem {

    public DigitalInput cargoIntakeSensor;
    public DigitalInput cargoEscalatorSensor;
    public DigitalInput cargoTrolleySensor;

    //TODO:  we should move escalator, Intake and Score into here and delete other subsystems!!!

    // The current position to go to when scoring (such as the middle or lower cargo
    // ports in the rocket)
    public double defaultScoringPosition;

    // Determines how the command will run when there is a ball in the trolley
    public boolean inDeadzone;

    public CargoBigBrother() {
        cargoIntakeSensor = new DigitalInput(0);
        cargoEscalatorSensor = new DigitalInput(2);
        cargoTrolleySensor = new DigitalInput(3);

        // Default to the middle scoring position
        defaultScoringPosition = Robot.Lift.cargoShipScorePosition;

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
        Robot.CargoScore.rollIn(0);
    }

    // Ball positions: TODO:
    // 0 - No ball: Automatically set as the default value
    // 1 - At intake sensor: Set when the cargo reaches the intake sensor
    // 2 - Between escalator sensors: Set when isPassedIntake is
    // --- true AND the cargoIntake sensor is false
    // 3 - Top of escalator: Set when the escalator sensor is true
    // 4 - Fully in the cargo scoring mechanism: Set when the trolley
    // --- sensor is true
    // 5 - At low cargo scoring position: Set when the lift is atPosition() for
    // --- the low cargo scoring position
    // 6 - At mid cargo scoring position: Set when the lift is atPosition() for
    // --- the mid cargo scoring position
    // 7 - At eject cargo position: Set when the lift is atPosition()
    // --- for the eject cargo scoring position

    public int cargoLevel() {
        if (cargoIntakeSensor.get()) {
            return 1;
        } else if (inDeadzone) {
            return 2;
        } else if (!cargoEscalatorSensor.get()) {
            return 3;
        } else if (!cargoTrolleySensor.get()) {
            return 4;
            /* TODO:
             * } else if (Robot.Lift.getSetpoint() == Robot.Lift.lowCargoScorePosition &&
             * Robot.Lift.atPosition(10)) { return 5; } else if (Robot.Lift.getSetpoint() ==
             * Robot.Lift.midCargoScorePosition && Robot.Lift.atPosition(10)) { return 6; }
             * else if (Robot.Lift.getSetpoint() == Robot.Lift.cargoEjectPosition &&
             * Robot.Lift.atPosition(10)) { return 7;
             */
        } else {
            return 0;
        }
    }


    public void periodic() {
        SmartDashboard.putBoolean("Passed intake sensor", inDeadzone);
        SmartDashboard.putNumber("Cargo level", cargoLevel());
        SmartDashboard.putBoolean("Intake sensor", cargoIntakeSensor.get());
        SmartDashboard.putBoolean("Escalator sensor", !cargoEscalatorSensor.get());
        SmartDashboard.putBoolean("Trolley sensor", !cargoTrolleySensor.get());
        SmartDashboard.putNumber("Lift setpoint", Robot.Lift.getSetpoint());
        SmartDashboard.putBoolean("Lift is at position", Robot.Lift.atCargoLowPosition(10)); // Add more
        SmartDashboard.putNumber("CargoBB Level EXEC", Robot.CargoBigBrother.cargoLevel());

       // SmartDashboard.putData("value", new cargoIntakeIn());

    }
}