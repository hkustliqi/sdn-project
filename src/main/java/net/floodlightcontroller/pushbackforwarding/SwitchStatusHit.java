/*
 Example of very simple switch status checker.
 Hit-based - every time it's checked, count a hit.
 If a switch is hit a certain number of times,
 rate limit it for a certain number of seconds.
 */

package net.floodlightcontroller.pushbackforwarding;

import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SwitchStatusHit implements ISwitchStatus {
    protected static Logger log = LoggerFactory.getLogger(SwitchStatusHit.class);
	
	private Map<Long, Integer> numHits;
	private Map<Long, Long> rateLimited;
	private long RATE_LIMIT_TIMEOUT;
    private int HIT_LIMIT;
	
	public SwitchStatusHit() {
        numHits = new HashMap<Long, Integer>();
		rateLimited = new HashMap<Long, Long>();
		RATE_LIMIT_TIMEOUT = 30 * 1000;
        HIT_LIMIT = 20;
	}
	
    // return true if rate limited
	public boolean getSwitchStatus(long swId) {
		if (rateLimited.containsKey(swId)) {
			long timeSet = rateLimited.get(swId);
            if ((System.currentTimeMillis() - timeSet) > RATE_LIMIT_TIMEOUT) {
                log.info("Removing switch " + swId + " from rate limited set");
                rateLimited.remove(swId);
                numHits.remove(swId);
                return false;
            }
            else
                return true;
        }
        
        int hits = 0;
        if (numHits.containsKey(swId))
            hits = numHits.get(swId);
        hits++;
        numHits.put(swId, hits);
        if (hits > HIT_LIMIT) {
            log.info("Rate limiting switch " + swId);
            rateLimited.put(swId, System.currentTimeMillis());
            return true;
        }
        return false;
	}
    
    
}