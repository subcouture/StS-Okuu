package OkuuMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;
import OkuuMod.powers.NuclearVisorPower;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

// public class NuclearVisor extends AbstractDynamicCard
public class NuclearVisor extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = OkuuMod_Old.makeID(NuclearVisor.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 2;
    private static final int UPGRADED_COST = 2;

    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 4;

    // /STAT DECLARATION/


    public NuclearVisor() { // public NuclearVisor() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p, new NuclearVisorPower(p,p)));
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
