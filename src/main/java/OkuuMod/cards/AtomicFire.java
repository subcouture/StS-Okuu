package OkuuMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji_old.OkuuMod_Old;
import OkuuMod.actions.AtomicFireAction;
import OkuuMod.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji_old.characters.UtsuhoReiuji;

import static utsuhoReiuji_old.OkuuMod_Old.makeCardPath;

public class AtomicFire extends AbstractDynamicCard {

    // Deal X damage for each card in your exhaust pile. [Exhaust]


    public static final String ID = OkuuMod_Old.makeID(AtomicFire.class.getSimpleName());
    public static final String IMG = makeCardPath("BoilerExplosion.png");


    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = UtsuhoReiuji.Enums.REIUJI_GREEN;

    private static final int COST = -1;


    public AtomicFire() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.damage = AbstractDungeon.player.exhaustPile.size();
        AbstractDungeon.actionManager.addToBottom(
                new AtomicFireAction(p, m, this.damage, this.damageTypeForTurn, this.freeToPlayOnce, this.energyOnUse));
    }


    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false;
            initializeDescription();
        }
    }
}
