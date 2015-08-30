package no.java.beerduke.control;

import no.rasmantuta.ts7800.TS_DIO64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutomatController {
	private int numSlots;
	private int robin = 0;
	private final boolean perform;
    private static Logger logger = LoggerFactory.getLogger(AutomatController.class);

	public AutomatController(int numSlots){
		this.numSlots = numSlots;
		perform = !System.getProperty("os.name").toLowerCase().contains("windows") ;
	}
	
	public void resetButtons(){
		if(perform) {
			TS_DIO64.setOutput(0, 0xffffffff, 0x0);
		}
	}
	
	public void giveBeer(int slot){

        int actualSlot = slot == 0 ? robin : slot - 1;
		int output = 1 << actualSlot;

		if(perform) {
			TS_DIO64.setOutput(0, 0xffffffff, output);
		}
		logger.info("Giving beer from slot " + (robin + 1));
		robin = ++robin%numSlots;
	}
	
	public void giveAlarm(){
		if(perform) {
			TS_DIO64.setOutput(0, 0xffffffff, 0x400);
		}
	}
}
