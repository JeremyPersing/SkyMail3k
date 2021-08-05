package org.csc133.a3;

/**
 * The AdvanceStrategy class has NonPlayerHelicopters try to advance to the
 * next SkyScraper object
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class AdvanceStrategy extends Strategy {
    AdvanceStrategy(GameWorld gw) {
        super(gw);
    }

    public void executeStrategy(NonPlayerHelicopter nph) {
        nph.setSpeed(7); // Lower speed for playability
        int lastScraperReached = nph.getLastSkyScraperReached();
        SkyScraper nextScraper;
        // If the scraper num is <= 1 from the last scraper num
        if (lastScraperReached <= getAllSkyScrapers().size() - 2) {
            nextScraper = getAllSkyScrapers().get(lastScraperReached + 1);
        }
        // last scraper
        else {
            nextScraper = getAllSkyScrapers().
                    get(getAllSkyScrapers().size() - 1);
        }

        // Find the angle that the heading needs to be to reach the object
        int angle = getAngleBetweenObjects(nph, nextScraper);
        nph.setHeading(angle);

        boolean collided = checkCollision(nph, nextScraper);

        if (collided) {
            nph.setLastSkyScraperReached(lastScraperReached + 1);
        }

        // Nph has reached the last Scraper
        if (lastScraperReached == getAllSkyScrapers().size() - 1) {
            getGameWorld().nphWon();
        }
    }

    public String getName() { return "Advance Strategy"; }
}
