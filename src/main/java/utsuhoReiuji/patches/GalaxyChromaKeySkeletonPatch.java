package utsuhoReiuji.patches;

import com.badlogic.gdx.Gdx;
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
    private static ShaderProgram shader = new ShaderProgram(
            Gdx.files.internal("utsuhoReiujiResources/shaders/vertexShader.vs"),
            Gdx.files.internal("utsuhoReiujiResources/shaders/fragShader.fs")
    );

    @SpireInsertPatch(
            locator=LocatorSkeletonStart.class
    )
    public static void InsertSkeletonStart(AbstractPlayer __instance, SpriteBatch sb)
    {
            shader.begin();

            CardCrawlGame.psb.setShader(shader);
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
