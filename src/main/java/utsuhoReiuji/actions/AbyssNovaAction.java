package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.lwjgl.Sys;
import utsuhoReiuji.OkuuMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;

import java.util.Iterator;

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
        AbstractCard card;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        if(this.p.exhaustPile.isEmpty()){
            this.isDone = true;
        }
        else if(this.p.exhaustPile.size() < cardsToPick){
            AbstractDungeon.actionManager.addToTop(new FetchAction(this.p.limbo, this.p.limbo.size()));
            this.isDone = true;
        }
        else{
            AbstractDungeon.actionManager.addToTop(new FetchAction(this.p.exhaustPile, cardsToPick));
            this.isDone = true;
        }

        int count = AbstractDungeon.player.hand.size();
        int i;

        for(i = 0; i < count; ++i) {
            if (Settings.FAST_MODE) {
                this.addToTop(new ExhaustAction(1, true, true, false, Settings.ACTION_DUR_XFAST));
            } else {
                this.addToTop(new ExhaustAction(1, true, true));
            }
        }


    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(OkuuMod.makeID("AbyssNova"));
        TEXT = uiStrings.TEXT;
    }

}
