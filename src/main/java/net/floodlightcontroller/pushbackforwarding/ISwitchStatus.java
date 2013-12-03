/*
 Interface for checking switch status (should it be rate limited or not).
 */

package net.floodlightcontroller.pushbackforwarding;

public interface ISwitchStatus {
    
	// return true if rate limited, false if not
    // may want to switch that (or switch name)
	public boolean getSwitchStatus(long swId);
	
}