package it.polimi.ingsw.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class FaithTrailTest {
    @Test
    public void main(){
           //Single Player Test
        GameTable gameTable = new GameTable(new ArrayList<String>(Arrays.asList("Single Player")));
        FaithTrail f= gameTable.getFaithTrailInstance();
        f.moveLorenzo();
        Assertions.assertEquals(1,f.getLorenzoPosition());
    }



}