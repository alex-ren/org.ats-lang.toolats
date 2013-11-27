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

public class TestATSD2E {

    /**
     * @param args
     * @throws RecognitionException
     * @throws IOException
     */
    public static void main(String[] args) throws RecognitionException,
            IOException {
        String[] filenames = { 
                // "test/test.tats"
                "/home/grad2/aren/workspace/Postiats/ATS-Postiats/libatsyn2json/DATS/libatsyn2json_cvt.tats"

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
            String output = js.trans("staload \"../SATS/libatsyn2json_cvt.sats\"", spec);

            System.out.println("==program is ==========================");
            System.out.println(output);

//            FileWriter fwINS = new FileWriter("test/" + classname + ".cvt");
            FileWriter fwINS = new FileWriter("/home/grad2/aren/workspace/Postiats/ATS-Postiats/libatsyn2json/DATS/libatsyn2json_cvt.dats");
            BufferedWriter bwINS = new BufferedWriter(fwINS);
            bwINS.write(output);
            bwINS.close();
            
            String output2 = js.getSATS("", spec);
            FileWriter fwINS2 = new FileWriter("/home/grad2/aren/workspace/Postiats/ATS-Postiats/libatsyn2json/SATS/libatsyn2json_cvt.sats");
            BufferedWriter bwINS2 = new BufferedWriter(fwINS2);
            bwINS2.write(output2);
            bwINS2.close();
            
            String output3 = js.getDATS("staload \"../SATS/libatsyn2json_cvt.sats\"", spec);
            FileWriter fwINS3 = new FileWriter("/home/grad2/aren/workspace/Postiats/ATS-Postiats/libatsyn2json/DATS/libatsyn2json_cvt_impl.dats");
            BufferedWriter bwINS3 = new BufferedWriter(fwINS3);
            bwINS3.write(output3);
            bwINS3.close();

            /* ******** ******** */

            System.out.println("\n" + "==" + filename + " is O.K. "
                            + "\n==============================================================================\n");
        }

    }

}
