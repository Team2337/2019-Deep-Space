package frc.robot.commands.Auto.Common;

import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.driveAtSetSpeed;
import frc.robot.commands.HatchBeak.hatchBeakClose;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

public class CommonHighLeftToNearRocketFast extends CommandGroup {
    public CommonHighLeftToNearRocketFast() {

    addSequential(new autoTurnOnLimeLightLED());
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 1.0));
    addSequential(new driveAtSetSpeed(-0.4));
    addSequential(new autoPIDVisionDrive(3, 0.07, 0.02, 0.6));
    addSequential(new autoResetEncoders());
    addSequential(new CommonScoreHatch());
    
    addParallel(new autoLiftToPositionWithWait(Robot.Lift.hatchLowScorePosition, 0.5));
    addSequential(new autoTankDrive(-1.0, -0.1, -25000, 0, "left", IdleMode.kCoast));
    addSequential(new autoTurnOnLimeLightLED());
    addSequential(new autoResetEncoders());
    addSequential(new autoTankDrive(0.1, 1.0, 0, 16000, "rightVision", IdleMode.kCoast));
    // addSequential(new autoResetSensors());
    addParallel(new hatchBeakClose());
    addSequential(new autoPIDVisionDrive(4.5, 0.09, 0.06, 0.75));
    addSequential(new CommonIntakeHatch());
    addSequential(new autoResetEncoders()); 
    addSequential(new autoWait(0.05));

    }
}