package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractHellboundCard;
import utsuhoReiuji.characters.UtsuhoReiuji;

import static utsuhoReiuji.OkuuMod.makeCardPath;

public class EightfoldResolve extends AbstractHellboundCard {


    public static final String ID = OkuuMod.makeID(EightfoldResolve.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 2;

    private static final int HELLBOUND_BLOCK = 6;


    public EightfoldResolve() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(isHellbound){
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block + HELLBOUND_BLOCK));
        }
        else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        }
    }


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
