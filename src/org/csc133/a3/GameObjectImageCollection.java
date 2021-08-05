package org.csc133.a3;

import com.codename1.ui.Image;

import java.io.IOException;

/**
 * The GameObjectImageCollection class creates and holds the images, excluding
 * the main helicopter.
 *
 * @author Jeremy Persing
 * @section 4
 * @SID 9682
 * @version 3.0
 */

public class GameObjectImageCollection {
    private Image[] skyScraperImages = new Image[10];
    private Image skyScraperMask;
    private Image [] nphImages = new Image[72];
    private Image [] heliBlades = new Image[6];
    private Image [] heliBladeMasks = new Image[6];
    private Image [] flapOneImages = new Image [72];
    private Image [] flapTwoImages = new Image [72];
    private Image [] flapThreeImages = new Image [72];
    private Image blimpImage;
    private Image blimpMask;

    GameObjectImageCollection() {
        try {
            skyScraperMask = Image.createImage("/SkyScraperMask.png");
            for (int i = 0; i < skyScraperImages.length; i++) {
                skyScraperImages[i] = Image.createImage("/SkyScraper" + i +
                        ".png");
            }

            Image heliBody = Image.createImage("/NonPlayerHelicopterBody.png");
            int rotationAmt = 360 / nphImages.length;
            for (int i = 0; i < nphImages.length; i++) {
                nphImages[i] = heliBody.rotate(i * -rotationAmt);
            }

            Image heliBlade = Image.createImage("/HelicopterBlade.png");
            Image heliBladeMask = Image.createImage("/HelicopterBladeMask.png");
            rotationAmt = 360 / heliBlades.length;
            for (int i = 0; i < heliBlades.length; i++) {
                heliBlades[i] = heliBlade.rotate(i * rotationAmt);
                heliBladeMasks[i] = heliBladeMask.rotate(i * rotationAmt);
            }

            rotationAmt = 360 / flapOneImages.length;
            Image flapOne = Image.createImage("/Bird1.png");
            Image flapTwo = Image.createImage("/Bird2.png");
            Image flapThree = Image.createImage("/Bird3.png");

            for (int i = 0; i < flapOneImages.length; i++) {
                flapOneImages[i] = flapOne.rotate(i * -rotationAmt);
                flapTwoImages[i] = flapTwo.rotate(i * -rotationAmt);
                flapThreeImages[i] = flapThree.rotate(i * -rotationAmt);
            }

            blimpImage = Image.createImage("/Blimp.png");
            blimpMask = Image.createImage("/BlimpMask.png");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image [] getSkyScraperImages() { return skyScraperImages; }

    public Image getSkyScraperMask() { return skyScraperMask; }

    public Image [] getNphImages() { return nphImages; }

    public Image [] getHeliBlades() { return heliBlades; }
    public Image [] getHeliBladeMasks() { return heliBladeMasks; }

    public Image [] getFlapOneImages() { return flapOneImages; }
    public Image [] getFlapTwoImages() { return flapTwoImages; }
    public Image [] getFlapThreeImages() { return flapThreeImages; }

    public Image getBlimpImage() { return blimpImage; }
    public Image getBlimpMask() { return blimpMask; }
}
