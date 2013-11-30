
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
        String[] filenames = { 
                 "test/test.tats"

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
            
            // name binding
            VisitorBinder vb = new VisitorBinder(spec);
            vb.bindName();            
   
            // generating
            JsonGen js = new JsonGen();
            
            String SatsCvt = classname + "_cvt.sats";
            String DatsCvt = classname + "_cvt.dats";
            String DatsImpl = classname + "_cvt_impl.dats";
            
            String output = js.trans("staload \"" + SatsCvt + "\"", spec);

            System.out.println("==program is ==========================");
            System.out.println(output);

            FileWriter fwINS = new FileWriter("test/" + DatsCvt);
            BufferedWriter bwINS = new BufferedWriter(fwINS);
            bwINS.write(output);
            bwINS.close();
            
            String output2 = js.getSATS("", spec);
            FileWriter fwINS2 = new FileWriter("test/" + SatsCvt);
            BufferedWriter bwINS2 = new BufferedWriter(fwINS2);
            bwINS2.write(output2);
            bwINS2.close();
            
            String output3 = js.getDATS("staload \"" + SatsCvt + "\"", spec);
            FileWriter fwINS3 = new FileWriter("test/" + DatsImpl);
            BufferedWriter bwINS3 = new BufferedWriter(fwINS3);
            bwINS3.write(output3);
            bwINS3.close();

            /* ******** ******** */

            System.out.println("\n" + "==" + filename + " is O.K. "
                            + "\n==============================================================================\n");
        }

    }

}
