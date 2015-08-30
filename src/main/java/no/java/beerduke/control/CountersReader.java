package no.java.beerduke.control;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by kristian on 30.08.2015.
 */
public class CountersReader {
    private static Logger logger = LoggerFactory.getLogger(CountersReader.class);

    private final File countersFile;

    public CountersReader(File countersFile) {
        this.countersFile = countersFile;
        if(!countersFile.exists()){
            try {
                countersFile.createNewFile();
            } catch (IOException e) {
                logger.warn("Could not create countersFile", e);
            }
            resetCounters();
        }
    }

    public int[] readCounters(){
        BufferedReader input = null;
        try {
            input = new BufferedReader(new InputStreamReader(new FileInputStream(countersFile), "utf-8"));
            final String line = input.readLine();
            final String[] countersStrings = line.split(";");
            int[] counters = new int[countersStrings.length];
            for (int i = 0; i < countersStrings.length; i++) {
                counters[i] = Integer.parseInt(countersStrings[i]);
            }
            return counters;
        } catch (Exception e) {
            logger.warn("Failed reading from countersFile", e);
            throw new RuntimeException("Failed reading from countersFile", e);
        }
        finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }

    }

    public int[] incrementCounter(int counter){
        final int[] counters = readCounters();
        counters[counter] = ++counters[counter];
        return writeCounter(counters);
    }

    public int[] writeCounter(int[] counters){
        String countersString = StringUtils.join(ArrayUtils.toObject(counters), ";");
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(countersFile, false), "utf-8"));
            output.write(countersString);
        } catch (Exception e) {
            logger.warn("Failed reading from countersFile", e);
            throw new RuntimeException("Failed reading from countersFile", e);
        }
        finally {
            if (output != null)
                try {
                    output.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
        return readCounters();
    }

    public int[] resetCounters(){
        writeCounter(new int[]{0,0,0,0,0,0,0,0,0,0});
        return readCounters();
    }


}
