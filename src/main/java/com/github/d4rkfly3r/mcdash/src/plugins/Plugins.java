package com.github.d4rkfly3r.mcdash.src.plugins;

import com.github.d4rkfly3r.mcdash.src.MCDashThreadGroup;


public class Plugins extends Thread {

    public Plugins() {
        super(MCDashThreadGroup.getInstance(), "Plugin System Thread");
    }
}
