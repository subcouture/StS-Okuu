package OkuuMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import utsuhoReiuji_old.OkuuMod_Old;

import java.util.Iterator;

//TODO change add to bottom to add to top

public class JupitersDescentAction extends AbstractGameAction {

    private final float startingDuration;
    private static  UIStrings uiStrings;
    public static final String[] TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;

    private final int cardsToPlay;
    private final int cardsToPeek;


    public JupitersDescentAction(){
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.source = AbstractDungeon.player;
        this.duration = this.startingDuration;
        this.cardsToPeek = 2;
        this.cardsToPlay = 1;
        this.duration = Settings.ACTION_DUR_MED;
    }

    //Intended Behaviour: When triggered, opens a gridselect window (similar to scry) with 2 cards.
    //One can be selected, and that selection should be confirmed. On confirmation, that card is played and goes the the discard pile. The other card is exhausted.

    public void update() {
        AbstractCard card;
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        //Iterator var5 = this.p.drawPile.group.iterator();

        if (this.cardsToPeek != -1) {
            for(int i = 0; i < Math.min(this.cardsToPeek, AbstractDungeon.player.drawPile.size()); ++i) {
                tmp.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
            }
        } else {
            Iterator var5 = AbstractDungeon.player.drawPile.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                tmp.addToBottom(c);
            }
        }

        if (this.duration == Settings.ACTION_DUR_MED) {
            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                card.unhover();
                card.lighten(true);
                card.setAngle(0.0F);
                card.drawScale = 0.12F;
                card.targetDrawScale = 0.75F;
                card.current_x = CardGroup.DRAW_PILE_X;
                card.current_y = CardGroup.DRAW_PILE_Y;
                this.p.drawPile.removeCard(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                this.addToBot(new NewQueueCardAction(card, true, false, true));
                this.isDone = true;
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.cardsToPlay, TEXT[0], false);
                this.tickDuration();
            }
        } else {
            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard) var1.next();
                    tmp.group.remove(c);
                    AbstractDungeon.player.drawPile.group.remove(c);
                    AbstractDungeon.getCurrRoom().souls.remove(c);
                    this.addToBot(new NewQueueCardAction(c, true, false, true));
                }

                for(int i = 0; i < tmp.size(); i++){
                    this.p.drawPile.moveToExhaustPile(tmp.getTopCard());
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(OkuuMod_Old.makeID("JupitersDescent"));
        TEXT = uiStrings.TEXT;
    }

}
