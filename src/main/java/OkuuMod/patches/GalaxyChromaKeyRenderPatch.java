package OkuuMod.patches;

import OkuuMod.character.UtsuhoReiuji;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Hitbox;
import javassist.CtBehavior;

import static OkuuMod.OkuuMod.shaderPath;
import static OkuuMod.OkuuMod.characterPath;


//TODO work out if the image version of this patch is actually necessary

//Insert into the players render function
@SpirePatch(
        clz= AbstractPlayer.class,
        method="render"
)

public class GalaxyChromaKeyRenderPatch {
    //import the shader location
    public static ShaderProgram shader = new ShaderProgram(
            Gdx.files.internal(shaderPath("chromaKey/vertexShader.vs")),
            Gdx.files.internal(shaderPath("chromaKey/fragShader.fs"))
    );

    //Initialise time as a float
    public static float currentTime = 0.0f;

    //TODO Update delta time outside of this function
    private static Texture galaxyTexture = new Texture(characterPath("animation/loopingGalaxy.png"));

    @SpireInsertPatch(
            locator=LocatorImageStart.class,
            localvars={"atlas"}
    )

    //apply a shader to Utsuho's sprite
    public static void InsertImageStart(AbstractPlayer __instance, SpriteBatch sb, TextureAtlas atlas) {
        if(__instance instanceof UtsuhoReiuji) { //ensure this function is only applied to Utsuho.
            currentTime += Gdx.graphics.getDeltaTime(); //Needs delta time to scroll the galaxy texture appropriately.
            //if(!shader.isCompiled()) System.out.println(shader.getLog()); //Debug string, if shader is crashing, enable this to trace the problem.
            if (atlas == null) {
                shader.begin();
                sb.setShader(shader);
                shader.setUniformf("Time", currentTime); //send the delta time to the shader.
                galaxyTexture.bind(1); //send the galaxy texture to the shader
                Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_S, GL20.GL_REPEAT); //these set of 4 lines ensure the galaxy texture wraps as we move it along time.
                Gdx.gl.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_WRAP_T, GL20.GL_REPEAT);
                shader.setUniformi("u_galaxyTexture", 1);
                Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
            }
        }
    }

    @SpireInsertPatch(
            locator=LocatorImageEnd.class,
            localvars={"atlas"}
    )

    //Once the sprite has been rendered, end the shader so it doesn't apply the chromakey to other sprites.
    // Also cleans up the space so the game can apply other shaders to other things. What little it does of that.
    public static void InsertImageEnd(AbstractPlayer __instance, SpriteBatch sb, TextureAtlas atlas) {
        if(__instance instanceof UtsuhoReiuji) {
            if (atlas == null) {
                sb.setShader(null);
                shader.end();
            }
        }
    }

    //whenever the player's sprite atlas is accessed, add the patcher code.
    private static class LocatorImageStart extends SpireInsertLocator
    {
        @Override
        public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
        {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "atlas");
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }
    }

    //Similarly, once it renders the sprite, add the patcher code.
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