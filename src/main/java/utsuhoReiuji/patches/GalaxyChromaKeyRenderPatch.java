package utsuhoReiuji.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Timer;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;

import java.sql.Time;

@SpirePatch(
        clz= AbstractPlayer.class,
        method="render"
)


//TODO Remember to remove this after finished debugging
public class GalaxyChromaKeyRenderPatch {
    public static ShaderProgram shader = new ShaderProgram(
            Gdx.files.internal("E:/Game Projects/tools/shaders/vertexShader.vs"),
            Gdx.files.internal("E:/Game Projects/tools/shaders/fragShader.fs")
    );

    public static float currentTime = 0.0f;

    //TODO Update delta time outside of this function
    private static Texture galaxyTexture = new Texture(Gdx.files.internal("utsuhoReiujiResources/images/char/okuuSprites/loopingGalaxy.png"));

    @SpireInsertPatch(
            locator=LocatorImageStart.class,
            localvars={"atlas"}
    )
    public static void InsertImageStart(AbstractPlayer __instance, SpriteBatch sb, TextureAtlas atlas) {
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
    }

    @SpireInsertPatch(
            locator=LocatorImageEnd.class,
            localvars={"atlas"}
    )
    public static void InsertImageEnd(AbstractPlayer __instance, SpriteBatch sb, TextureAtlas atlas) {

        if (atlas == null) {
            sb.setShader(null);
            shader.end();
        }
    }

    private static class LocatorImageStart extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "atlas");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    private static class LocatorImageEnd extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "render");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}