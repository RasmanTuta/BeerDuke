package no.java.beerduke.control;

import no.rasmantuta.ts7800.TS_DIO64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutomatController {
	private int numSlots;
	private int robin = 0;
	private final boolean perform;
    private final CountersReader counters;
    private static Logger logger = LoggerFactory.getLogger(AutomatController.class);

	public AutomatController(int numSlots, CountersReader counters){
		this.numSlots = numSlots;
        this.counters = counters;
        perform = !(System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0) ;
	}
	
	public void resetButtons(){
		if(perform) {
			TS_DIO64.setOutput(0, 0xffffffff, 0x0);
		}
	}
	
	public int[] giveBeer(int slot){

        int actualSlot = slot == 0 ? robin : slot - 1;
		int output = 1 << actualSlot;

		if(perform) {
			TS_DIO64.setOutput(0, 0xffffffff, output);
		}
		logger.info("Giving beer from slot " + (actualSlot + 1));
		if(slot == 0) {
            robin = ++robin % numSlots;
        }
        return counters.incrementCounter(actualSlot);
	}
	
	public void giveAlarm(){
		if(perform) {
			TS_DIO64.setOutput(0, 0xffffffff, 0x400);
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				logger.warn("Interrupted", e);
			} finally {
				resetButtons();
			}
		}
	}
}
