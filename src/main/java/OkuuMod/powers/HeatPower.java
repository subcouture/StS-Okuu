package OkuuMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import utsuhoReiuji_old.OkuuMod_Old;
import utsuhoReiuji_old.util.TextureLoader;

import static utsuhoReiuji_old.OkuuMod_Old.makePowerPath;

// Heat is Okuu's resource. Conceptually similar to grey health in a fighting game, she can avoid damage or gain resources by building it up
// But one wrong move will cause her to lose it all, so she'll need to plan how she accumulates/spends it wisely.
// Designed to fit the high risk/reward nature of nuclear energy, with careful management over a razor's edge.
public class HeatPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = OkuuMod_Old.makeID("HeatPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public HeatPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.owner != this.owner && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DamageAction(owner, new DamageInfo(owner, amount*2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HeatPower(owner, source, amount);
    }
}
