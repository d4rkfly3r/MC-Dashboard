package com.github.d4rkfly3r.mcdash.src;

public class MCDashThreadGroup extends ThreadGroup {
    private static MCDashThreadGroup ourInstance = new MCDashThreadGroup();

    private MCDashThreadGroup() {
        super("MC Dashboard ThreadGroup");
    }

    public static MCDashThreadGroup getInstance() {
        return ourInstance;
    }
}
