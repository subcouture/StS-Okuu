package OkuuMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import utsuhoReiuji_old.OkuuMod_Old;
import utsuhoReiuji_old.util.TextureLoader;

import static utsuhoReiuji_old.OkuuMod_Old.makePowerPath;

public class AstralHeatSelfDamagePower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    private int selfDamage;

    public static final String POWER_ID = OkuuMod_Old.makeID("AstralHeatSelfDamagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public AstralHeatSelfDamagePower(final AbstractCreature owner, final AbstractCreature source, final int selfDamage){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;

        this.selfDamage = selfDamage;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        this.flash();
        this.addToBot(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(owner, new DamageInfo(owner, selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0] + selfDamage + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AstralHeatSelfDamagePower(owner, source, selfDamage);
    }
}
