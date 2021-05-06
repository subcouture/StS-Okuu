package utsuhoReiuji.deprecated.util;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.Iterator;

public class HellboundChecker {

    private AbstractCard card;
    private AbstractPlayer player;

    public boolean HellboundCheck(AbstractPlayer p, AbstractCard card){
        this.card = card;
        this.player = p;

        Iterator<AbstractCard> handCards = player.hand.group.iterator();

        boolean check = true;

        while(handCards.hasNext()){
            AbstractCard currentCard = handCards.next();
            if((currentCard.type == AbstractCard.CardType.SKILL || currentCard.type == AbstractCard.CardType.ATTACK || currentCard.type == AbstractCard.CardType.POWER) && currentCard != card){
                check = false;
            }
        }
        return check;
    }
}
