package OkuuMod.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import utsuhoReiuji_old.OkuuMod_Old;
import utsuhoReiuji_old.util.TextureLoader;

import static utsuhoReiuji_old.OkuuMod_Old.makePowerPath;

public class AstralHeatPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = OkuuMod_Old.makeID("AstralHeatPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    //TODO find out if it's harmful to add variables to the definition of a power, and if powers should be allowed to create other powers.
    private final int selfDamage;

    public AstralHeatPower(final AbstractCreature owner, final AbstractCreature source, final int selfDamage){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;

        this.selfDamage = selfDamage;

        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        AbstractDungeon.actionManager.addToBottom(new SkipEnemiesTurnAction());
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new AstralHeatSelfDamagePower(owner, owner, selfDamage)));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
            description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AstralHeatPower(owner, source, selfDamage);
    }
}
