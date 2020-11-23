package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Corruption;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import utsuhoReiuji.cards.DeepRepository;

import java.util.ArrayList;
import java.util.Iterator;

public class DeepRepositoryAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList deepRepositories = new ArrayList();

    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    int cardsToSelect;

    public DeepRepositoryAction(int cardsToSelect){
        this.cardsToSelect = cardsToSelect;
        this.p = AbstractDungeon.player;
        //this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        Iterator<AbstractCard> cardList;
        AbstractCard card;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
            }
            else if (this.p.exhaustPile.size() == 1) {
                if ( ( this.p.exhaustPile.group.get(0)).cardID.equals(DeepRepository.ID)) {
                    this.isDone = true;
                }
                else{
                    AbstractCard c = this.p.exhaustPile.getTopCard();
                    c.unfadeOut();
                    this.p.hand.addToHand(c);
                    if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                        c.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(c);

                    c.unhover();
                    c.fadingOut = false;
                    this.isDone = true;
                }

            }
            else if (this.p.exhaustPile.size() == 2 && this.cardsToSelect == 2){
                ArrayList<AbstractCard> cards = this.p.exhaustPile.group;
                for(AbstractCard c : cards){
                    if(c.cardID.equals(DeepRepository.ID)){
                        assert true;
                    }
                    else{
                        c.unfadeOut();
                        this.p.hand.addToHand(c);
                        if (AbstractDungeon.player.hasPower("Corruption") && c.type == AbstractCard.CardType.SKILL) {
                            c.setCostForTurn(-9);
                        }

                        this.p.exhaustPile.removeCard(c);

                        c.unhover();
                        c.fadingOut = false;
                        this.isDone = true;
                    }
                }
            }
            else{
                cardList = this.p.exhaustPile.group.iterator();

                while(cardList.hasNext()){
                    card = cardList.next();
                    card.stopGlowing();
                    card.unhover();
                    card.unfadeOut();
                }
                cardList = this.p.exhaustPile.group.iterator();

                while(cardList.hasNext()){
                    card = cardList.next();
                    if (card.cardID.equals(DeepRepository.ID)){
                        cardList.remove();
                        this.deepRepositories.add(card);
                    }
                }

                if (this.p.exhaustPile.isEmpty()){
                    this.p.exhaustPile.group.addAll(this.deepRepositories);
                    this.deepRepositories.clear();
                    this.isDone = true;
                }
                else {
                    AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, cardsToSelect, TEXT[0], false);
                    this.tickDuration();
                }
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (cardList = AbstractDungeon.gridSelectScreen.selectedCards.iterator(); cardList.hasNext(); card.unhover()) {
                    card = (AbstractCard) cardList.next();
                    this.p.hand.addToHand(card);
                    if (AbstractDungeon.player.hasPower(Corruption.ID) && card.type == AbstractCard.CardType.SKILL) {
                        card.setCostForTurn(-9);
                    }

                    this.p.exhaustPile.removeCard(card);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
                this.p.exhaustPile.group.addAll(this.deepRepositories);
                this.deepRepositories.clear();

                for (cardList = this.p.exhaustPile.group.iterator(); cardList.hasNext(); card.target_y = 0.0F) {
                    card = (AbstractCard) cardList.next();
                    card.unhover();
                    card.target_x = (float) CardGroup.DISCARD_PILE_X;
                }
            }


            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("DeepRepositoryAction");
        TEXT = uiStrings.TEXT;
    }

}
