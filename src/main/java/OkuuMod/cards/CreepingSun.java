package OkuuMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;
import OkuuMod.powers.CreepingSunPower;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

// public class CreepingSun extends AbstractDynamicCard
public class CreepingSun extends AbstractDynamicCard {

    // Until you play an attack, each card you play increases the damage of your next attack by 3.

    // TEXT DECLARATION

    public static final String ID = OkuuMod_Old.makeID(CreepingSun.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //
    private static final CardTarget TARGET = CardTarget.SELF;  //   
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int VIGOR = 3;
    private static final int UPGRADE_PLUS_VIGOR = 2;


    // /STAT DECLARATION/

    public CreepingSun() { // public CreepingSun() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = baseMagicNumber = VIGOR;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new CreepingSunPower(p, p, magicNumber), magicNumber));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            upgradeMagicNumber(UPGRADE_PLUS_VIGOR);
            initializeDescription();
        }
    }
}
