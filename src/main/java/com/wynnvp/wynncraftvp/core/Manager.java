/*
 * Copyright © Team-VoW 2024.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynnvp.wynncraftvp.core;

import java.util.List;

/**
 * Managers are classes that manage a specific aspect of the mod.
 * This can be parsing and handling events from an aspect of Minecraft,
 * or holding data that is used throughout the mod.
 */
public abstract class Manager {
    /**
     * Create a manager with certain dependencies.
     * @param dependencies The list of managers that this manager depends on.
     *                     A manager only needs to declare dependency on another manager,
     *                     if it is used during initialization.
     *                     (Dependency does not matter when everything is initialized)
     */
    protected Manager(List<Manager> dependencies) {}
}
