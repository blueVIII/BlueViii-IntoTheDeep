package org.firstinspires.ftc.teamcode.auto;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SlideIntake {
    private DcMotorEx slideMotor;
    private Telemetry telemetry;

    public SlideIntake(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        slideMotor = hardwareMap.get(DcMotorEx.class, "slideIntake");
        slideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void moveToPosition(int targetPosition, double power) {
        slideMotor.setTargetPosition(targetPosition);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(power);

        while (slideMotor.isBusy()) {
            this.telemetry.addData("SlideIntake Position", slideMotor.getCurrentPosition());
            this.telemetry.update();
        }

        slideMotor.setPower(0);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }


    public Action slideMoveAction(int targetPosition, double power) {
        return new Action() {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    moveToPosition(targetPosition, power);
                    initialized = true;
                }
                return false;
            }
        };
    }

    public void Init() {
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public double GetPosition() {
        return slideMotor.getCurrentPosition();
    }
}
