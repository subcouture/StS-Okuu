package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;
import utsuhoReiuji.powers.SelfTokamakPower;

import static utsuhoReiuji.OkuuMod.makeCardPath;


public class SelfTokamak extends AbstractDynamicCard {



    public static final String ID = OkuuMod.makeID(SelfTokamak.class.getSimpleName());
    public static final String IMG = makeCardPath("SelfTokamak.png");



    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 1;

    private static final int BLOCK = 12;
    private static final int UPGRADE_PLUS_BLOCK = 6;




    public SelfTokamak() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }



    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SelfTokamakPower(p, p, block)));
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
