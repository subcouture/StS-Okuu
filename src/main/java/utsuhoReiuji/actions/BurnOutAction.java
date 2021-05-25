package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DiscardToHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BurnOutAction extends AbstractGameAction {

    //TODO change add to bottom to add to top

    private static final UIStrings uiStrings = CardCrawlGame.languagePack
            .getUIString("ExhaustAction");
    public static final String[] UISTRING = uiStrings.TEXT;
    AbstractPlayer player;
    private AbstractCard cardInstance;
    private DamageInfo.DamageType damageTypeForTurn;

    public BurnOutAction(AbstractCard cardInstance){
        this.cardInstance = cardInstance;
        this.player = AbstractDungeon.player;
        this.damageTypeForTurn = DamageInfo.DamageType.NORMAL;
        this.actionType = ActionType.EXHAUST;
        this.duration = Settings.ACTION_DUR_FAST;
    }


    //TODO work out why card loops to hand infinitely
    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {

            if (this.player.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.player.hand.size() == 1) {
                AbstractCard handTopCard = player.hand.getTopCard();
                AbstractDungeon.actionManager.addToBottom(
                            new DiscardToHandAction(cardInstance));
                this.player.hand.moveToExhaustPile(handTopCard);
                this.isDone = true;
                return;
            } else {
                AbstractDungeon.handCardSelectScreen.open(UISTRING[0], 1, false, false);
            }

            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            cardInstance.returnToHand = false;
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                cardInstance.returnToHand = true;
                this.player.hand.moveToExhaustPile(c);
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            AbstractDungeon.player.hand.refreshHandLayout();
            this.isDone = true;
            return;
        }
        tickDuration();
    }
}
