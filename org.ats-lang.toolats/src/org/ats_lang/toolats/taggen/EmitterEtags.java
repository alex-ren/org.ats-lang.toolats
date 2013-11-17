package org.ats_lang.toolats.taggen;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class EmitterEtags implements TagEmitter {
    private STGroup m_stg;
    private TagFileReader m_reader;
    
    public EmitterEtags() {
        m_reader = new TagFileReader();
        URL fileURL = this.getClass().getResource("/org/ats_lang/toolats/taggen/etags.stg");
        m_stg = new STGroupFile(fileURL, "ascii", '<', '>');
    }


    
    public String fromJSONReader(Reader input) throws IOException {
        TagFile[] tagfs = m_reader.fromJSONReader(input);
        
        ST stFiles = m_stg.getInstanceOf("etags_file_st");
        
        for (TagFile tagfile: tagfs) {
            ST stFile = m_stg.getInstanceOf("tag_file_st");
            String filename = tagfile.getTagfile();
            // tag_file_st(name, length, entries) ::= <<
            stFile.add("name", filename);
            
            /* ******** **********/
            
            File file = new File(filename);
            FileReader r = new FileReader(file);
            LineNumberReader lnr = new LineNumberReader(r);
            List<String> lines = new ArrayList<String>();
            
            String line = null;
            while (null != (line = lnr.readLine())) {
                lines.add(line);
            }
            lnr.close();
            
            /* ******** **********/
            
            TagEntry[] tags = tagfile.getTagentarr();
            
            ST stEntries = m_stg.getInstanceOf("tag_entries_st");
            for (TagEntry tag: tags) {
                ST stEntry = m_stg.getInstanceOf("tag_entry_st");
                
                // read line
                String content = lines.get(tag.getNline());
                
                // tag_entry_st(content, tagname, lineno, offset) ::= <<
                stEntry.add("content", content);
                stEntry.add("lineno", tag.getNline() + 1);
                stEntry.add("offset", tag.getNchar());
                
                stEntries.add("entries", stEntry);
            }
            
            stFile.add("entries", stEntries);
            stFile.add("length", stEntries.render().length());
            
            stFiles.add("files", stFile);
        }
        
        return stFiles.render();
    }
}
