package com.dragn0007.hhamsters.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class HamtasticHamstersCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;



    static {
        BUILDER.push("Configs for Hamtastic Hamsters!");



        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
