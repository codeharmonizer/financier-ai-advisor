package com.neo.v1.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class OffersUtilTest {

    @InjectMocks
    private OffersUtil subject;

    @ParameterizedTest
    @ValueSource(strings = {"https://google.com","<https://google.com>","[Link text](https://google.com)","[Link text ](https://google.com \"Link title\")"})
    void replaceHyperlink(String link) {
        String text = "•\tOffer only redeemable through the dedicated promotional link. \n" +
                "•\tBookings made with the Booking.com app or main website are "+link+" not eligible for this promotion. \n" +
                "•\tAccommodations that do not accept card payments and do not have a “cashback” badge displayed on their listing are not";
        String result = subject.replaceHyperlink(text , "123");
        assertTrue(result.contains("<a href"));
        assertFalse(result.contains("\n"));
    }

    @Test
    void replaceHyperlink_null_test() {
        String text = null;
        String result = subject.replaceHyperlink(text, "456");
        assertTrue(StringUtils.isEmpty(result));
    }

    @Test
    void replaceHyperlink_replaceNewLineWithBrTag() {
        String text = "•\tOffer only redeemable through the dedicated promotional link. \n" +
                "•\tBookings made with the Booking.com app or main website are not eligible for this promotion. \n" +
                "•\tAccommodations that do not accept card payments and do not have a “cashback” badge displayed on their listing are not";
        String result = subject.replaceHyperlink(text , "123");
        assertFalse(result.contains("<a href"));
        assertFalse(result.contains("\n"));
    }
}