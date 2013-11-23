package org.ats_lang.toolats.taggen;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class TagFileReader {
    private Gson m_gson;
    
    public TagFileReader() {
        m_gson = new Gson();
    }
    
    public TagFile[] fromJSONReader(Reader input) throws IOException {
        JsonReader reader = new JsonReader(input);
        reader.setLenient(true);
        List<TagFile> tagfs = new ArrayList<TagFile>();
        while (JsonToken.END_DOCUMENT != reader.peek()) {
            TagFile tagfile = m_gson.fromJson(reader, TagFile.class);
            tagfs.add(tagfile);
        }
        
        return tagfs.toArray(new TagFile[tagfs.size()]);

    }
    
//    public TagFile[] fromJSONObject(Reader input) {
//        TagFile[] tagfs = m_gson.fromJson(input, TagFile[].class);
//        return tagfs;
//    }
//    
//    public TagFile[] fromJSONObject(String input) {
//        TagFile[] tagfs = m_gson.fromJson(input, TagFile[].class);
//        return tagfs;
//    }
}
