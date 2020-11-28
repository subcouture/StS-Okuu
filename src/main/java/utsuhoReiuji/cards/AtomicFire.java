package utsuhoReiuji.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.actions.AtomicFireAction;
import utsuhoReiuji.cards.abstractCards.AbstractDynamicCard;
import utsuhoReiuji.characters.UtsuhoReiuji;

import static utsuhoReiuji.OkuuMod.makeCardPath;

public class AtomicFire extends AbstractDynamicCard {


    public static final String ID = OkuuMod.makeID(AtomicFire.class.getSimpleName());
    public static final String IMG = makeCardPath("AtomicFire.png");


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
