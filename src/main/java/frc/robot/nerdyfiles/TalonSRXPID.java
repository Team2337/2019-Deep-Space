package frc.robot.nerdyfiles;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * Add your docs here.
 */
public class TalonSRXPID extends TalonSRX implements PIDOutput, PIDSource {
    
    private PIDSourceType m_pidSource;

    public TalonSRXPID(int deviceNumber) {
        super(deviceNumber | 0x02040000);
    }

    @Override
    public void pidWrite(double output) {
        this.set(ControlMode.Position, output);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        m_pidSource = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return m_pidSource;
    }

    @Override
    public double pidGet() {
        return this.getSelectedSensorPosition();
	}
}
