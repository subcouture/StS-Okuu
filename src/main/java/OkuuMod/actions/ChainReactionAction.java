package OkuuMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ChainReactionAction extends AbstractGameAction{
    //private UseCardAction action;
    private AbstractCard card;
    private int amount;

    public ChainReactionAction(AbstractCard card, int amount){
        //this.action = action;
        this.amount = amount;
        this.card = card;
    }

    @Override
    public void update(){
        if(!card.purgeOnUse) {
            AbstractMonster m = null;
            if (target != null) {
                m = (AbstractMonster)target;
            }
            for(int i = 1; i < amount; i++) {
                GameActionManager.queueExtraCard(card, m);
            }
        }
    }


    /*
    @Override
    public void update() {
        if(!card.purgeOnUse){
            AbstractMonster m = null;
            if (target != null) {
                m = (AbstractMonster)target;
            }

            for(int i = 0; i <= amount;i++) {
                AbstractCard tmp = card.makeSameInstanceOf();
                AbstractDungeon.player.limbo.addToBottom(tmp);
                tmp.current_x = card.current_x;
                tmp.current_y = card.current_y;
                tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                tmp.target_y = (float) Settings.HEIGHT / 2.0F;
                if (m != null) {
                    tmp.calculateCardDamage(m);
                }

                tmp.purgeOnUse = true;
                AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, card.energyOnUse, true, true), true);
            }
        }
    }

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

    }*/
}
