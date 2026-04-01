package com.neo.v1.util;


import static java.lang.Boolean.TRUE;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.neo.v1.product.catalogue.model.OfferDetails;
import com.neo.v1.product.catalogue.model.OffersListData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
@Slf4j
public class OffersUtil {

    private OffersUtil() {
    }

    public static List<OfferDetails> getFilteredOffers(OffersListData offersListData, String offerCategory, String offerProduct, String offerSubProduct, boolean isNewOfferOnly) {
        boolean checkOfferProduct = StringUtils.isNotBlank(offerProduct);
        boolean checkOfferCategory = StringUtils.isNotBlank(offerCategory);
        boolean checkOfferSubProduct = StringUtils.isNotBlank(offerSubProduct);

        return offersListData.getOffers().stream()
                .filter(offerDetails -> (!checkOfferProduct || StringUtils.equals(offerDetails.getOfferProduct(), offerProduct)) &&
                        (!checkOfferCategory || StringUtils.equals(offerDetails.getOfferCategory().getName(), offerCategory)) &&
                        (!checkOfferSubProduct || offerDetails.getOfferApplicableSubProducts().stream().anyMatch(offerSubProduct1 -> Objects.nonNull(offerSubProduct1.getSubProduct()) && offerSubProduct.equalsIgnoreCase(offerSubProduct1.getSubProduct().getName())))
                        && (!isNewOfferOnly || Objects.equals(offerDetails.isIsNewOffer(), TRUE))
                )
                .collect(Collectors.toList());
    }

    public static String replaceHyperlink(String text , String offerId){
        if(StringUtils.isEmpty(text))
            return text;

        if(!text.contains("<br") && text.contains("\n"))
        {
            text = text.replace("\n", "<br>");
        }

        String linkText = null,website = null;
        String websiteRegex = "(?:(?:https?|http):\\/\\/|www\\.)(?:\\([-A-Z0-9+&@#/%=~_|$?!:,.]*\\)|[-A-Z0-9+&@#/%=~_|$?!:,.])*(?:\\([-A-Z0-9+&@#/%=~_|$?!:,.]*\\)|[A-Z0-9+&@#/%=~_|$])";
        Matcher matcher = Pattern.compile(websiteRegex,Pattern.CASE_INSENSITIVE|Pattern.MULTILINE).matcher(text);
        if (matcher.find()){
            website = matcher.group();
        }
        if(StringUtils.isEmpty(website))
            return text;

        if (text.indexOf("[")!=-1 && text.indexOf("]")!=-1){
            linkText = text.substring(text.indexOf("[")+1,text.indexOf("]"));
        }

        StringBuffer anchor = new StringBuffer("<a href = '");
        anchor.append(website).append("'>").append(StringUtils.isNotEmpty(linkText)?linkText.trim():website).append("</a>");
        if (StringUtils.isEmpty(linkText) && text.indexOf("<")!=-1){//only link inside <
            text = text.replace(text.substring(text.indexOf("<"),text.indexOf(">")+1),anchor);
        } else if (StringUtils.isEmpty(linkText) && text.indexOf("<")==-1) {//only link
            text = text.replace(text.substring(text.indexOf(website),text.indexOf(website)+website.length()),anchor);
        } else{//link with title text
            text = text.replace(text.substring(text.indexOf("["),text.indexOf(")")+1),anchor);
        }
        return text;
    }

}
