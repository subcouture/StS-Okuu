package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;

import static utsuhoReiuji.OkuuMod.makeCardPath;

// public class VengefulSpirits extends AbstractDynamicCard
public class VengefulSpirits extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = OkuuMod.makeID(VengefulSpirits.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = -2;
    private static final int UPGRADED_COST = -2;

    private static final int DAMAGE = 1;
    private static final int UPGRADE_PLUS_DMG = 0;

    private static final int HITS = 9;
    private static final int UPGRADE_PLUS_HITS = 3;

    private static final int CARDS_DRAWN = 2;
    private static final int UPGRADE_PLUS_CARDS_DRAWN = 1;

    // /STAT DECLARATION/


    public VengefulSpirits() { // public VengefulSpirits() - This one and the one right under the imports are the most important ones, don't forget them
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = HITS;
        secondMagicNumber = baseSecondMagicNumber = CARDS_DRAWN;
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(CARDS_DRAWN));
        for(int i = 0; i < HITS; ++i) {
            AbstractDungeon.actionManager.addToBottom(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_HITS);
            upgradeSecondMagicNumber(UPGRADE_PLUS_CARDS_DRAWN);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
