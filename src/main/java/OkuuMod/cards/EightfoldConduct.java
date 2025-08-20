package OkuuMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.cards.abstractCards.AbstractHellboundCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

public class EightfoldConduct extends AbstractHellboundCard {


    public static final String ID = OkuuMod_Old.makeID(EightfoldConduct.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");


    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 0;
    private static final int UPGRADED_COST = 0;

    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 2;
    //TODO: Work out how to increase multidamage by the hellbound damage.
    private static final int HELLBOUND_PLUS_DMG = 2;



    public EightfoldConduct() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(isHellbound){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        }
        else{
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
