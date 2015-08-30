package no.java.beerduke.rfid;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;

import com.phidgets.Phidget;
import com.phidgets.PhidgetException;
import com.phidgets.RFIDPhidget;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.TagGainEvent;
import com.phidgets.event.TagGainListener;
import com.phidgets.event.TagLossEvent;
import com.phidgets.event.TagLossListener;



public class RfIdReader {
	private RFIDPhidget rfid;
	private Person mostRecentPerson;
	private LinkedList mostRecentPersons = new LinkedList();
	private AutomatController controller;
	private String personsFileName;
	private Map persons;

	public RfIdReader(AutomatController controller, String personsFileName) {
		this.controller = controller;
		this.personsFileName = personsFileName;

		loadPersons();

		try {
			rfid = new RFIDPhidget();
			rfid.addAttachListener(new AttachListener() {
				public void attached(AttachEvent ae) {
					try {
						((RFIDPhidget) ae.getSource()).setAntennaOn(true);
					} catch (PhidgetException ex) {
					}
					System.out.println("attachment of " + ae);
				}
			});
			rfid.addTagGainListener(new TagGainListener() {
				public void tagGained(TagGainEvent oe) {
					performTagGained(oe);
				}
			});
			rfid.addTagLossListener(new TagLossListener() {
				public void tagLost(TagLossEvent oe) {
					performTagLoss(oe);
				}
			});
			rfid.openAny();
		} catch (PhidgetException e) {

		}
	}

	private void performTagGained(TagGainEvent oe) {
		String id = oe.getValue();
		System.out.println("rfid: " + id);
		try {
			((RFIDPhidget) oe.getSource()).setLEDOn(true);
			((RFIDPhidget) oe.getSource()).setOutputState(1, true);
		} catch (PhidgetException e) {
		}
		Person person = (Person) persons.get(id.toLowerCase());
		System.out.println();
		
		if(null == person || person.isHero() == false){
			controller.giveAlarm();
			if (null!= person)
				person.incrementRejectedCount();
		}else{
			controller.giveBeer(0);
			person.incrementBeerCount();
		}
		
		if(null != person)
			updateRecent(person);
		
	}
	private void performTagLoss(TagLossEvent oe){
		try {
			((RFIDPhidget) oe.getSource()).setLEDOn(false);
			((RFIDPhidget) oe.getSource()).setOutputState(1, false);
		} catch (PhidgetException e) {
		}
		controller.resetButtons();
	}

	private void loadPersons() {
		persons = PersonImporter.loadPersons(new File(personsFileName));
		System.out.println("Loaded " + persons.size() + " persons.");
	}

	public String getPhidgetLibraryVersion() {
		return Phidget.getLibraryVersion();
	}

	public String getPhidgetName() {
		String ret = "No Phidget attached!";
		try {
			ret = rfid.getDeviceName();
		} catch (PhidgetException e) {
			// return default
		}
		return ret;
	}

	public int getPhidgetSerial() {
		int ret = 0;
		try {
			ret = rfid.getSerialNumber();
		} catch (PhidgetException e) {
			// return default
		}
		return ret;
	}

	private void updateRecent(Person person){
		if(null != mostRecentPerson){
			mostRecentPersons.addFirst(mostRecentPerson);
			if(mostRecentPersons.size() > 10)
				mostRecentPersons.removeLast();			
		}
		mostRecentPerson = person;

	}

	public Person getMostRecentPerson() {
		return mostRecentPerson;
	}

	public LinkedList getMostRecentPersons() {
		System.out.println("mostRecent size: " + mostRecentPersons.size());
		return mostRecentPersons;
	}
	
}
