/*
 * Copyright © Team-VoW 2026.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.sound;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.wynnvp.wynncraftvp.ModCore;
import com.wynnvp.wynncraftvp.config.VOWAutoConfig;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MasterSoundRerouteTest {
    private VOWAutoConfig config;

    @BeforeEach
    void setUp() {
        config = new VOWAutoConfig();
        ModCore.config = config;
    }

    @AfterEach
    void tearDown() {
        ModCore.config = null;
    }

    @Test
    void movesMasterSoundsToBlocks() {
        SoundInstance sound = new FakeSound(SoundSource.MASTER, false);

        SoundInstance rerouted = MasterSoundReroute.apply(sound);

        assertEquals(SoundSource.BLOCKS, rerouted.getSource());
    }

    @Test
    void keepsEverythingElseAboutTheSound() {
        FakeSound sound = new FakeSound(SoundSource.MASTER, false);

        SoundInstance rerouted = MasterSoundReroute.apply(sound);

        assertEquals(sound.getIdentifier(), rerouted.getIdentifier());
        assertEquals(sound.getVolume(), rerouted.getVolume());
        assertEquals(sound.getPitch(), rerouted.getPitch());
        assertEquals(sound.getDelay(), rerouted.getDelay());
        assertEquals(sound.getX(), rerouted.getX());
        assertEquals(sound.getY(), rerouted.getY());
        assertEquals(sound.getZ(), rerouted.getZ());
        assertEquals(sound.getAttenuation(), rerouted.getAttenuation());
        assertEquals(sound.isRelative(), rerouted.isRelative());
        assertEquals(sound.isLooping(), rerouted.isLooping());
    }

    @Test
    void leavesOtherCategoriesAlone() {
        SoundInstance sound = new FakeSound(SoundSource.PLAYERS, false);

        assertSame(sound, MasterSoundReroute.apply(sound));
    }

    /** The engine looks looping sounds up by identity when they are stopped, so they must not be wrapped. */
    @Test
    void leavesLoopingMasterSoundsAlone() {
        SoundInstance sound = new FakeSound(SoundSource.MASTER, true);

        assertSame(sound, MasterSoundReroute.apply(sound));
    }

    @Test
    void leavesMasterSoundsAloneWhenDisabled() {
        config.routeMasterSoundsToBlocks = false;
        SoundInstance sound = new FakeSound(SoundSource.MASTER, false);

        assertSame(sound, MasterSoundReroute.apply(sound));
    }

    @Test
    void leavesMasterSoundsAloneBeforeTheConfigIsLoaded() {
        ModCore.config = null;
        SoundInstance sound = new FakeSound(SoundSource.MASTER, false);

        assertSame(sound, MasterSoundReroute.apply(sound));
    }

    private record FakeSound(SoundSource source, boolean looping) implements SoundInstance {
        @Override
        public Identifier getIdentifier() {
            return Identifier.withDefaultNamespace("block.note_block.harp");
        }

        @Override
        public WeighedSoundEvents resolve(SoundManager manager) {
            return null;
        }

        @Override
        public Sound getSound() {
            return null;
        }

        @Override
        public SoundSource getSource() {
            return source;
        }

        @Override
        public boolean isLooping() {
            return looping;
        }

        @Override
        public boolean isRelative() {
            return true;
        }

        @Override
        public int getDelay() {
            return 7;
        }

        @Override
        public float getVolume() {
            return 0.25F;
        }

        @Override
        public float getPitch() {
            return 1.5F;
        }

        @Override
        public double getX() {
            return 1.0;
        }

        @Override
        public double getY() {
            return 2.0;
        }

        @Override
        public double getZ() {
            return 3.0;
        }

        @Override
        public Attenuation getAttenuation() {
            return Attenuation.NONE;
        }
    }
}
