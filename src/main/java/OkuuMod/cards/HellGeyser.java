package OkuuMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

// public class HellGeyser extends AbstractDynamicCard
public class HellGeyser extends AbstractDynamicCard {

    public static final String ID = OkuuMod_Old.makeID(HellGeyser.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 3;
    private static final int UPGRADED_COST = 3;

    private static final int DAMAGE = 30;
    private static final int UPGRADE_PLUS_DMG = 10;

    //TODO ensure that Hell Geyser interacts correctly with vulnerable/weakness/strength and the like.
    //TODO observe the effect of doubling/chain reaction on Hell Geyser
    public HellGeyser() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m.currentHealth + m.currentBlock > damage){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        }
        else{
            for(int i = 0; i < this.multiDamage.length; i++) {
                multiDamage[i] = multiDamage[i]- (m.currentBlock + m.currentHealth);
            }
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(
                            new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
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
