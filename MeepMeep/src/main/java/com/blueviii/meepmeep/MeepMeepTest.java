package com.blueviii.meepmeep;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTest {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);
        double halfWidth = 7.4375;
        double halfLength = 8.125;
        Pose2d initPosRedSample = new Pose2d(-24+halfWidth, -72 + halfLength, Math.toRadians(0));

        RoadRunnerBotEntity redSampleBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.5)
                .build();

        redSampleBot.runAction(redSampleBot.getDrive().actionBuilder(initPosRedSample)
                .splineToLinearHeading(new Pose2d(-40 -halfWidth, -55 + halfLength, Math.toRadians(225)),Math.toRadians(225),
                        null, new ProfileAccelConstraint(-80, 80))
                        .lineToX(-55)
                        .setTangent(Math.toRadians(45))
                        .lineToY(-40)
                        .turn(Math.toRadians(-135))
                        .strafeTo(new Vector2d(-48,-48+halfLength))
                        .turn(Math.toRadians(180))
                        .lineToY(-60+halfLength)
                        .turn(Math.toRadians(-45))
                        .lineToX(-55)
                .build());

        RoadRunnerBotEntity redSpecBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.5)
                .build();

        Pose2d initPosRedSpec = new Pose2d(24-halfWidth, -72 + halfLength, Math.toRadians(90));
        redSpecBot.runAction(redSpecBot.getDrive().actionBuilder(initPosRedSpec)
                .strafeTo(new Vector2d(-5 + halfWidth, -40 + halfLength))
                .lineToY(-35)
                .setTangent(Math.toRadians(180))
                .strafeTo(new Vector2d(35, -35), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(-10)
                .setTangent(Math.toRadians(180))
                .lineToX(48)
                .setTangent(Math.toRadians(90))
                .lineToY(-65+halfLength)
                .lineToY(-10)
                .setTangent(Math.toRadians(180))
                .lineToX(58)
                .setTangent(Math.toRadians(90))
                .lineToY(-65+halfLength)
                .lineToY(-10)
                .setTangent(180)
                .strafeTo(new Vector2d(50, -10))
                .turn(Math.toRadians(180))
                .setTangent(Math.toRadians(90))
                .lineToY(-70+halfLength)
                .lineToY(-45)
                .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(180))
                .lineToX(10)
                .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(-32)
                .build());

        Pose2d initPosBlueSample = new Pose2d(24-halfWidth, 72 - halfLength, Math.toRadians(225));
        RoadRunnerBotEntity blueSampleBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(270), Math.toRadians(270), 13.5)
                .build();

        blueSampleBot.runAction(blueSampleBot.getDrive().actionBuilder(initPosBlueSample)
                .splineToLinearHeading(new Pose2d(40 + halfWidth, 55 - halfLength, Math.toRadians(45)),Math.toRadians(45),
                        null, new ProfileAccelConstraint(-80, 80))
                .lineToX(55)
                .setTangent(Math.toRadians(45))
                .lineToY(40)
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(48,48+halfLength))
                .turn(Math.toRadians(90))
                .setTangent(Math.toRadians(90))
                .lineToY(60-halfLength)
                .turn(Math.toRadians(135))
                .setTangent(Math.toRadians(225))
                .lineToX(55)
                .build());

        RoadRunnerBotEntity blueSpecBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 13.5)
                .build();

        Pose2d initPosBlueSpec = new Pose2d(-24+halfWidth, 72 + halfLength, Math.toRadians(270));
        blueSpecBot.runAction(redSpecBot.getDrive().actionBuilder(initPosBlueSpec)
                .strafeTo(new Vector2d(-5 + halfWidth, 40 - halfLength))
                .lineToY(35)
                .setTangent(Math.toRadians(180))
                .strafeTo(new Vector2d(-35, 35), null, new ProfileAccelConstraint(-80, 80))
                .setTangent(Math.toRadians(90))
                .lineToY(10)
                .setTangent(Math.toRadians(180))
                .lineToX(-48)
                .setTangent(Math.toRadians(90))
                .lineToY(65-halfLength)
                .lineToY(10)
                .setTangent(Math.toRadians(180))
                .lineToX(-58)
                .setTangent(Math.toRadians(90))
                .lineToY(65-halfLength)
                .lineToY(10)
                .strafeTo(new Vector2d(-50, 10))
                .turn(Math.toRadians(180))
                .setTangent(Math.toRadians(90))
                .lineToY(70-halfLength)
                .lineToY(45)
                .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(180))
                .lineToX(-10)
                .turn(Math.toRadians(-90))
                .setTangent(Math.toRadians(90))
                .lineToY(32)
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(redSampleBot)
                .addEntity(redSpecBot)
                .addEntity(blueSampleBot)
                .addEntity(blueSpecBot)
                .start();
    }
}
