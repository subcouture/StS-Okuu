package utsuhoReiuji.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utsuhoReiuji.OkuuMod;
import utsuhoReiuji.cards.*;
import utsuhoReiuji.cards.defaultCards.DefaultCommonAttack;
import utsuhoReiuji.relics.DefaultClickableRelic;
import utsuhoReiuji.relics.PlaceholderRelic;
import utsuhoReiuji.relics.PlaceholderRelic2;

import java.util.ArrayList;
import java.util.Iterator;

import static utsuhoReiuji.OkuuMod.*;
import static utsuhoReiuji.characters.UtsuhoReiuji.Enums.REIUJI_GREEN;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in DefaultMod-character-Strings.json in the resources

public class UtsuhoReiuji extends CustomPlayer {
    public static final Logger logger = LogManager.getLogger(OkuuMod.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass UTSUHO_REIUJI;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor REIUJI_GREEN;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    // =============== CHARACTER ENUMERATORS  =================


    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 75;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 3;
    private static final int START_ORBS = 0;
    public static final int ORB_SLOTS = 1;

    // =============== /BASE STATS/ =================

    // =============== TEXTURE ATLASES =================

    private static final String OKUU_SKELETON_IDLE_ATLAS = "utsuhoReiujiResources/images/char/okuuSprites/Dragonbones/idle/skeleton.atlas";
    private static final String OKUU_SKELETON_IDLE_JSON = "utsuhoReiujiResources/images/char/okuuSprites/Dragonbones/idle/skeleton.json";


    // =============== /TEXTURE ATLASES/ =================

    // =============== STRINGS =================

    private static final String ID = makeID("DefaultCharacter");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer1.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer2.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer3.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer4.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer5.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer6.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer1d.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer2d.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer3d.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer4d.png",
            "utsuhoReiujiResources/images/char/defaultCharacter/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public UtsuhoReiuji(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "utsuhoReiujiResources/images/char/defaultCharacter/orb/vfx.png", null,
                new SpineAnimation(OKUU_SKELETON_IDLE_ATLAS, OKUU_SKELETON_IDLE_JSON, 0.47f));


        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in DefaultMod.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_2, // campfire pose
                THE_DEFAULT_SHOULDER_1, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================
        loadAnimation(OKUU_SKELETON_IDLE_ATLAS, OKUU_SKELETON_IDLE_JSON, 0.47f);
        for(Texture tex: atlas.getTextures()){
            tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        this.stateData.setMix("Idle", "PhysicalAttack", 0.2f);
        this.stateData.setMix("PhysicalAttack", "Idle", 0.2f);
        this.stateData.setMix("Idle", "Death", 0.0f);
        this.stateData.setMix("PhysicalAttack", "Death", 0.0f);


        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1f);

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =========== ATTACK AND HIT ANIMATIONS =====================

    @Override
    public void useFastAttackAnimation(){
        AnimationState.TrackEntry e = state.setAnimation(0, "PhysicalAttack", false);
        state.addAnimation(0,"Idle", true, 1.1f);
        e.setTimeScale(1f);
    }

    @Override
    public void damage(DamageInfo info)
    {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > currentBlock) {
            AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0,"Idle", true, 0.3f);
            e.setTimeScale(1f);
        }
        super.damage(info);
    }

    @Override
    public void playDeathAnimation(){
        AnimationState.TrackEntry e = state.setAnimation(0, "Death", false);
        e.setTimeScale(0.5f);
        //state.addAnimation(0,"Death", false, 0f);
    }


    // =========== /ATTACK AND HIT ANIMATIONS/ =====================

    //====================== SHADERS ========================
