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
        method="render"
)

public class GalaxyChromaKeyRenderPatch {
    private static ShaderProgram shader = new ShaderProgram(
            Gdx.files.internal("utsuhoReiujiResources/shaders/vertexShader.vs"),
            Gdx.files.internal("utsuhoReiujiResources/shaders/fragShader.fs")
    );

    @SpireInsertPatch(
            locator=LocatorImageStart.class,
            localvars={"atlas"}
    )
    public static void InsertImageStart(AbstractPlayer __instance, SpriteBatch sb, TextureAtlas atlas) {
        if (atlas == null) {
            shader.begin();
            sb.setShader(shader);
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
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "isDead");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }
}
