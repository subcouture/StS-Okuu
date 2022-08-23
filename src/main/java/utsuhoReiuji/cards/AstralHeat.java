package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;
import utsuhoReiuji.powers.AstralHeatPower;

import static utsuhoReiuji.OkuuMod.makeCardPath;

// public class AstralHeat extends AbstractDynamicCard
public class AstralHeat extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = OkuuMod.makeID(AstralHeat.class.getSimpleName());
    public static final String IMG = makeCardPath("AstralHeat.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private static final int SELF_DAMAGE = 108;

    // /STAT DECLARATION/


    public AstralHeat() { super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = SELF_DAMAGE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new AstralHeatPower(p,p, magicNumber)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
