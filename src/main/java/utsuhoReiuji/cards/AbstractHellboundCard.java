package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public abstract class AbstractHellboundCard extends AbstractDynamicCard{


    public boolean isHellbound = false;

    public AbstractHellboundCard(final String id,
                               final String img,
                               final int cost,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, img, cost, type, color, rarity, target);

    }

    //TODO: Rework the logic here so I don't have to use empty braces.
    @Override
    public void applyPowers() {
        super.applyPowers();
        isHellbound = true;
        for(AbstractCard card : AbstractDungeon.player.hand.group){
            if(card.type == AbstractCard.CardType.STATUS && card.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit")){
            assert true;} else if (card.type == AbstractCard.CardType.CURSE && card.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle")) {
            assert true;} else if(card.hasEnoughEnergy() && !card.cardID.equals(this.cardID)) {
                isHellbound = false;
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = isHellbound ? AbstractCard.GOLD_BORDER_GLOW_COLOR : AbstractCard.BLUE_BORDER_GLOW_COLOR;
    }
}
