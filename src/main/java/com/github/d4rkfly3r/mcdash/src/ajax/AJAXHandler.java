package com.github.d4rkfly3r.mcdash.src.ajax;

import com.github.d4rkfly3r.mcdash.src.MCDashThreadGroup;

public class AJAXHandler extends Thread {
    public AJAXHandler() {
        super(MCDashThreadGroup.getInstance(), "AJAX Handler Thread");
    }
}
