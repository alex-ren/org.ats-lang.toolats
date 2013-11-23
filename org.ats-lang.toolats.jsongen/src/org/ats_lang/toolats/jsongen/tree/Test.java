package org.ats_lang.toolats.jsongen.tree;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.ats_lang.toolats.jsongen.parser.ATSTypeLexer;
import org.ats_lang.toolats.jsongen.parser.ATSTypeParser;
import org.ats_lang.toolats.jsongen.utils.FilenameUtils;

public class Test {

    /**
     * @param args
     * @throws RecognitionException
     * @throws IOException
     */
    public static void main(String[] args) throws RecognitionException,
            IOException {
        String[] filenames = { "test/test.tats"

        };

        for (String filename : filenames) {
            System.out.println("==Processing file " + filename + "==========");

            ANTLRFileStream fileStream = new ANTLRFileStream(filename);

            File file = new File(filename);
            String classname = FilenameUtils.removeExtension(file.getName());

            /* ******** ******** */
            // lexing
            ATSTypeLexer lexer = new ATSTypeLexer(fileStream);
            TokenStream tokenStream = new CommonTokenStream(lexer);
            // System.out.println(tokenStream.toString());

            /* ******** ******** */
            // parsing
            ATSTypeParser parser = new ATSTypeParser(tokenStream);
            ATSTypeSpec spec = parser.rule();
            JsonGen js = new JsonGen();
            String output = js.trans(spec);

            System.out.println("==program is ==========================");
            System.out.println(output);

            FileWriter fwINS = new FileWriter("test/" + classname + ".cvt");
            BufferedWriter bwINS = new BufferedWriter(fwINS);
            bwINS.write(output);
            bwINS.close();

            /* ******** ******** */

            System.out.println("\n" + "==" + filename + " is O.K. "
                            + "\n==============================================================================\n");
        }

    }

}
