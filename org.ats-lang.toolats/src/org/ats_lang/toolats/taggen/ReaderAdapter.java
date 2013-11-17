package org.ats_lang.toolats.taggen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class ReaderAdapter {

    // replace all the '\014' with new line
    // reader: will be closed if succeeded
    // capacity: e.g. length of the whole file
//  sReader = ReaderAdapter.createStringReader(fReader,
//  (int) file.length());
    static public StringReader createStringReader(Reader reader, int capacity) throws IOException {
        String nl = System.getProperty("line.separator");

        StringBuilder fileContents = new StringBuilder(capacity); // just
                                                                  // approximation

        BufferedReader input = new BufferedReader(reader);
        String line = null;
        try {
            while ((line = input.readLine()) != null) {
                if (!line.equals("\014")) {
                    fileContents.append(line);
                    fileContents.append(nl);
                } else {
                    fileContents.append(nl);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw e;
        } finally {
            input.close();
        }

        return new StringReader(fileContents.toString());
    }

}
