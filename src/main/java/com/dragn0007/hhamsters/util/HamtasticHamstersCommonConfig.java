package com.dragn0007.hhamsters.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class HamtasticHamstersCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<Integer> HAMSTER_FED_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> HAMSTER_STAND_TICK;
    public static final ForgeConfigSpec.ConfigValue<Integer> HAMSTER_MAX_STAND_TICK;
    public static final ForgeConfigSpec.BooleanValue HAMSTERS_FIGHT;

    static {
        BUILDER.push("Configs for Hamtastic Hamsters!");

        HAMSTER_STAND_TICK = BUILDER.comment("Minimum amount of time, in ticks, it takes a hamster to stand up on it's hind legs. Default is 3600.")
                .define("Hamster Min Stand Tick", 3600);

        HAMSTER_MAX_STAND_TICK = BUILDER.comment("Possible added amount of time, in ticks, it takes a hamster to stand up on it's hind legs. Default is +3600.")
                .define("Hamster Additive Stand Tick", 3600);

        HAMSTER_FED_TICK = BUILDER.comment("Amount of time, in ticks, that a hamster's cheeks will stay puffed for after being fed. Default is 2400.")
                .define("Hamster Puffy Cheeks Tick", 2400);

        HAMSTERS_FIGHT = BUILDER.comment("Should tamed hamsters of the same gender fight if allowed near one another?")
                .define("Hamsters Fight", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
