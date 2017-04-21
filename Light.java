/**
 * 
 */
package com.lights.model;

/**
 * @author prasanth
 *
 */
public class Light {
	
	 /**
     * @return Returns the Lights' status (True = ON / False = OFF)
     */
	public boolean getStatus() {
		return status;
	}
	
	/**
     * @param status Boolean which set Lights' status (True = ON / False = OFF)
     */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	 /**
     * @return Return the LightBulb Object as A String example. Red ON
     * @see Object
     */
    @Override
    public String toString() {
        return colour + " " + getStrStatus();
    }
	
	 /**
	 * @return Returns the Colour of the LightBulb.
	 */
	public String getColour() {
		return colour;
	}
	/**
     * @param colour String that sets the colour of the Light.
     */
	
	public void setColour(String colour) {
		this.colour = colour;
	}
	private boolean status; //Status of Light. True= ON and False=OFF
    private String colour; //Colour of the Light as a String literal. example "RED"
    
    
    /**
     * Light constructor (Default)
     * Creates a Light Object which is RED and status is OFF.
     */
    public Light() {
		this.status = false;
		this.colour = "RED";
	}
    
    /**
     * Light constructor
     * Creates a Light Object with passed in parameter for colour and status
     *
     * @param colour Colour of the bulb.
     * @param state  set the state bulb to ON or OFF
     */
	public Light(boolean status, String colour) {
		super();
		this.status = status;
		this.colour = colour;
	}
	
	 /**
     * Light constructor
     * Creates a Light Object which is switched off and bulb colour is defined by colour parameter.
     *
     * @param colour Colour of the Bulb.
     */
    public Light(String colour) {
    	this.status = false;
        this.colour = colour;
    }
    
    /**
     * @param col String giving the name of colour.
     * @return Returns a Boolean (True = they are the same colour / False = not the same colour)
     */
    public boolean isThisColour(String col) {
        return colour.toLowerCase().equals(col.toLowerCase());
    }
    
    /**
     * Flick the lightbulb state ON if it OFF or OFF if its ON
     */
    public void switchLight() {
        if (this.status) 
            turnOffLight();
         else 
        	turnOnLight();
    }
    
    /**
     * @return Returns a String with the text version of the boolean being ON or OFF
     */
    public String getStrStatus() {
        return (this.status) ? "on" : "off";
    }
    
    /**
     * Turn OFF the LightBulb
     */
    public void turnOffLight() {
        setStatus(false);
    }

    /**
     *Turn ON the LightBulb
     */
    public void turnOnLight() {
    	setStatus(true);
    }
}
