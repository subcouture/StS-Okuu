package utsuhoReiuji.patches;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CtBehavior;


@SpirePatch(
        clz= AbstractPlayer.class,
        method="renderPlayerImage"
)

public class GalaxyChromaKeySkeletonPatch {
    public static ShaderProgram shader = new ShaderProgram(
//            Gdx.files.internal("utsuhoReiujiResources/shaders/chromaKey/vertexShader.vs"),
            Gdx.files.internal("E:/Game Projects/tools/shaders/vertexShader.vs"),
            Gdx.files.internal("E:/Game Projects/tools/shaders/fragShader.fs")
    );

    private static Texture galaxyTexture = new Texture(Gdx.files.internal("utsuhoReiujiResources/images/char/okuuSprites/loopingGalaxy.png"));

    public static float currentTime = 0.0f;

    @SpireInsertPatch(
            locator=LocatorSkeletonStart.class
    )
    public static void InsertSkeletonStart(AbstractPlayer __instance, SpriteBatch sb)
    {
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

    @SpireInsertPatch(
            locator=LocatorSkeletonEnd.class
    )
    public static void InsertSkeletonEnd(AbstractPlayer __instance, SpriteBatch sb)
    {
            CardCrawlGame.psb.setShader(null);
            shader.end();
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