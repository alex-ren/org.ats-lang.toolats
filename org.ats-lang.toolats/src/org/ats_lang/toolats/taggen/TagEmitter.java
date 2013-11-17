package org.ats_lang.toolats.taggen;

import java.io.IOException;
import java.io.Reader;

public interface TagEmitter {
    public String fromJSONReader(Reader input) throws IOException;
}
