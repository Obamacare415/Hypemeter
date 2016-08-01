package com.mlm.hypemeter.cardstream;

import java.util.HashSet;
/**
 * Created by Martin on 2/29/2016.
 */
public class CardStreamState {

    protected Card[] visibleCards;
    protected Card[] hiddenCards;
    protected HashSet<String> dismissibleCards;
    protected String shownTag;

    protected CardStreamState(Card[] visible, Card[] hidden, HashSet<String> dismissible, String shownTag) {
        visibleCards = visible;
        hiddenCards = hidden;
        dismissibleCards = dismissible;
        this.shownTag = shownTag;
    }
}
