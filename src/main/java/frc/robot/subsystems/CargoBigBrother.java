package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * Controls the escalator/conveyor for cargo
 */
public class CargoBigBrother extends Subsystem {

    public DigitalInput cargoIntakeSensor;
    public DigitalInput cargoEscalatorSensor;
    public DigitalInput cargoTrolleySensor;

    // The current position to go to when scoring (such as the middle or lower cargo
    // ports in the rocket)
    public double currentScoringPosition;

    // Determines how the command will run when there is a ball in the trolley
    public boolean passedIntakeSensor;

    public boolean isScoreMode;

    public CargoBigBrother() {
        cargoIntakeSensor = new DigitalInput(0);
        cargoEscalatorSensor = new DigitalInput(1);
        cargoTrolleySensor = new DigitalInput(2);

        // Default to the middle scoring position
        currentScoringPosition = Robot.Lift.midCargoScorePosition;

        passedIntakeSensor = false;
        isScoreMode = false;
    }

    public void initDefaultCommand() {

    }

    /**
     * Stop all systems involved in the movement of cargo (the lift will pick up
     * right where a new command is telling it to go, even if it stops for a moment)
     */
    public void stop() {
        Robot.Lift.stop();
        Robot.CargoIntake.rollIn(0);
        Robot.CargoEscalator.rollUp(0);
        Robot.CargoScore.rollIn(0);
    }

    // Ball positions:
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
        } else if (passedIntakeSensor) {
            return 2;
        } else if (cargoEscalatorSensor.get()) {
            return 3;
        } else if (cargoTrolleySensor.get()) {
            return 4;
        } else if (Robot.Lift.getSetpoint() == Robot.Lift.lowCargoScorePosition && Robot.Lift.atPosition(10)) {
            return 5;
        } else if (Robot.Lift.getSetpoint() == Robot.Lift.midCargoScorePosition && Robot.Lift.atPosition(10)) {
            return 6;
        } else if (Robot.Lift.getSetpoint() == Robot.Lift.cargoEjectPosition && Robot.Lift.atPosition(10)) {
            return 7;
        } else {
            return 0;
        }
    }

    /**
     * Set the lift setpoint, which would mean that the trolley is no longer ready
     * to score
     * 
     * @param pos The setpoint to move to
     */
    public void moveToPosition(double pos) {
        isScoreMode = false;
        Robot.Lift.setSetpoint(pos);
    }

    public void periodic() {
        SmartDashboard.putBoolean("Score mode", isScoreMode);
        SmartDashboard.putBoolean("Passed intake sensor", passedIntakeSensor);
        SmartDashboard.putNumber("Cargo level", cargoLevel());
        SmartDashboard.putBoolean("Intake sensor", cargoIntakeSensor.get());
        SmartDashboard.putBoolean("Escalator sensor", cargoEscalatorSensor.get());
        SmartDashboard.putBoolean("Trolley sensor", cargoTrolleySensor.get());
        SmartDashboard.putNumber("Lift setpoint", Robot.Lift.getSetpoint());
        SmartDashboard.putBoolean("Lift is at position", Robot.Lift.atPosition(10));
    }
}