package OkuuMod.cardMods;


import basemod.abstracts.AbstractCardModifier;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.CommonKeywordIconsField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import utsuhoReiuji_old.util.HelperClass;

public class EtherealCardMod extends AbstractCardModifier {

    public EtherealCardMod() {
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
            card.isEthereal = true;
    }

    @Override
        public void onRemove(AbstractCard card) {
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return (!CommonKeywordIconsField.useIcons.get(card)?HelperClass.capitalize(GameDictionary.ETHEREAL.NAMES[0] + LocalizedStrings.PERIOD) + " NL ":"") + rawDescription;
    }

    public boolean isApplicable(AbstractCard card){
        return !card.isEthereal;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        try {
            return new EtherealCardMod();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
