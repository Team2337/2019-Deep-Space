package frc.robot.commands.Auto.Common;

import frc.robot.commands.Auto.Common.*;
import frc.robot.commands.Chassis.*;
import frc.robot.commands.HatchBeak.hatchBeakClose;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Robot;
import frc.robot.commands.Auto.*;

public class CommonRightLoadToNearRocketLow extends CommandGroup {
    public CommonRightLoadToNearRocketLow() {
       
        addSequential(new autoTankDrive(-0.6, -0.5, 55000, 0, "left", IdleMode.kCoast));
    addParallel(new autoTurnOnLimeLightLED());
    addSequential(new autoTankDrive(-0.6, -0.2, 89000, 0, "left", IdleMode.kBrake));
    addSequential(new autoResetEncoders());
    addSequential(new autoWait(0.05));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 14000, "right", IdleMode.kCoast));
    addSequential(new autoTankDrive(0.1, 0.5, 0, 55000, "rightVision", IdleMode.kCoast));
    addSequential(new autoPIDVisionDrive(4, 0.08, 0.08, 0.7));
    addSequential(new autoLiftToPosition(Robot.Lift.hatchLowScorePosition));
    // addSequential(new CommonScoreHatch());

    }
}