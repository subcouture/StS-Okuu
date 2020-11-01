package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.characters.UtsuhoReiuji;
import utsuhoReiuji.powers.EmergencyShutdownPower;

import static utsuhoReiuji.OkuuMod.makeCardPath;

// public class EmergencyShutdown extends AbstractDynamicCard
public class EmergencyShutdown extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = OkuuMod.makeID(EmergencyShutdown.class.getSimpleName());
    public static final String IMG = makeCardPath("EmergencyShutdown.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.SELF;  //   since they don't change much.
    private static final CardType TYPE = CardType.SKILL;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int AMOUNT = 1;
    private static final int UPGRADE_PLUS_AMOUNT = 0;

    // /STAT DECLARATION/


    public EmergencyShutdown() { // public EmergencyShutdown() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = AMOUNT;
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
            upgradeBlock(UPGRADE_PLUS_AMOUNT);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
