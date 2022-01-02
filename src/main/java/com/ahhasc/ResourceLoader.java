package com.ahhasc;

import java.io.InputStream;
import java.net.URL;

public class ResourceLoader {
    public static InputStream LoadResource(String path) {
        return ResourceLoader.class.getResourceAsStream(path);
    }

    public static URL LoadURL(String path){
        return  ResourceLoader.class.getResource(path);
    }
}
