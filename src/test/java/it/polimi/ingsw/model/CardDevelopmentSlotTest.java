package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class CardDevelopmentSlotTest {

    @Test
    void testCloneConstructor() throws InvalidCardDevelopmentPlacementException, FullSlotException {
        CardDevelopmentSlot slot1 = new CardDevelopmentSlot(CardDevelopmentSlotID.ONE);
        slot1.placeCard(new CardDevelopment(0, 0, 0));

        CardDevelopmentSlot clone1 = new CardDevelopmentSlot(slot1);

        assertNotSame(slot1, clone1);
        assertNotSame(slot1.getCards(), clone1.getCards());

        System.out.println("debug : break point");
    }

    @Test
    void getTop() {

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
    void placeCard() throws InvalidCardDevelopmentPlacementException, FullSlotException {

        CardDevelopment green_level1 = new CardDevelopment(0, 0, 0);
        CardDevelopment green_level2 = new CardDevelopment(1, 0, 0);
        CardDevelopment green_level3 = new CardDevelopment(2, 0, 0);
        CardDevelopment yellow_level1 = new CardDevelopment(0, 1, 0);
        CardDevelopment yellow_level2 = new CardDevelopment(1, 1, 0);
        CardDevelopment yellow_level3 = new CardDevelopment(2, 1, 0);

        CardDevelopmentSlot slot1 = new CardDevelopmentSlot(CardDevelopmentSlotID.ONE);
        CardDevelopmentSlot slot2 = new CardDevelopmentSlot(CardDevelopmentSlotID.TWO);



        //Place 3 cards correctly in the slot
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                slot1.placeCard(green_level1);
                slot1.placeCard(green_level2);
                slot1.placeCard(green_level3);
            }
        });

        //Place a 4th card in the slot, should throw FullSlotException!
        assertThrows(FullSlotException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot1.placeCard(yellow_level1);
            }
        });

        //Place cards in random ways, always checking if the expected behaviour is met.
        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(green_level2);
            }
        });

        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(green_level3);
            }
        });

        //Place 1 card correctly
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(yellow_level1);
            }
        });

        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(green_level1);
            }
        });

        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(yellow_level3);
            }
        });

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(yellow_level2);
            }
        });

        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(green_level2);
            }
        });

        assertThrows(InvalidCardDevelopmentPlacementException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(yellow_level1);
            }
        });

        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(yellow_level3);
            }
        });

        assertThrows(FullSlotException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                slot2.placeCard(green_level3);
            }
        });

        assertEquals(3, slot1.getCards().size());
        assertEquals(3, slot2.getCards().size());

    }
}