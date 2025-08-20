package OkuuMod.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import javassist.CtBehavior;
import utsuhoReiuji_old.characters.UtsuhoReiuji;

//Consult the Render Patch for explanations of what this class does and how it works.
//It's been a little while since I wrote it, but I think this patch exists so the chromakey continues to work correctly for non-atlas'd sprites
//Like the death animation, or campfire shoulder animations.

@SpirePatch(
        clz= AbstractPlayer.class,
        method="renderPlayerImage"
)

public class GalaxyChromaKeySkeletonPatch {
    public static ShaderProgram shader = new ShaderProgram(
            Gdx.files.internal("utsuhoReiujiResources_old/shaders/chromaKey/vertexShader.vs"),
            Gdx.files.internal("utsuhoReiujiResources_old/shaders/chromaKey/fragShader.fs")
    );

    private static Texture galaxyTexture = new Texture(Gdx.files.internal("utsuhoReiujiResources_old/images/char/okuuSprites/loopingGalaxy.png"));

    public static float currentTime = 0.0f;

    @SpireInsertPatch(
            locator=LocatorSkeletonStart.class
    )
    public static void InsertSkeletonStart(AbstractPlayer __instance, SpriteBatch sb)
    {
        if(__instance instanceof UtsuhoReiuji) {
            currentTime += Gdx.graphics.getDeltaTime();
            shader.begin();
            CardCrawlGame.psb.setShader(shader);
            shader.setUniformf("Time", currentTime);
            galaxyTexture.bind(1);
            Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT);
            Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
            shader.setUniformi("u_galaxyTexture", 1);
            Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        }
    }

    @SpireInsertPatch(
            locator=LocatorSkeletonEnd.class
    )
    public static void InsertSkeletonEnd(AbstractPlayer __instance, SpriteBatch sb)
    {
        if(__instance instanceof UtsuhoReiuji) {
            CardCrawlGame.psb.setShader(null);
            shader.end();
        }
    }

    private static class LocatorSkeletonStart extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(SkeletonMeshRenderer.class, "draw");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    private static class LocatorSkeletonEnd extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.MethodCallMatcher(PolygonSpriteBatch.class, "end");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}