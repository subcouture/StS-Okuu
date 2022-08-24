package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;

import static utsuhoReiuji.OkuuMod.makeCardPath;

// public class BoilerExplosion extends AbstractDynamicCard
public class CoolantSpray extends AbstractDynamicCard {

    // Gain 14(18) Block. Radiate 1

    // TEXT DECLARATION

    public static final String ID = OkuuMod.makeID(CoolantSpray.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int BLOCK = 14;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    private static final int OVERLOAD = 1;

    private boolean OVERLOADED = false;



    // /STAT DECLARATION/


    public CoolantSpray() { // public BoilerExplosion() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    @Override
    public boolean hasEnoughEnergy() {
        if (!super.hasEnoughEnergy()){
            this.costForTurn -= OVERLOAD;
            OVERLOADED = true;
        }
        if (OVERLOADED && EnergyPanel.totalCount >= this.costForTurn + OVERLOAD){
            OVERLOADED = false;
        }
        return super.hasEnoughEnergy();

    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (OVERLOADED) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }

    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new GainBlockAction(p,p, block));
        if(OVERLOADED) {
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                AbstractDungeon.actionManager.addToBottom(
                        new GainBlockAction(monster, p, block));
            }
        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
