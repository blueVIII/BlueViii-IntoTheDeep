package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "Square Auto", group = "BlueViii-auto")
public class DriveSquare extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.setMsTransmissionInterval(50);

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));

        while (!isStarted() && !isStopRequested()) {
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {

        }
    }
}
