package com.dragn0007.hhamsters.compat.jade;

import com.dragn0007.dragnlivestock.compat.jade.breed.*;
import com.dragn0007.dragnlivestock.compat.jade.gender.*;
import com.dragn0007.dragnlivestock.entities.bee.OBee;
import com.dragn0007.dragnlivestock.entities.camel.OCamel;
import com.dragn0007.dragnlivestock.entities.chicken.OChicken;
import com.dragn0007.dragnlivestock.entities.cow.OCow;
import com.dragn0007.dragnlivestock.entities.horse.OHorse;
import com.dragn0007.dragnlivestock.entities.llama.OLlama;
import com.dragn0007.dragnlivestock.entities.mule.OMule;
import com.dragn0007.dragnlivestock.entities.pig.OPig;
import com.dragn0007.dragnlivestock.entities.rabbit.ORabbit;
import com.dragn0007.dragnlivestock.entities.sheep.OSheep;
import com.dragn0007.dragnlivestock.entities.unicorn.Unicorn;
import com.dragn0007.dragnlivestock.entities.util.AbstractOMount;
import com.dragn0007.hhamsters.compat.jade.breed.HamsterBreedTooltip;
import com.dragn0007.hhamsters.compat.jade.gender.HamsterGenderTooltip;
import com.dragn0007.hhamsters.entities.hamster.Hamster;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(new HamsterGenderTooltip(), Hamster.class);
        registration.registerEntityComponent(new HamsterBreedTooltip(), Hamster.class);
    }
}
