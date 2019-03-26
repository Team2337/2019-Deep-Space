package frc.robot.commands.Chassis;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class lockDownNeo extends Command {

    // PID Values
    double moveSpeed;
    double kP = 4;
    double kI = 0;
    double kD = 0;
    double kIZ = 0;
    double kFF = 0;
    double kMaxOutput = 1;
    double kMinOutput = -1;
    private CANPIDController rightPIDController, leftPIDController;

    public lockDownNeo() {

        // PID Controllers for each Spark MAX
        rightPIDController = Robot.Chassis.neoRightFrontMotor.getPIDController();
        leftPIDController = Robot.Chassis.neoLeftFrontMotor.getPIDController();

        // Set the PID values to those given above
        rightPIDController.setP(kP);
        rightPIDController.setI(kI);
        rightPIDController.setD(kD);
        rightPIDController.setIZone(kIZ);
        rightPIDController.setFF(kFF);
        rightPIDController.setOutputRange(kMinOutput, kMaxOutput);

        leftPIDController.setP(kP);
        leftPIDController.setI(kI);
        leftPIDController.setD(kD);
        leftPIDController.setIZone(kIZ);
        leftPIDController.setFF(kFF);
        leftPIDController.setOutputRange(kMinOutput, kMaxOutput);

        requires(Robot.Chassis);
    }

    protected void initialize() {

        // Reset all of the robots encoders to set their current position to 0
        Robot.Chassis.resetEncoders();
        Robot.Chassis.resetNeoEncoders();
        Robot.Shifter.shiftLowGear();

        // Sets the NEO Position to zero to hold the current position
        rightPIDController.setReference(0, ControlType.kPosition);
        leftPIDController.setReference(0, ControlType.kPosition);
    }

    // This command cannot end, or else the Chassis returns to driveByJoystick
    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }
}