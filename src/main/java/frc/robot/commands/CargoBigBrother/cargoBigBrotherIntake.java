package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the escalator upwards to move cargo towards the scoring
 * mechanism
 * 
 * @author Jack E.
 */
public class cargoBigBrotherIntake extends Command {

    double intakeSpeed = 1;
    double escalatorSpeed = 1;
    double scoreSpeed = 1;

    boolean startWithBall = false;

    public cargoBigBrotherIntake() {
        requires(Robot.CargoBigBrother);
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {

        switch (Robot.CargoBigBrother.cargoLevel()) {
            // If a ball is not detected within the robot
            case 0: {
                // Set the lift to move to the intake position
                Robot.Lift.setSetpoint(Robot.CargoBigBrother.intakeCargoPosition);
                // If the ball is just between the escalator sensors
                if (Robot.CargoBigBrother.passedIntakeSensor) {

                    // If there really is no ball within the robot
                } else {
                    Robot.CargoIntake.rollIn(1);
                    Robot.CargoEscalator.rollUp(1);
                }
            }
            case 1: {
                
            }
        }
    }

    @Override
    protected void execute() {
        if (!startWithBall) {
            if (Robot.CargoBigBrother.cargoIntakeSensor.get()) {
                Robot.CargoIntake.stop();
            }
            if (Robot.CargoBigBrother.cargoEscalatorSensor.get()) {
                if (!Robot.Lift.atPosition(10)) {
                    Robot.CargoEscalator.stop();
                } else {
                    Robot.CargoScore.rollIn(1);
                    Robot.CargoEscalator.rollUp(1);
                    // isFinished
                }
            }
        }
        // If we do start with a ball, the trolley's speed is already set (in init)
        // until it
        // doesn't detect the ball anymore
    }

    @Override
    protected boolean isFinished() {
        if (startWithBall) {
            // If the cargo has successfully reached the trolley
            Robot.CargoBigBrother.startWithCargo = true;
            return Robot.CargoBigBrother.cargoTrolleySensor.get() && !Robot.CargoBigBrother.cargoEscalatorSensor.get();
        } else {
            // If the cargo has successfully exited the trolley (making it or otherwise)
            Robot.CargoBigBrother.startWithCargo = false;
            return !Robot.CargoBigBrother.cargoTrolleySensor.get();
        }
    }

    @Override
    protected void end() {
        // If the trigger is released mid-travel or the ball has exited
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        // If the ball is not fully in the trolley when the trigger is released, we do
        // not start with a ball
        this.end();
    }
}