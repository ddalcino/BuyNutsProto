# BuyNutsProto

This is our prototype for CS4310, group Epsilon.

Coding Standards (guidelines, not set in stone):

<b>Java code:</b>

1) All class, interface, and enums are written in CamelCase, with the first letter capitalized, as required by the Java compiler.

2) All variable names in Java code are written in camelCase, with the first letter in lowercase, except for:

3) Constants declared as 'static final' are written in all caps with underscores between words, LIKE_THIS.

4)Every class, member method, and member variable should have a JavaDoc, particularly if someone else needs to use that class. If someone else has to use your code, don't make them read the whole method to find out what it does or find out how it handles invalid parameters: just describe it in the JavaDoc to save that person some time.

<br/>
<br/>

<b>XML code:</b>

1) Resource identifiers: If it's an id that will be accessed from Java code, make it camelCase to fit in with the rest of the Java code. If the only place you'll ever see it is in an xml file, make it lowercase with underscores between words, so that it fits in with the rest of the xml.

2) Exception to resource identifiers rule: If you have identifiers for two different objects that exist in two different layouts, but they have very similar (or identical) names, and they are not meant to be the same object, they need to be easily distinguished from each other, and in that case you can mix camelCase with underscores. For example, I have a spinner in the activity_make_offer.xml layout called spinnerCommodityType_MO, and another spinner in the activity_set_search_filter.xml layout called spinnerCommodityType_SF. This odd naming convention makes it immediately clear that they are not the same object, and they belong in two different places. <i>I welcome other suggestions as to how to solve this problem, since this isn't an established convention and it looks kind of weird. </i>

3) Where possible, text values should be stored in the strings.xml file and not in the layout xml file that uses them. The only exception is for placeholder strings that won't exist in the final product.

