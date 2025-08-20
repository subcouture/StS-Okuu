package OkuuMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import utsuhoReiuji_old.OkuuMod_Old;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;

import java.util.Iterator;
import java.util.function.Predicate;

public class AbyssNovaAction extends AbstractGameAction {

    private final float startingDuration;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;

    private final int cardsToPick;


    public AbyssNovaAction(int cardsToPick){
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.source = AbstractDungeon.player;
        this.duration = this.startingDuration;
        this.cardsToPick = cardsToPick;
        this.duration = Settings.ACTION_DUR_MED;
    }

    //how I would expect it to resolve would depend entirely on the card
    //But how I would probably handle it is just
    //In action added by card:
    // - remove select cards from exhaust pile
    // - add action to top that puts selected cards in hand
    // - add action to top that exhausts hand and deck (since these are added to top, this will happen before cards are added to hand)

    //Intended Behaviour: A grid select screen is opened, showing all cards in the exhaust pile. The player can select up to 5(8) of them.
    //When the player finishes selecting, the screen is closed, the deck is exhausted, and the 5 cards are added to the players hand, and are free to play this turn.

    //TODO Auto add the cards if the pile is less than the required amount.

    //TODO Switch from fetchaction to custom that exhausts the cards THEN puts them in hand.

    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            AbstractCard card;
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            tmp = selectCardsToLimbo(tmp);
            Iterator tmpCards = tmp.group.iterator();

            while (tmpCards.hasNext()) {
                AbstractCard c = (AbstractCard) tmpCards.next();
                AbstractDungeon.actionManager.addToTop(new FetchAction(this.p.limbo, Predicate.isEqual(c)));
            }

            int count = AbstractDungeon.player.hand.size();
            int i;

            for (i = 0; i < count; ++i) {
                if (Settings.FAST_MODE) {
                    this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
                } else {
                    this.addToTop(new ExhaustAction(1, true, true));
                }
            }
        }
        this.isDone = true;
    }

    CardGroup selectCardsToLimbo(CardGroup tmp) {

        if (this.p.exhaustPile.size() == 0) {
            this.isDone = true;
        } else if (this.p.exhaustPile.size() <= cardsToPick) {
            for(int i = 0; i < this.p.exhaustPile.size(); i++){
                AbstractCard c = AbstractDungeon.player.exhaustPile.getTopCard();
                tmp.group.add(c);
                AbstractDungeon.player.discardPile.group.remove(c);
                AbstractDungeon.getCurrRoom().souls.remove(c);
                AbstractDungeon.player.limbo.group.add(c);
            }
        } else {
            AbstractDungeon.gridSelectScreen.open(tmp, this.cardsToPick, TEXT[0], false);
            this.tickDuration();
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

            while (var1.hasNext()) {
                AbstractCard c = (AbstractCard) var1.next();
                tmp.group.add(c);
                AbstractDungeon.player.discardPile.group.remove(c);
                AbstractDungeon.getCurrRoom().souls.remove(c);
                AbstractDungeon.player.limbo.group.add(c);
            }

            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }

        return tmp;
    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(OkuuMod_Old.makeID("AbyssNova"));
        TEXT = uiStrings.TEXT;
    }

}
