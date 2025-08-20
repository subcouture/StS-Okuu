package OkuuMod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.actions.GluttonousCrowFollowUpAction;
import OkuuMod.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

public class GluttonousCrow extends AbstractDynamicCard {


    public static final String ID = OkuuMod_Old.makeID(GluttonousCrow.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int DRAW = 3;
    private static final int UPGRADE_PLUS_DRAW = 2;


    public GluttonousCrow() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = DRAW;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber, new GluttonousCrowFollowUpAction()));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DRAW);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }
}
