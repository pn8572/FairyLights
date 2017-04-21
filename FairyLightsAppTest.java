/**
 * 
 */
package test.com.lights.impl;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.lights.impl.FairyLightsApp;
import com.lights.model.LightCollection;

/**
 * @author prasanth
 *
 */
public class FairyLightsAppTest {
	
	FairyLightsApp fairyLightsApp;
	public static String CURRENT_CONTROLLER =  "sequence";
	LightCollection arrayListOfLights = null;
	public static String[] COL_LIST = new String[]{"red","green","white"};
	String[] listOfColours;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		fairyLightsApp = new FairyLightsApp();
		CURRENT_CONTROLLER =  "colour";
		arrayListOfLights = new LightCollection(20, COL_LIST);
		listOfColours = COL_LIST;
	}

	/**
	 * Test method for {@link com.lights.impl.FairyLightsApp#executeFairyLights()}.
	 */
	@Test
	public final void testExecuteFairyLights() {
		fairyLightsApp.main(null);
		//System.exit(0);
	}
	
	/**
	 * Test method for {@link com.lights.impl.FairyLightsApp#executeController()}.
	 */
	@Test
	public final void testExecuteController() {
		fairyLightsApp.executeController(CURRENT_CONTROLLER, arrayListOfLights, listOfColours);
	}

}
