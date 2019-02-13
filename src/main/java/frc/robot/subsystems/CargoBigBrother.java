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

    public double currentScoringPosition;

    // Determines how the command will run when there is a ball in the trolley
    public boolean passedIntakeSensor;

    public boolean isScoreMode;

    public CargoBigBrother() {
        cargoIntakeSensor = new DigitalInput(0);
        cargoEscalatorSensor = new DigitalInput(1);
        cargoTrolleySensor = new DigitalInput(2);

        //Default to the middle scoring position
        currentScoringPosition = midCargoPosition;

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

    /*
     * 0 - No ball: Automatically set as the default value 1 - At intake sensor: Set
     * when the in 2 - Between escalator sensors: Set when isPassedIntake is true
     * AND the cargoIntake sensor is false (it is part of the conditions for
     * isPassedIntake to be set to true, but double check while testing) 3 - Top of
     * escalator: Set when the escalator sensor is true 4 - Fully in the cargo
     * scoring mechanism: Set when the trolley sensor is true 5 - At low cargo
     * scoring position: Set when the lift is atPosition(10) for the low cargo
     * scoring position 6 - At mid cargo scoring position: Set when the lift is
     * atPosition(10) for the mid cargo scoring position 7 - At eject cargo
     * position: Set when the lift is atPosition(10) for the eject cargo scoring
     * position
     */

    public int cargoLevel() {
        if (cargoIntakeSensor.get()) {
            return 1;
        } else if (passedIntakeSensor) {
            return 2;
        } else if (cargoEscalatorSensor.get()) {
            return 3;
        } else if (cargoTrolleySensor.get()) {
            return 4;
        } else if (Robot.Lift.getSetpoint() == lowCargoPosition && Robot.Lift.atPosition(10)) {
            return 5;
        } else if (Robot.Lift.getSetpoint() == midCargoPosition && Robot.Lift.atPosition(10)) {
            return 6;
        } else if (Robot.Lift.getSetpoint() == ejectCargoPosition && Robot.Lift.atPosition(10)) {
            return 7;
        } else {
            return 0;
        }
    }

    public void moveToPosition(double pos) {
        isScoreMode = false;
        Robot.Lift.setSetpoint(pos);
    }
}