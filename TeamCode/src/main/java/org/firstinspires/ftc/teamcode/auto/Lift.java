package org.firstinspires.ftc.teamcode.auto;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {
    private DcMotorEx liftMotor1, liftMotor2;
    private Telemetry telemetry = null;

    public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        liftMotor1 = hardwareMap.get(DcMotorEx.class, "liftMotor1");
        liftMotor2 = hardwareMap.get(DcMotorEx.class, "liftMotor2");

        liftMotor1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        liftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        liftMotor2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        int liftMotor1StartPosition;
        int liftMotor1EndPosition;
    }

    private boolean moveLiftToPosition(int targetPosition, double power) {
        liftMotor1.setTargetPosition(targetPosition);
        liftMotor2.setTargetPosition(targetPosition);

        liftMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        liftMotor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        liftMotor1.setPower(power);
        liftMotor2.setPower(power);

        while (liftMotor1.isBusy() && liftMotor2.isBusy()) {
            this.telemetry.addData("LiftMotor1 Position", liftMotor1.getCurrentPosition());
            this.telemetry.addData("LiftMotor2 Position", liftMotor2.getCurrentPosition());
            this.telemetry.update();
        }

        liftMotor1.setPower(0);
        liftMotor2.setPower(0);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        return false;
    }

    public Action liftUp(int targetPosition, double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {

                    initialized = true;
                }
                moveLiftToPosition(targetPosition, power);
                if (liftMotor1.getCurrentPosition() >= targetPosition - 5) {
                    return false;
                }
                return true;
            }
        };
    }

    public Action liftDown(int targetPosition, double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {

                    initialized = true;
                }
                moveLiftToPosition(targetPosition, power);
                packet.put("Lift Down Position", liftMotor1.getCurrentPosition());
                if (liftMotor1.getCurrentPosition() <= targetPosition + 5) {
                    return false;
                }
                return true;
            }
        };
    }

    public void Init() {
        liftMotor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}