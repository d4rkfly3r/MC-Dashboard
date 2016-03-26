package com.github.d4rkfly3r.mcdash.src.util;

import com.github.d4rkfly3r.mcdash.src.MainClass;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Created by Joshua on 3/26/2016.
 * Project: MCDash
 */
public class FileResponse {
    private final HashMap<String, Object> context;
    byte[] data;

    public FileResponse(String filename, HashMap<String, Object> context) throws IOException {
        this.context = context;
        data = Files.readAllBytes(Paths.get(MainClass.CONSTANTS.SITE_WORKING_DIR, filename));
    }

    public byte[] getData() {
        StringWriter stringWriter = new StringWriter();
        Mustache mustache = new DefaultMustacheFactory().compile(new StringReader(new String(data)), "Server File");
        mustache.execute(stringWriter, context);
        return stringWriter.toString().getBytes();
    }
}
