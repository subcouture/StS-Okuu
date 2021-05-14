package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
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

import java.util.Iterator;

public class JupitersDescentAction extends AbstractGameAction {

    private float startingDuration;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack
            .getUIString("ExhaustAction");
    public static final String[] UISTRING = uiStrings.TEXT;
    private final AbstractPlayer p = AbstractDungeon.player;


    public JupitersDescentAction(){
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.source = AbstractDungeon.player;
        this.duration = this.startingDuration;
        this.amount = 2;
    }

    //Intended Behaviour: When triggered, opens a gridselect window (similar to scry) with 2 cards.
    //One can be selected, and that selection should be confirmed. On confirmation, that card is played and goes the the discard pile. The other card is exhausted.

    public void update() {
        AbstractCard card;
        /*if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            Iterator var5 = this.p.drawPile.group.iterator();

            while(var5.hasNext()) {
                AbstractCard c = (AbstractCard)var5.next();
                if (c.type == AbstractCard.CardType.ATTACK) {
                    tmp.addToRandomSpot(c);
                }
            }

            if (tmp.size() == 0) {
                this.isDone = true;
            } else if (tmp.size() == 1) {
                card = tmp.getTopCard();
                if (this.p.hand.size() == 10) {
                    this.p.drawPile.moveToDiscardPile(card);
                    this.p.createHandIsFullDialog();
                } else {
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    this.p.drawPile.removeCard(card);
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }

                this.isDone = true;
            } else {
                AbstractDungeon.gridSelectScreen.open(tmp, this.amount, UISTRING[0], false);
                this.tickDuration();
            }
        } else {*/
            //if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    card = (AbstractCard)var1.next();
                    card.unhover();
                    if (this.p.hand.size() == 10) {
                        this.p.drawPile.moveToDiscardPile(card);
                        this.p.createHandIsFullDialog();
                    } else {
                        this.p.drawPile.removeCard(card);
                        this.p.hand.addToTop(card);
                    }

                    this.p.hand.refreshHandLayout();
                    this.p.hand.applyPowers();
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                this.p.hand.refreshHandLayout();
            //}

            this.tickDuration();
        //}
    }



    //Copied from ScryAction, needs changing to match intended behaviour
    /*
    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
        } else {
            Iterator var1;
            AbstractCard c;
            if (this.duration == this.startingDuration) {

                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                if (this.amount != -1) {
                    for(int i = 0; i < Math.min(this.amount, AbstractDungeon.player.drawPile.size()); ++i) {
                        tmpGroup.addToTop((AbstractCard)AbstractDungeon.player.drawPile.group.get(AbstractDungeon.player.drawPile.size() - i - 1));
                    }
                } else {
                    Iterator<AbstractCard> var5 = AbstractDungeon.player.drawPile.group.iterator();

                    while(var5.hasNext()) {
                        c = (AbstractCard)var5.next();
                        tmpGroup.addToBottom(c);
                    }
                }

                AbstractDungeon.cardRewardScreen.customCombatOpen(tmpGroup.group, UISTRING[0], false);
                this.tickDuration();
            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()){
                    c = (AbstractCard)var1.next();
                    this.addToBot(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), true));
                }

            }

                /*
                while(var1.hasNext()) {
                    c = (AbstractCard)var1.next();
                    AbstractDungeon.player.drawPile.moveToDiscardPile(c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            var1 = AbstractDungeon.player.discardPile.group.iterator();

            while(var1.hasNext()) {
                c = (AbstractCard)var1.next();
                c.triggerOnScry();
            }



            this.tickDuration();
        }
    }*/
}
