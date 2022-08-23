package utsuhoReiuji.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;

import java.util.function.Predicate;

import static utsuhoReiuji.OkuuMod.makeCardPath;

public class DeepRepository extends AbstractDynamicCard {

    // Choose 1(2) cards from your exhaust pile, put them at the bottom of your draw pile.


    public static final String ID = OkuuMod.makeID(DeepRepository.class.getSimpleName());
    public static final String IMG = makeCardPath("SelfTokamak.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    public DeepRepository() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }


    @Override
    //TODO Deep Repository exhausts a card if the exhaust pile is empty, work out what's going on there.
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new MoveCardsAction(AbstractDungeon.player.exhaustPile, AbstractDungeon.player.drawPile, deepRepositoryPredicateNegator(Predicate.isEqual(DeepRepository.ID)) , magicNumber));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeBaseCost(UPGRADED_COST);
            initializeDescription();
        }
    }

    private Predicate<AbstractCard> deepRepositoryPredicateNegator(Predicate<AbstractCard> predicate){
        return predicate.negate();
    }
}
