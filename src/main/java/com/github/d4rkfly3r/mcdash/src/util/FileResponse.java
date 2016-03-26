package com.github.d4rkfly3r.mcdash.src.util;

import com.github.d4rkfly3r.mcdash.src.MainClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Joshua on 3/26/2016.
 * Project: MCDash
 */
public class FileResponse {
    byte[] data;

    public FileResponse(String filename) throws IOException {
        data = Files.readAllBytes(Paths.get(MainClass.CONSTANTS.SITE_WORKING_DIR, filename));
    }

    public byte[] getData() {
        return data;
    }
}
