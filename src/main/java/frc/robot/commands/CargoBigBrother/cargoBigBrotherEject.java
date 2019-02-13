package frc.robot.commands.CargoBigBrother;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

/**
 * This command will run the escalator upwards to move cargo towards the scoring
 * mechanism
 * 
 * @author Jack E.
 */
public class cargoBigBrotherEject extends Command {

    double intakeSpeed = 1;
    double escalatorSpeed = 1;
    double scoreSpeed = 1;

    boolean startWithBall = false;
    
    boolean liftEject = false;

    public cargoBigBrotherEject() {
        requires(Robot.CargoBigBrother);
    }

    // Set the speed of the cargo escalator motors
    @Override
    protected void initialize() {
        this.startWithBall = Robot.CargoBigBrother.startWithCargo;

        if (startWithBall) {
            if (Robot.CargoBigBrother.cargoLevel() == 3) {
                // If the lift is meant to at the mid cargo position, its likely traveling to
                // that position, so just have it continue to the eject cargo position
                if (Robot.Lift.getSetpoint() >= Robot.CargoBigBrother.midCargoPosition) {
                    Robot.Lift.setSetpoint(Robot.CargoBigBrother.ejectCargoPosition);
                    // In execute, we will check to see if the lift is actually in position
                }
            }
        }
        Robot.CargoIntake.rollOut(1);
        Robot.CargoEscalator.rollDown(1);
    }

    @Override
    protected void execute() {
        if (startWithBall) {
            if (Robot.CargoBigBrother.cargoIntakeSensor.get()) {
                Robot.CargoIntake.stop();
            }
            if (Robot.CargoBigBrother.cargoEscalatorSensor.get()) {
                if (!Robot.Lift.atPosition(10)) {
                    Robot.CargoEscalator.stop();
                } else {
                    Robot.CargoScore.rollIn(1);
                    Robot.CargoEscalator.rollUp(escalatorSpeed);
                    // isFinished
                }
            }
        }
        // If we do start with a ball, the trolley's speed is already set until it
        // doesn't detect the ball anymore
    }

    @Override
    protected boolean isFinished() {
        if (startWithBall) {
            return Robot.CargoBigBrother.cargoTrolleySensor.get() && !Robot.CargoBigBrother.cargoEscalatorSensor.get();
        } else {
            return !Robot.CargoBigBrother.cargoTrolleySensor.get();
        }
    }

    @Override
    protected void end() {
        Robot.CargoBigBrother.stop();
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}