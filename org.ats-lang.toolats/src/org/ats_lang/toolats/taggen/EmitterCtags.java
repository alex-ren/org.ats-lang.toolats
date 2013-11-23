package org.ats_lang.toolats.taggen;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;


import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;


public class EmitterCtags implements TagEmitter {

    private STGroup m_stg;
    private TagFileReader m_reader;
    
    public EmitterCtags() {
        m_reader = new TagFileReader();
        URL fileURL = this.getClass().getResource("/org/ats_lang/toolats/taggen/ctags.stg");
        m_stg = new STGroupFile(fileURL, "ascii", '<', '>');
    }

    @Override
    public String fromJSONReader(Reader input) throws IOException {
        TagFile[] tagfs = m_reader.fromJSONReader(input);
        
        ST stTagFile = m_stg.getInstanceOf("ctags_file_st");
        
        for (TagFile tagfile: tagfs) {
            String file = tagfile.getTagfile();
            TagEntry[] tags = tagfile.getTagentarr();
            for (TagEntry tag: tags) {
                ST tagline = m_stg.getInstanceOf("tag_line_st");
                tagline.add("name", tag.getName());
                tagline.add("file", file);
                tagline.add("address", tag.getNline());
                
                stTagFile.add("taglines", tagline);
            }
            
        }
        return stTagFile.render();
    }
}
