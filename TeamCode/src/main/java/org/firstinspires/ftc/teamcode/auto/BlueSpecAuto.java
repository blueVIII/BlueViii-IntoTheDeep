package org.firstinspires.ftc.teamcode.auto;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.hardware.Servo;

        import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "BlueSpecAuto", group = "Autonomous")
public class BlueSpecAuto extends LinearOpMode {

    @Override
    public void runOpMode() {

        double halfWidth = 7.4375;
        double halfLength = 8.125;

        Pose2d initialPose = new Pose2d(24 - halfWidth, -72 + halfLength, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        Lift lift            = new Lift(hardwareMap, telemetry);
        lift.Init();
        SlideIntake slide    = new SlideIntake(hardwareMap, telemetry);
        slide.Init();
        RobotServos servos   = new RobotServos(hardwareMap);
        Servo rotateArm    = hardwareMap.get(Servo.class, "rotateArm");


        /*int liftMotor1StartPosition = lift.liftMotor1.getCurrentPosition();
        int liftMotor1EndPosition = liftMotor1StartPosition + 4650; */


        Action liftToHighJunction = lift.moveLiftAction(3000, 0.8);
        Action liftToLowPosition  = lift.moveLiftAction( 1500, 0.8);
        Action liftDown = lift.moveLiftAction(0, 0.8);

        Action slideIn  = slide.slideMoveAction( 109,  0.7);
        Action slideOut = slide.slideMoveAction(-1250, 0.7);

        Action openBottomClaw  = servos.moveBottomClaw(0.0);
        Action closeBottomClaw = servos.moveBottomClaw(1.0);
        Action closeTopClaw = servos.moveTopClaw(1.0);
        Action openTopClaw = servos.moveTopClaw(0.0);
        Action rotateArmOut    = servos.moveRotateArm(1.0);
        Action flipTClawOut = servos.moveFlipTClaw(0); // for specimen
        Action rotateTClaw = servos.moveRotateTClaw(1); // for specimen
;
        TrajectoryActionBuilder drive1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-12 + halfWidth, -36 + halfLength))
                .waitSeconds(0.01);

        TrajectoryActionBuilder drive2 = drive.actionBuilder(new Pose2d(-12 + halfWidth, -36 + halfLength,Math.toRadians(90)))
                .lineToY(-42.5 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(47 - halfWidth, -45.5 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(53, -22 + halfLength),null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52, null, new ProfileAccelConstraint(-80, 80))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(63, -22 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .strafeTo(new Vector2d(64,-35), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(52, -75 + halfLength, Math.toRadians(260)),Math.toRadians(260), null, new ProfileAccelConstraint(-80, 80))
                ;

        /*TrajectoryActionBuilder drive1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-12 + halfWidth, -39.5 + halfLength))
                .waitSeconds(1)
                .lineToY(-42.5 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(47 - halfWidth, -45.5 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(53, -22 + halfLength),null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52, null, new ProfileAccelConstraint(-80, 80))
                .lineToY(-22 + halfLength, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(63, -22 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .lineToY(-22 + halfLength)
                .waitSeconds(0.01)
                .strafeTo(new Vector2d(71.5, -22 + halfLength))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .strafeTo(new Vector2d(64,-42), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(52, -76 + halfLength, Math.toRadians(250)),Math.toRadians(250), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(42, -65 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(90), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(5, -45 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(45, -76 + halfLength, Math.toRadians(250)),Math.toRadians(250), null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1)
                .strafeTo(new Vector2d(35, -65 + halfLength), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5 + halfLength, Math.toRadians(90)),Math.toRadians(90), null, new ProfileAccelConstraint(-80, 80));
                */

        /*TrajectoryActionBuilder drive1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-12, -39.5))
                .waitSeconds(1);

        TrajectoryActionBuilder drive2 = drive.actionBuilder(initialPose)
                .lineToY(-42.5, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(47, -45.5), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-22, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(53, -22), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52, null, new ProfileAccelConstraint(-80, 80))
                .lineToY(-22, null, new ProfileAccelConstraint(-80, 80))
                .strafeTo(new Vector2d(63, -22), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-52)
                .strafeTo(new Vector2d(64, -42), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90));
        TrajectoryActionBuilder drive3 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(52, -76, Math.toRadians(250)), Math.toRadians(250),
                        null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1);
        TrajectoryActionBuilder drive4 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(42, -65), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5, Math.toRadians(90)), Math.toRadians(90),
                        null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1);
        TrajectoryActionBuilder drive5 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(5, -45), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(45, -76, Math.toRadians(250)), Math.toRadians(250),
                        null, new ProfileAccelConstraint(-80, 80))
                .waitSeconds(1);
        TrajectoryActionBuilder drive6 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(35, -65), null, new ProfileAccelConstraint(-80, 80))
                .splineToLinearHeading(new Pose2d(-6, -39.5, Math.toRadians(90)), Math.toRadians(90),
                        null, new ProfileAccelConstraint(-80, 80));

        Action trajectoryAction1 = drive1.build();
        Action trajectoryAction2 = drive2.build();
        Action trajectoryAction3 = drive3.build();
        Action trajectoryAction4 = drive4.build();
        Action trajectoryAction5 = drive5.build();
        Action trajectoryAction6 = drive6.build();

         */
        Action trajectoryAction1 = drive1.build();
        Action trajectoryAction2 = drive2.build();

        Actions.runBlocking(closeTopClaw);

        while (!isStarted() && !isStopRequested()) {
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Autonomous", "Started");

            Actions.runBlocking(
                    new SequentialAction (
                            closeTopClaw,
                            liftToHighJunction,
                            flipTClawOut,
                            trajectoryAction1,
                            liftToLowPosition,
                            openTopClaw,
                            liftDown,
                            trajectoryAction2
                    )
            );
            telemetry.addData("Autonomous", "Complete");
            telemetry.update();
        }
    }
}