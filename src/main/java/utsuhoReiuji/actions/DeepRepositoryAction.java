package utsuhoReiuji.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import utsuhoReiuji.cards.DeepRepository;

import java.util.ArrayList;
import java.util.Iterator;

public class DeepRepositoryAction extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> exhumes = new ArrayList();

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
            else if (this.p.exhaustPile.size() == cardsToSelect) {
                if (cardsToSelect == 1 && ( this.p.exhaustPile.group.get(0)).cardID.equals(DeepRepository.ID)) {
                    this.isDone = true;
                }
                else{

                }
            }
        }
    }
}
