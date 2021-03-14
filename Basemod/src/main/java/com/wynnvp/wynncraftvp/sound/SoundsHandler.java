package com.wynnvp.wynncraftvp.sound;

import com.wynnvp.wynncraftvp.ModCore;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;

public class SoundsHandler {
   // public static SoundEvent TALKING_MUSHROOM_RETURNINGARDFD;
    public HashMap<Integer, CustomSoundClass> sounds;

    public SoundsHandler() {
        sounds = new HashMap<>();
        registerSounds();
    }

    public void registerSounds(){
            //Creeper Infiltration
        addSound("[1/7] Thomas: Oh, finally, someone has come to this hidden village!", "thomas_creeperinfl_1", false);
        addSound("[1/10] Thomas: I didn't expect you to fall out of the sky like that. Are you okay, or should I go get the potion merchant to heal you?", "thomas_creeperinfl_2", false);
        addSound("[1/6] Thomas: I see you have the hide. This skin will work perfectly for my plan.", "thomas_creeperinfl_3", false);
        addSound("[1/7] Thomas: So, what was in the cave? Were there really creepers in there?", "thomas_creeperinfl_4", false);

        //Maltics Well
        addSound("[1/4] Rynend: Please help us! We called for a Ragni guard days ago, but Humans tend to forget this little village.", "malticwellrysend1", false);
        addSound("[1/1] Child: Help me! Someone! The witch is being mean!!", "malticwellchild", true);
        addSound("[1/2] Witch: I don't know why they call me a witch. Magic is everywhere, you know. This one's father made fun of my looks, so I'll turn his son into a Grook!", "malticwellwitch1", false);
        addSound("[1/2] Witch: Oh...drat. I never could get that spell right. Well, now you'll have to guess the way, um, you fool! Turn back now, for, uh...","malticwellwitch2", false);
        addSound("[1/4] Witch: Oh...didn't think that'd fool you. You know, I'm really not all that good at magic. I just like wearing pointy hats, and have warts.", "malticwellwitch3", false);
        addSound("[1/3] Rynend: Oh my son! What happened down there? Erh, not a witch, you say?", "malticwellrysend2", false);

        //Grave Digger
        addSound("[1/8] Sayleros: You sure look like a strong adventurer who could help me!", "gravediggersayleros1", false);
        addSound("[1/2] Drucksh: Mmh. g... uh! oh why hello there.", "gravediggerdrucksch1", false);
        addSound("[1/4] Drucksh: Ohh mmhhhgg... g-g-good old Nemract whiskey!", "gravediggerdrucksch2", false);
        addSound("[1/4] The Priest: Are you trying to go down into the crypt? Oh, that is a very bad idea.", "gravediggerpriest1", false);
        addSound("[1/2] Priest: You got the flesh. Good.", "gravediggerpriest2", false);
        addSound("[1/1] Sayleros: Brilliant! It is a little bit dusty, but it will do the trick! Here, take these as proof of my gratitude.", "gravediggersayleros2", false);

        //Wrath of the Mummy
        addSound("[1/6] Ormrod: Uh, you shouldn't have come here!", "wrathofthemummyormrod1", false);
        addSound("[1/6] Achper: If I know about mummies? Of course! I've spent my entire life studying them!", "wrathofthemummyachper", false);
        addSound("[1/3] Bandit: Hey, what are you doing? If you're trying to loot the tomb, me and the other Creden Tibus already beat you to it.", "wrathofthemummybandit1", false);
        addSound("[1/4] Bandit: Well, whaddya know? You got some Pink Wool! Okay, hand it here and I'll look the other way and let you into the tomb.", "wrathofthemummybandit2", false);
        addSound("[1/4] Ormrod: Uh, hello again... What are those bandages?", "wrathofthemummyormrod2", false);

        //Corrupted village
        addSound("[1/7] Orikal: Go away! I know you're angry but there's nothing to gain her-", "corruptedviallgeorikal1", false);
        addSound("[1/6] Orikal: That was quick... did you find anything?", "corruptedviallgeorikal2", false);
        addSound("[1/5] Orikal: Okay. I happen to know a recipe for creating an extremely lethal magical explosive.", "corruptedviallgeorikal3", false);
        addSound("[1/3] Orikal: You got 'em all! Excellent, this is exactly what we need.", "corruptedviallgeorikal4", false);
        addSound("[1/4] Orikal: Oh, do you feel that? It's like a buzzing in my ears I never knew about has just vanished.", "corruptedviallgeorikal5", false);
        addSound("[1/17] Roy: Well, that did the trick. Let's take a look.", "corruptedviallgeroy", false);
        addSound("[1/3] Alfonse: This is insane, why are we here?! We're looking for corruption you know that right?", "corruptedviallgealfonse", true);
      //  TALKING_MUSHROOM_RETURNINGARDFD = registerSound("talkingMushroomreturningAfterRFD");

    }


    private static SoundEvent registerSound(String name) {
        ResourceLocation location = new ResourceLocation(ModCore.MODID, name);
        SoundEvent event = new SoundEvent(location);
        event.setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

    private void addSound(String message, String name, boolean movingSound) {
        message = formatToSound(message);
        sounds.put(message.hashCode(), new CustomSoundClass(registerSound(name), movingSound));
    }

    public static String formatToSound(String message) {
        message = message.replaceAll(" ", "");
        message = message.toLowerCase();
        return message;
    }

}
