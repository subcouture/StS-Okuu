package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;

import static utsuhoReiuji.OkuuMod.makeCardPath;

// public class ApollosLight extends AbstractDynamicCard
public class ApollosLight extends AbstractDynamicCard {

    // Heal 2 hp. Exhaust. Chain Reaction

    // TEXT DECLARATION

    public static final String ID = OkuuMod.makeID(ApollosLight.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //
    private static final CardTarget TARGET = CardTarget.SELF;  //   
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int HEAL = 2;
    private static final int UPGRADE_PLUS_HEAL = 1;

    // /STAT DECLARATION/


    public ApollosLight() { // public ApollosLight() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = HEAL;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new HealAction(p, p, magicNumber));
        if(!this.purgeOnUse) {
            for(int i = 1; i < AbstractDungeon.actionManager.cardsPlayedThisTurn.size(); i++) {
                GameActionManager.queueExtraCard(this, m);
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_HEAL);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
