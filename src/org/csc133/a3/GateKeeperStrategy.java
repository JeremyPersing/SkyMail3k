package org.csc133.a3;

import java.util.ArrayList;

/**
 * The GateKeeperStrategy class has NonPlayerHelicopters flying between the
 * last two SkyScrapers
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GateKeeperStrategy extends Strategy {
    private SkyScraper secondToLastSkyScraper;
    private SkyScraper lastSkyScraper;
    ArrayList<SkyScraper> allScrapers = getAllSkyScrapers();
    int nextSkyScraperNum;
    GateKeeperStrategy(GameWorld gw) {
        super(gw);
        nextSkyScraperNum = allScrapers.size() - 2;
        secondToLastSkyScraper = allScrapers.get(nextSkyScraperNum);
        lastSkyScraper = allScrapers.get(allScrapers.size() - 1);
    }

    public void executeStrategy(NonPlayerHelicopter nph) {
        if (nextSkyScraperNum == secondToLastSkyScraper.getSequenceNumber()) {
            int angle = getAngleBetweenObjects(nph, secondToLastSkyScraper);
            nph.setHeading(angle);
            if (checkCollision(nph, secondToLastSkyScraper)) {
                nextSkyScraperNum = lastSkyScraper.getSequenceNumber();
            }
        }
        else {
            int angle = getAngleBetweenObjects(nph, lastSkyScraper);
            nph.setHeading(angle);
            if (checkCollision(nph, lastSkyScraper)) {
                nextSkyScraperNum = secondToLastSkyScraper.getSequenceNumber();
            }
        }

    }

    public String getName() {return "GateKeeper Strategy"; }
}
