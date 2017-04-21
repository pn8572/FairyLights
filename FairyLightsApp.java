package com.lights.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.lights.model.LightCollection;

public class FairyLightsApp {
	
	/*
	 * All the below values are initialised with default values. 
	 * But the actual values will be retrieved from property file.
	 */
    public static final String SEQ = "sequence";
    public static final String COL = "colour";
    public static final String ALT = "alternate";

    public static int NO_LIGHTS = 20;
    public static String CURRENT_CONTROLLER =  "sequence";
    public static String[] COL_LIST = new String[]{"red","green","white"};

    public static int SEQ_MILLI_TIME = 500;
    public static int COL_MILLI_TIME = 1000;
    public static int ALT_MILLI_TIME = 30000;
    public static String[] listOfColours;
    public static String listOfStrColours;
    public static LightCollection arrayListOfLights;

    public FairyLightsApp(){
    }
    /**
     * Main function - Entry point to the light demo application
     */
    public static void main(String[] args) {
    	FairyLightsApp m = new FairyLightsApp();
    	List<String> myColourList = new ArrayList<String>();
    	Properties prop = m.loadConfigProperties();
    	NO_LIGHTS = Integer.parseInt(prop.getProperty("NO_LIGHTS"));
    	CURRENT_CONTROLLER = prop.getProperty("CURRENT_CONTROLLER");
    	SEQ_MILLI_TIME = Integer.parseInt(prop.getProperty("SEQ_MILLI_TIME"));
    	COL_MILLI_TIME = Integer.parseInt(prop.getProperty("COL_MILLI_TIME"));
    	ALT_MILLI_TIME = Integer.parseInt(prop.getProperty("ALT_MILLI_TIME"));
    	String listOfStrColours = prop.getProperty("COLOURS_LIST");
    	myColourList = new ArrayList<String>(Arrays.asList(listOfStrColours.split(",")));
    	String[] listOfColours = myColourList.toArray(new String[myColourList.size()]);
    	COL_LIST = listOfColours;
        LightCollection arrayListOfLights = new LightCollection(NO_LIGHTS, COL_LIST);
        executeController(CURRENT_CONTROLLER, arrayListOfLights, listOfColours);
    }
    
    /**
     * This method will execute the fairy light program for the selected algorithm
     * @param CURRENT_CONTROLLER
     * @param arrayListOfLights
     * @param listOfColours
     */
    public static void executeController(String CURRENT_CONTROLLER, LightCollection arrayListOfLights,
			String[] listOfColours) {
    	
    	Thread lightThread;
        if(CURRENT_CONTROLLER.equalsIgnoreCase("sequence")) {
        
	        					//1.	'sequence' : each light is turned on for 0.5 seconds then off in turn from first to last.
					        	System.out.println("Starting Sequence Controller Algorithm");
					            lightThread = new Thread(new SequenceController(arrayListOfLights, SEQ_MILLI_TIME));
					            lightThread.start();
	        				 }
        else if(CURRENT_CONTROLLER.equalsIgnoreCase("colour")) {
					        	//2.	'colour' :  all of the red lights are turned on for 1 second, then all the green for 1 second then all the white for 1 second.
				                System.out.println("Starting Colour Controller Algorithm:");
				                lightThread = new Thread(new ColourController(arrayListOfLights, COL_MILLI_TIME, listOfColours));
				                lightThread.start();
					         }
	        	
        else if(CURRENT_CONTROLLER.equalsIgnoreCase("alternate")) {
					        	//3.	'alternate' : the 'sequence' algorithm runs for 30 seconds, then the 'colour' algorithm for 30 seconds.
				                System.out.println("Starting Alternative Controller Algorithm:");
				                lightThread = new Thread(new AlternativeController(arrayListOfLights, ALT_MILLI_TIME));
				                lightThread.start();
					         }
        else 				{
					        	System.out.println("New Controller");
					            System.out.println("Future Implementation");
					        }
	}
	/**
     * This method is used to load the properties files. In order to change the default behaviour of the application, the equivalent 
     * attributes in the property file can be amended
     * 
     * @return prop Properties instance
     */

    private Properties loadConfigProperties() {
    	Properties prop = new Properties();
    	InputStream in = null;
    	try {
    		in = this.getClass().getClassLoader().getResourceAsStream("resources/config.properties");
    		prop.load(in);
    	} catch (IOException ex) {
    		ex.printStackTrace();
    	} finally {
    		if (in != null) {
    			try {
    				in.close();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    	return prop;
	}


	/**
     * Abstract ParentController algorithm Class.
     * This abstract class has the bare minimum attributes and the run() which needs to be overridden by the subclasses
     */
    private abstract static class ParentController implements Runnable {
        private LightCollection lightArr;
        private int time; 

        public int getTime() {
			return time;
		}

		public LightCollection getLightArr() {
			return lightArr;
		}

		/**
         * ParentController constructor
         * 
         * @param li lights Array that will be manipulated with this programme.
         * @param t time in millisec used to delay of switching light ON or OFF.
         */
        public ParentController(LightCollection li, int t) {
            lightArr = li;
            time = t;
        }

        /**
         * Override this method in the subclasses
         */
        public void run() {
        }
    }

    /**
     * Subclass implementation of abstract class ParentController.
     * Covers the functionality for sequence controller
     */
    private static class SequenceController extends ParentController {
    	
        private int sequenceNumber = 0;
        /**
         * @param li see ParentController
         * @param t see ParentController
         * @see ParentController
         */
        public SequenceController(LightCollection li, int t) {
            super(li, t);
        }

        /**
         *@see ParentController
         */
        public void run() {
            try {
                while (true) {
                    if (sequenceNumber > super.getLightArr().getLightArraySize()) {
                        sequenceNumber = 0;
                    }
                    super.getLightArr().changeLight(sequenceNumber);//this is to switch ON the light
                    Thread.sleep(super.getTime());
                    super.getLightArr().changeLight(sequenceNumber);//this is to switch OFF the light after 0.5 sec
                    sequenceNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Subclass implementation of abstract class ParentController.
     * Covers the functionality for colour controller
     */
    private static class ColourController extends ParentController {
        private String[] listOfColours;

        /**
         * @param colourList set the colour you wish to alternate between with this controller
         * @see ParentController
         */
        public ColourController(LightCollection li, int t, String[] colourList) {
            super(li, t);
            listOfColours = colourList;
        }

        /**
         *@see ParentController
         */
        public void run() {
            try {
                while (true) {
                    for (String col : listOfColours) {
                        super.getLightArr().switchColourLight(col);//this is to switch ON the light
                        Thread.sleep(super.getTime());
                        super.getLightArr().switchColourLight(col);//this is to switch OFF the light after 1 sec
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    private static class AlternativeController extends ParentController {
        /**
         * @see ParentController
         */
        public AlternativeController(LightCollection li, int t) {
            super(li, t);
            try {
                Thread d;
                while (true) {
                    d = new Thread(new SequenceController(super.getLightArr(), SEQ_MILLI_TIME));
                    d.start();
                    Thread.sleep(super.getTime());//delay of 30 sec
                    super.getLightArr().turnOffAllTheLights();
                    d = new Thread(new ColourController(super.getLightArr(), COL_MILLI_TIME, COL_LIST));
                    d.start();
                    Thread.sleep(super.getTime());//delay of 30 sec
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
