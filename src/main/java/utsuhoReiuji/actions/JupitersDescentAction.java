package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Iterator;

public class JupitersDescentAction extends AbstractGameAction {
    private float startingDuration;

    public JupitersDescentAction(AbstractCreature target){
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.duration = this.startingDuration;
    }


    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()){
            this.isDone = true;
        } else {
            Iterator var1;
            AbstractCard card;
            if(this.duration == this.startingDuration) {
                var1 = AbstractDungeon.player.powers.iterator();

                if (AbstractDungeon.player.drawPile.isEmpty()) {
                    this.isDone = true;
                    return;
                }

                CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            }
        }
    }
}
