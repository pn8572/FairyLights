package com.lights.model;

import java.util.ArrayList;

public class LightCollection {
	
	private ArrayList<Light> lightsArray;
	private String[] listOfColours;
	
    /**
     * LightCollection Constructor to create list of Lights in the order of RED,GREEN,WHITE for the number
     * of lights requested with in the noOfLights parameter.
     *
     * @param noOfLights int which determines the initial size of the ArrayList of Lights
     */
    public LightCollection(int noOfLights, String[] listOfColours) {
    	lightsArray = new ArrayList<Light>();
        this.listOfColours = listOfColours;
        for (int i = 0; i < noOfLights; i++) {
        	lightsArray.add(getColour(i));
        }
    }
    /**
     * This method returns a Light object with colour from the listOfColour Array which would be the order of the colours within the group.
     * @param i int which represent the position of light in the array list of Lights.
     * 
     * @return Returns name of the colour
     */
    public Light getColour(int i) {
        return new Light((listOfColours[i % listOfColours.length]));
    }
    
    /**
     * @return Returns the size of the LightArray.
     */
    public int getLightArraySize() {
        return lightsArray.size();
    }
    
    /**
     * Changes the status of light.
     * @param pos location of light within the lights array.
     */
    public void changeLight(int pos) {
        if (pos < lightsArray.size()) {
            Light li = lightsArray.get(pos);
            li.switchLight();
            System.out.println("Light " + (pos + 1) + " " + li.toString());
        }
    }
    
    /**
     * changes the status of the light for a single set of colours
     * @param colour String, the colour of the light.
     */
    public void switchColourLight(String colour) {
        for (int i : getColoursPos(colour)) {
            changeLight(i);
        }
    }
    
    /**
     * Turn off all lights with in the array of lights
     */
    public void turnOffAllTheLights() {
        for (Light li : lightsArray) {
            li.turnOffLight();
        }
    }
    /**
     * Finds a sub-set of Lights within the array of a certain colour.
     *
     * @param colour The Colour you want to search for in the LightArray List
     * @return Returns an Array of int of the position within the larger array
     */
    public ArrayList<Integer> getColoursPos(String colour) {
        ArrayList<Integer> colourPos = new ArrayList<Integer>();
        //check if colour exist reduces loop
        if (colourInList(colour)) {
            for (int i = 0; i < lightsArray.size(); i++) {
                Light li = lightsArray.get(i);
                if (li.isThisColour(colour)) {
                    colourPos.add(i);
                }
            }
        }
        return colourPos;
    }
    
    /**
     * Check if the specific colour exist with the array.
     *
     * @param colour the colour searching for
     * @return true if found.
     */
    public boolean colourInList(String colour) {
        for (String col : listOfColours) {
            if (colour.toLowerCase().equals(col.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
