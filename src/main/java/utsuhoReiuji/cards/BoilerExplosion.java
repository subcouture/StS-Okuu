package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.characters.UtsuhoReiuji;

import static utsuhoReiuji.OkuuMod.makeCardPath;

// public class BoilerExplosion extends AbstractDynamicCard
public class BoilerExplosion extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = OkuuMod.makeID(BoilerExplosion.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int DAMAGE = 20;
    private static final int UPGRADE_PLUS_DMG = 20;

    private static final int OVERLOAD = 2;

    private boolean OVERLOADED = false;



    // /STAT DECLARATION/


    public BoilerExplosion() { // public BoilerExplosion() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
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
                new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        if(OVERLOADED) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(p, new DamageInfo(p, this.baseDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));

        }
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
