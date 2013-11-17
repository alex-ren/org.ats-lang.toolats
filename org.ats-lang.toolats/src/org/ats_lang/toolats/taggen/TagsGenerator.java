package org.ats_lang.toolats.taggen;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

public class TagsGenerator {

    public enum TagType {
        ctags, emacs
    }

    private TagType m_type;
    private String m_input;
    private String m_output;

    public void options() {
        System.out
                .println("usage: java -jar atstools.jar <command> ... <command>");

        System.out
                .println("where a <command> is of one of the following forms:");
        System.out.println();
        System.out.println("  -c, --ctags");
        System.out.println("               Generate tag file for ctags.");
        System.out.println("  -e, --emacs");
        System.out.println("               Generate tag file for emacs.");
        System.out.println("  --input filenames");
        System.out
                .println("               Specify the name of the input file.");
        System.out.println("  --output filenames");
        System.out
                .println("               Specify the name of the output file.");
        System.out.println();

    }

    public TagsGenerator() {
        m_type = null;
        m_input = null;
        m_output = null;
    }

    public int parseArgv(String argv[]) {
        for (int i = 0; i < argv.length; ++i) {
            String arg = argv[i];

            if (arg.equals("-c") || arg.equals("--ctags")) {
                m_type = TagType.ctags;
            } else if (arg.equals("-e") || arg.equals("--emacs")) {
                m_type = TagType.emacs;
            } else if (arg.equals("--input")) {
                if ((i + 1) >= argv.length) {
                    System.out
                            .println("Error: Please specify the name of the input file.\n");
                    options();
                    return -1;
                } else {
                    m_input = argv[i + 1];
                    i = i + 1;
                }
            } else if (arg.equals("--output")) {
                if ((i + 1) >= argv.length) {
                    System.out
                            .println("Error: Please specify the name of the output file.\n");
                    options();
                    return -1;
                } else {
                    m_output = argv[i + 1];
                    i = i + 1;
                }
            } else {
                System.out.println("unknow options");
                options();
                return -1;
            }
        }

        if (null == m_type || null == m_input) {
            System.out.println("Error: Please provide more information.\n");
            options();
            return -1;
        }

        return 0;
    }

    public void emit() throws IOException {
        FileReader fReader = null;;
        StringReader sReader = null;;
        FileWriter fw = null;
        
        try {
            File file = new File(m_input);
            fReader = new FileReader(file);

            TagEmitter emitter = null;
            if (TagType.ctags == m_type) {
                emitter = new EmitterCtags();
            } else {
                emitter = new EmitterEtags();
            }
            
            String ctags = emitter.fromJSONReader(fReader);
            // System.out.println(ctags);

            if (null == m_output) {
                System.out.println(ctags);
            } else {
                fw = new FileWriter(m_output);
                fw.write(ctags);
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (null != sReader) {
                sReader.close();
            } 
            
            if (null != fReader) {
                fReader.close(); // Redundant close won't cause any trouble.
            } 

            if (null != fw) {
                fw.close();
            } 
        }

    }

    public static void main(String argv[]) throws IOException {
        TagsGenerator tg = new TagsGenerator();
        if (tg.parseArgv(argv) != 0) {
            return;
        }

        tg.emit();

    }
}
