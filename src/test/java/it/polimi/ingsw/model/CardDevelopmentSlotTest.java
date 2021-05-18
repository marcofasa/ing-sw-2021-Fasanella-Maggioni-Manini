package it.polimi.ingsw.model;

import org.junit.Test;

import static org.junit.Assert.*;

class CardDevelopmentSlotTest {

    @Test
    public void testCloneConstructor() {
        CardDevelopmentSlot slot1 = new CardDevelopmentSlot(CardDevelopmentSlotID.ONE);
        try {
            slot1.placeCard(new CardDevelopment(0, 0, 0));
        } catch (Exception e) {
            System.out.println("Unexpected exception was thrown.");
        }

        CardDevelopmentSlot clone1 = new CardDevelopmentSlot(slot1);

        assertNotSame(slot1, clone1);
        assertNotSame(slot1.getCards(), clone1.getCards());

        System.out.println("debug : break point");
    }

    @Test
    public void getTop() {

        CardDevelopment card1 = new CardDevelopment(0, 0, 0);
        CardDevelopment card2 = new CardDevelopment(0, 0, 1);
        CardDevelopment clone1 = new CardDevelopment(card1);

        assertNotSame(card1, clone1);

        CardDevelopmentSlot slot1 = new CardDevelopmentSlot(CardDevelopmentSlotID.ONE);
        CardDevelopmentSlot slot2 = new CardDevelopmentSlot(CardDevelopmentSlotID.TWO);
        CardDevelopmentSlot slot3 = new CardDevelopmentSlot(CardDevelopmentSlotID.THREE);

        try {
            slot1.placeCard(card1);
            slot2.placeCard(card2);
        } catch (FullSlotException | InvalidCardDevelopmentPlacementException e) {
            System.out.println(e);
        }

        assertSame(card1, slot1.getTop());
        assertSame(card2, slot2.getTop());

        assertEquals(1, slot1.getCards().size());
        assertEquals(1, slot2.getCards().size());
        assertEquals(0, slot3.getCards().size());
    }

    @Test
    public void placeCard() throws InvalidCardDevelopmentPlacementException, FullSlotException {

        CardDevelopment green_level1 = new CardDevelopment(0, 0, 0);
        CardDevelopment green_level2 = new CardDevelopment(1, 0, 0);
        CardDevelopment green_level3 = new CardDevelopment(2, 0, 0);
        CardDevelopment yellow_level1 = new CardDevelopment(0, 1, 0);
        CardDevelopment yellow_level2 = new CardDevelopment(1, 1, 0);
        CardDevelopment yellow_level3 = new CardDevelopment(2, 1, 0);

        CardDevelopmentSlot slot1 = new CardDevelopmentSlot(CardDevelopmentSlotID.ONE);
        CardDevelopmentSlot slot2 = new CardDevelopmentSlot(CardDevelopmentSlotID.TWO);


        try {
            slot1.placeCard(green_level1);
            slot1.placeCard(green_level2);
            slot1.placeCard(green_level3);
        } catch (Exception e) {
            System.out.println("Unexpected exception thrown! : ");
            e.printStackTrace();
        }

        //Place a 4th card in the slot, should throw FullSlotException!
        try {
            slot1.placeCard(yellow_level1);
            fail("Exception was not thrown.");
        } catch (FullSlotException ignored) {

        }

        //Place cards in random ways, always checking if the expected behaviour is met.
        try {
            slot2.placeCard(green_level2);
            fail("Exception that should have been thrown, was not.");
        } catch (InvalidCardDevelopmentPlacementException ignored) {

        }

        //Place 1 card correctly
        try {
            slot2.placeCard(yellow_level1);
        } catch (Exception e) {
            System.out.println("Unexpected exception thrown! : ");
            e.printStackTrace();
        }

        //Place cards in incorrect ways : exceptions should be thrown here
        try {
            slot2.placeCard(green_level1);
            fail("Exception that should have been thrown, was not.");
        } catch (InvalidCardDevelopmentPlacementException ignored) {

        }

        try {
            slot2.placeCard(yellow_level3);
            fail("Exception that should have been thrown, was not.");
        } catch (InvalidCardDevelopmentPlacementException ignored) {

        }

        //Place second card correctly
        try {
            slot2.placeCard(yellow_level2);
        } catch (Exception e) {
            System.out.println("Unexpected exception thrown! : ");
            e.printStackTrace();
        }

        //Place more cards incorrectly
        try {
            slot2.placeCard(green_level2);
            fail("Exception that should have been thrown, was not.");
        } catch (InvalidCardDevelopmentPlacementException ignored) {

        }

        try {
            slot2.placeCard(yellow_level1);
            fail("Exception that should have been thrown, was not.");
        } catch (InvalidCardDevelopmentPlacementException ignored) {

        }

        //Place third card correctly
        try {
            slot2.placeCard(yellow_level3);
        } catch (Exception e) {
            System.out.println("Unexpected exception thrown! : ");
            e.printStackTrace();
        }

        //Place a card on a full slot
        try {
            slot2.placeCard(green_level3);
            fail("Exception that should have been thrown, was not.");
        } catch (FullSlotException ignored) {

        }

        assertEquals(3, slot1.getCards().size());
        assertEquals(3, slot2.getCards().size());

    }
}