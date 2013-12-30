package org.ats_lang.toolats.jsongen.example1;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;



public class ExpTest1 {
    
    public List<Integer> transListInt(JsonReader reader) throws IOException {
        List<Integer> xs = new ArrayList<Integer>();

        reader.beginArray();
        while (reader.hasNext()) {
            xs.add(reader.nextInt());
        }
        reader.endArray();

        System.out.print("[");
        for (int i = 0; i < xs.size() - 1; ++i) {
            System.out.print(xs.get(i) + ", ");
        }
        System.out.print(xs.get(xs.size() - 1));
        System.out.print("]");

        System.out.println();

        // while (JsonToken.END_DOCUMENT != reader.peek()) {
        //
        // }
        return xs;

    }
    
    public static class RecordX {
        public Integer m_x;
        public String m_y;
        
        public RecordX(int x, String y) {
            m_x = x;
            m_y = y;
        }
        
        public String toString() {
            String ret;
            ret = "{";
            ret += "\"x\": " + m_x;
            ret += ", ";
            ret += "\"y\": " + "\"" + m_y + "\"";
            ret += "}";
            return ret;
        }
    }
    
    public RecordX transRecord(JsonReader reader) throws IOException {
        Integer x = null;
        String y = null;
        
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("x")) {
                x = reader.nextInt();                
            } else if (name.equals("y")) {
                y = reader.nextString();
            } else {
                throw new Error("type not match");
            }
        }
        reader.endObject();
        
        RecordX rx = new RecordX(x, y);
        System.out.println(rx.toString());
        return rx;

    }
    
    public static interface T2 {
    }

    public static class T2_cons1 implements T2 {
        // public 

    }
    

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        ExpTest1 test1 = new ExpTest1();
        
        String input = "[1, 2, 3]";
        input += "{\"x\": 3, \"y\": \"ok\"}";
        
        Reader sReader = new StringReader(input);;
        JsonReader reader = new JsonReader(sReader);
        reader.setLenient(true);
                
        test1.transListInt(reader);
        System.out.println();

        test1.transRecord(reader);
        System.out.println();
        
    }
    
//  FileReader fReader = null;;

//  FileWriter fw = null;
//  
//  try {
//      File file = new File("test/json.txt");
//      fReader = new FileReader(file);
//  } catch (IOException e) {
//      e.printStackTrace();
//      throw e;
//  } finally {
//      if (null != fReader) {
//          fReader.close(); // Redundant close won't cause any trouble.
//      } 
//
//      if (null != fw) {
//          fw.close();
//      } 
//  }


}













