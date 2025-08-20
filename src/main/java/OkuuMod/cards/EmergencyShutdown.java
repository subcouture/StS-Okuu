package OkuuMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;
import OkuuMod.powers.EmergencyShutdownPower;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

// public class EmergencyShutdown extends AbstractDynamicCard
public class EmergencyShutdown extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = OkuuMod_Old.makeID(EmergencyShutdown.class.getSimpleName());
    public static final String IMG = makeCardPath("EmergencyShutdown.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int INTANGIBLE = 1;
    private static final int UPGRADE_PLUS_INTANGIBLE = 0;

    // /STAT DECLARATION/


    public EmergencyShutdown() { // public EmergencyShutdown() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = INTANGIBLE;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p, new EmergencyShutdownPower(p,p)));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_INTANGIBLE);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