/*

    public static ShaderProgram shader = new ShaderProgram(
            Gdx.files.internal("E:/Game Projects/tools/shaders/vertexShader.vs"),
            Gdx.files.internal("E:/Game Projects/tools/shaders/fragShader.fs")
    );

    private static Texture galaxyTexture = new Texture(Gdx.files.internal("utsuhoReiujiResources/images/char/okuuSprites/loopingGalaxy.png"));

    public static float currentTime = 0.0f;

    @Override
    public void renderPlayerImage(SpriteBatch sb){
        if (this.atlas != null) {
            this.state.update(Gdx.graphics.getDeltaTime());
            this.state.apply(this.skeleton);
            this.skeleton.updateWorldTransform();
            currentTime += Gdx.graphics.getDeltaTime();
            shader.begin();
            CardCrawlGame.psb.setShader(shader);
            shader.setUniformf("Time", currentTime);
            galaxyTexture.bind(1);
            Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
            Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
            shader.setUniformi("u_galaxyTexture", 1);
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
            this.skeleton.setPosition(this.drawX + this.animX, this.drawY + this.animY);
            this.skeleton.setColor(this.tint.color);
            this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
            CardCrawlGame.psb.setShader(null);
            shader.end();
            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, this.skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        } else {
            sb.setColor(Color.WHITE);
            sb.draw(this.img, this.drawX - (float)this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY, (float)this.img.getWidth() * Settings.scale, (float)this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
        }
    }

    @Override
    public void render(SpriteBatch sb){
        this.stance.render(sb);
        if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !this.isDead) {
            this.renderHealth(sb);
            if (!this.orbs.isEmpty()) {
                Iterator var2 = this.orbs.iterator();

                while(var2.hasNext()) {
                    AbstractOrb o = (AbstractOrb)var2.next();
                    o.render(sb);
                }
            }
        }

        if (!(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
            currentTime += Gdx.graphics.getDeltaTime();
            if (atlas == null) {
                shader.begin();
                sb.setShader(shader);
                shader.setUniformf("Time", currentTime);
                galaxyTexture.bind(1);
                Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
                Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
                shader.setUniformi("u_galaxyTexture", 1);
                Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
            }
            if (this.atlas != null) {

                this.renderPlayerImage(sb);
            } else {
                sb.setColor(Color.WHITE);
                sb.draw(this.img, this.drawX - (float)this.img.getWidth() * Settings.scale / 2.0F + this.animX, this.drawY, (float)this.img.getWidth() * Settings.scale, (float)this.img.getHeight() * Settings.scale, 0, 0, this.img.getWidth(), this.img.getHeight(), this.flipHorizontal, this.flipVertical);
            }
            if (atlas == null) {
                sb.setShader(null);
                shader.end();
            }
            this.hb.render(sb);
            this.healthHb.render(sb);
        } else {
            sb.setColor(Color.WHITE);
            this.renderShoulderImg(sb);
        }
    }*/
    // ===================== /SHADERS/ =====================

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, START_ORBS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");

        retVal.add(HighTensionBlade.ID);

        retVal.add(BoilerExplosion.ID);
        retVal.add(BanishingStrike.ID);
        retVal.add(Grapeshot.ID);
        retVal.add(EmergencyShutdown.ID);

        retVal.add(CannonCross.ID);
        retVal.add(CreepingSun.ID);
        retVal.add(EightfoldConduct.ID);

        retVal.add(BurnOut.ID);


       // retVal.add(DefaultCommonSkill.ID);
       // retVal.add(DefaultUncommonSkill.ID);
       // retVal.add(DefaultRareSkill.ID);

       // retVal.add(DefaultCommonPower.ID);
      //  retVal.add(DefaultUncommonPower.ID);
       // retVal.add(DefaultRarePower.ID);

       // retVal.add(DefaultAttackWithVariable.ID);
       // retVal.add(DefaultSecondMagicNumberSkill.ID);
       // retVal.add(OrbSkill.ID);
        return retVal;
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(PlaceholderRelic.ID);
        retVal.add(PlaceholderRelic2.ID);
        retVal.add(DefaultClickableRelic.ID);

        UnlockTracker.markRelicAsSeen(PlaceholderRelic.ID);
        UnlockTracker.markRelicAsSeen(PlaceholderRelic2.ID);
        UnlockTracker.markRelicAsSeen(DefaultClickableRelic.ID);

        return retVal;
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 0;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return REIUJI_GREEN;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return OkuuMod.DEFAULT_GRAY;
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new DefaultCommonAttack();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new UtsuhoReiuji(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return OkuuMod.DEFAULT_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return OkuuMod.DEFAULT_GRAY;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }


}
