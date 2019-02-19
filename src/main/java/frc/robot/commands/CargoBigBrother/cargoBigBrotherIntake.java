package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will intake a ball and move it to the trolley
 * 
 * @author Jack E.
 */
public class cargoBigBrotherIntake extends Command {
    // TODO: Tune these values to comp bot
    double intakeSpeed = 1;
    double escalatorSpeed = 1;
    // Speed of the trolley intake. Slower to prevent overshooting the sensor
    double trolleyIntakeSpeed = 0.3;

    // How close the lift needs to be to the intake position
    double liftTolerance = 5;

    public cargoBigBrotherIntake() {
        requires(Robot.CargoBigBrother);
        requires(Robot.CargoIntake);
        requires(Robot.CargoEscalator);
        requires(Robot.CargoScore);
        requires(Robot.Lift);
    }

    // Check the cargo level and start the command accordingly.
    @Override
    protected void initialize() {
        // Assume we dont have a cargo in the deadzone
        Robot.CargoBigBrother.inDeadzone = false;

        // Move the lift into the intake positon 
        
        switch (Robot.CargoBigBrother.cargoLevel()) {
            
            case 0: {
                // If the non-defensive intake button is pressed
                // if (Robot.oi.operatorJoystick.start.get()) {
                    Robot.CargoDrawbridge.lowerTheDrawbridge();
                    // }
                    // Start rolling the intake inwards
                    Robot.CargoIntake.rollIn(intakeSpeed);
                    // Does not break, as the next cases have the same ending
                }
                case 1:
                case 2:
                case 3: {
                    // Start rolling the escalator upwards
                    Robot.CargoEscalator.rollUp(escalatorSpeed);
                    Robot.Lift.setSetpoint(Robot.Lift.cargoIntakePosition);
                    Robot.CargoScore.rollForwards(trolleyIntakeSpeed);
            break;
        }
        case 4: {
            break;
        }

        }
    }

    @Override
    protected void execute() {

        switch (Robot.CargoBigBrother.cargoLevel()) {

        case 0: {
            // Nothing needs to happen in execute, as the necessary motors are running
            // as per initialize
            break;
        }
        case 1: {
            // Once the ball is passed the intake, raise the drawbridge and stop the intake
            Robot.CargoDrawbridge.raiseTheDrawbridge();
            Robot.CargoIntake.stop();

            // Once the ball has passed the intake *sensor*, it is between the two escalator
            // sensors, and thus, in position 2.
            if (Robot.CargoBigBrother.cargoIntakeSensor.get() == false) {
                Robot.CargoBigBrother.inDeadzone = true;
            }
            //break;
        }
        case 2: {
            // Nothing special needs to happen at this position
            if (!Robot.CargoBigBrother.cargoEscalatorSensor.get()) {
                Robot.CargoBigBrother.inDeadzone = false;
            }
            //break;
        }
        case 3: {
            // If the lift is not in position, stop the escalator until it is
            if (Robot.Lift.atCargoIntakePosition(liftTolerance)) {
                // Roll the cargo into the trolley
                Robot.CargoEscalator.rollUp(escalatorSpeed);
                Robot.CargoScore.rollForwards(trolleyIntakeSpeed);
            } else {
                Robot.CargoEscalator.stop();
            }
            break;
        }
        case 4: {
            // Stop both the escalator and the trolley
            Robot.CargoEscalator.stop();
            Robot.CargoScore.stop();
            // Move the lift to a lower position to prevent the robot from being topheavy
           // Robot.Lift.setSetpoint(Robot.Lift.cargoLoadedPosition);
            // When the intake is finished, the trolley is "loaded"
            Robot.CargoBigBrother.inFireMode = true;
            break;
        }

        }
    }

    // This command is meant to run as long as the trigger is held
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        // Stop everything except for the trolley
        Robot.CargoDrawbridge.raiseTheDrawbridge();
        Robot.CargoEscalator.stop();
        Robot.CargoIntake.stop();
        // Since this command requires cargoBigBrother, when we try to score, it will
        // kick out this command. This condition prevents the scoring motors from
        // locking up due to this fighting
        if (!Robot.oi.operatorJoystick.triggerLeft.get()) {
            Robot.CargoScore.stop();
        }
    }

    @Override
    protected void interrupted() {
        // If the trigger is released, stop the cargo system
        this.end();
    }
}