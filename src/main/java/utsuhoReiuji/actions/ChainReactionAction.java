package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

public class ChainReactionAction extends AbstractGameAction{
    private DamageInfo info;
    private AbstractCard card;

    public ChainReactionAction(AbstractCreature target, AbstractCard card, DamageInfo info, AttackEffect effect){
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
        this.card = card;
    }

    public ChainReactionAction(AbstractCreature target, AbstractCard card, DamageInfo info) {
        this(target, card, info, AttackEffect.NONE);
    }

    //TODO make this work with more than just attacks
    @Override
    public void update() {
        this.isDone = true;
        if (this.target != null && this.target.currentHealth > 0) {
            int count = AbstractDungeon.actionManager.cardsPlayedThisTurn.size();

            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));


            for(int i = 0; i < count - 1; ++i) {

                if (!card.purgeOnUse) {
                    AbstractMonster m = null;
                    if (target != null) {
                        m = (AbstractMonster)target;
                    }

                    AbstractCard tmp = card.makeSameInstanceOf();
                    AbstractDungeon.player.limbo.addToBottom(tmp);
                    tmp.current_x = card.current_x;
                    tmp.current_y = card.current_y;
                    tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                    tmp.target_y = (float)Settings.HEIGHT / 2.0F;
                    if (m != null) {
                        tmp.calculateCardDamage(m);
                    }

                    tmp.purgeOnUse = true;
                    AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
                }
            }

        }

    }
}
