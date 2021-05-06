package utsuhoReiuji.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import utsuhoReiuji.cardMods.EtherealCardMod;

import java.util.Iterator;

public class GluttonousCrowFollowUpAction extends AbstractGameAction {
    public GluttonousCrowFollowUpAction() {
        this.duration = 0.001F;
    }

    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4F));
        this.tickDuration();
        if (this.isDone) {
            Iterator<AbstractCard> var1 = DrawCardAction.drawnCards.iterator();

            EtherealCardMod mod = new EtherealCardMod();

            while (var1.hasNext()) {
                AbstractCard card = var1.next();
                if (mod.isApplicable(card)) {
                    CardModifierManager.addModifier(card, mod);
                }
            }
        }
    }
}
