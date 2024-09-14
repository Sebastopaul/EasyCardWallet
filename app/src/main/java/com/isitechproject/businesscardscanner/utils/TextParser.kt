package com.isitechproject.businesscardscanner.utils

import com.google.mlkit.vision.text.Text.Line
import com.google.mlkit.vision.text.Text.TextBlock
import com.isitechproject.easycardwallet.model.BusinessCard

class TextParser {
    private fun parseString(string: String, map: MutableMap<String, String>): Pair<String, String> {
        if (EMAIL_ADDRESS_REGEX.matches(string)
            && map[BusinessCard.CONTACT_EMAIL_FIELD].isNullOrEmpty()) {
            return Pair(BusinessCard.CONTACT_EMAIL_FIELD, string)
        }
        if (PHONE_REGEX.matches(string)
            && map[BusinessCard.CONTACT_PHONE_FIELD].isNullOrEmpty()) {
            return Pair(BusinessCard.CONTACT_PHONE_FIELD, string)
        }
        if (PHONE_REGEX.matches(string)
            && map[BusinessCard.CONTACT_PHONE_FIELD].isNullOrEmpty()
            && map[BusinessCard.CONTACT_MOBILE_FIELD].isNullOrEmpty()) {
            return Pair(BusinessCard.CONTACT_MOBILE_FIELD, string)
        }
        if (ADDRESS_REGEX.matches(string)
            && map[BusinessCard.ADDRESS_FIELD].isNullOrEmpty()) {
            return Pair(BusinessCard.ADDRESS_FIELD, string)
        }
        if (ZIPCODE_REGEX.matches(string)
            && map[BusinessCard.ZIP_FIELD].isNullOrEmpty()) {
            return Pair(BusinessCard.ZIP_FIELD, string)
        }
        if (CITY_REGEX.matches(string)
            && map[BusinessCard.CITY_FIELD].isNullOrEmpty()) {
            return Pair(BusinessCard.CITY_FIELD, string)
        }
        if (NAME_REGEX.matches(string)
            && map[BusinessCard.COMPANY_NAME_FIELD].isNullOrEmpty()
            && (map[BusinessCard.CONTACT_FIRSTNAME_FIELD].isNullOrEmpty()
            || map[BusinessCard.CONTACT_LASTNAME_FIELD].isNullOrEmpty())) {
            return Pair(BusinessCard.COMPANY_NAME_FIELD, string)
        }
        return Pair(BusinessCard.NAME_FIELD, string)
    }

    private fun parseLines(lines: List<Line>, map: MutableMap<String, String>): MutableMap<String, String> {
        for (line in lines) {
            val pair = parseString(line.text, map)

            if (pair.first == BusinessCard.COMPANY_NAME_FIELD && pair.second.split(" ").count() > 1) {
                val split = pair.second.split(" ")
                map[BusinessCard.CONTACT_FIRSTNAME_FIELD] = split.first()
                map[BusinessCard.CONTACT_LASTNAME_FIELD] = split.last()
            } else {
                map[pair.first] = pair.second
            }
        }
        return map
    }

    fun parseBlocks(textBlocks: List<TextBlock>): Map<String, String> {
        var map = mutableMapOf<String, String>()
        for (block in textBlocks) {
            val pair = parseString(block.text, map)
            if (block.lines.count() > 1) {
                map = parseLines(block.lines, map)
            } else if (pair.first == BusinessCard.COMPANY_NAME_FIELD && pair.second.split(" ").count() > 1) {
                val split = pair.second.split(" ")
                map[BusinessCard.CONTACT_FIRSTNAME_FIELD] = split.first()
                map[BusinessCard.CONTACT_LASTNAME_FIELD] = split.last()
            } else {
                map[pair.first] = pair.second
            }
        }

        return map
    }

    companion object {
        val EMAIL_ADDRESS_REGEX = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
        )

        val PHONE_REGEX = Regex(
            "(\\+[0-9]+[\\- \\.]*)?"
                + "(\\([0-9]+\\)[\\- \\.]*)?"
                + "([0-9][0-9\\- \\.]+[0-9])"
        )

        val ADDRESS_REGEX = Regex(
            "(([a-zA-Z-éÉèÈàÀùÙâÂêÊîÎôÔûÛïÏëËüÜçÇæœ'.]*\\s)\\d*" +
                "(\\s[a-zA-Z-éÉèÈàÀùÙâÂêÊîÎôÔûÛïÏëËüÜçÇæœ']*)*,)*\\d*" +
                "(\\s[a-zA-Z-éÉèÈàÀùÙâÂêÊîÎôÔûÛïÏëËüÜçÇæœ']*)+," +
                "\\s([\\d]{5})\\s[a-zA-Z-éÉèÈàÀùÙâÂêÊîÎôÔûÛïÏëËüÜçÇæœ']+"
        )

        val ZIPCODE_REGEX = Regex(
            "(?:0[1-9]|[1-8]\\d|9[0-8])\\d{3}"
        )

        val CITY_REGEX = Regex(
            "([A-Z]+[-' ]?[A-Z]+)*"
        )

        val NAME_REGEX = Regex(
            "[[:alpha:]]([- ]?[[:alpha:]])*"
        )
    }
}