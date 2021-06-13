package com.house.framework.web.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.house.framework.commons.cache.DictCacheUtil;
import com.house.framework.entity.DictItem;

public final class XSSSecurityManager {
	
	private Log logger = LogFactory.getLog(this.getClass());

    /** regex flag union representing /si modifiers in php **/
    private static final int REGEX_FLAGS_SI = Pattern.CASE_INSENSITIVE | Pattern.DOTALL;
    private static final Pattern P_COMMENTS = Pattern.compile("<!--(.*?)-->", Pattern.DOTALL);
    private static final Pattern P_COMMENT = Pattern.compile("^!--(.*)--$", REGEX_FLAGS_SI);
    private static final Pattern P_TAGS = Pattern.compile("<(.*?)>", Pattern.DOTALL);
    private static final Pattern P_END_TAG = Pattern.compile("^/([a-z0-9]+)", REGEX_FLAGS_SI);
    private static final Pattern P_START_TAG = Pattern.compile("^([a-z0-9]+)(.*?)(/?)$", REGEX_FLAGS_SI);
    private static final Pattern P_QUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)=([\"'])(.*?)\\2", REGEX_FLAGS_SI);
    private static final Pattern P_UNQUOTED_ATTRIBUTES = Pattern.compile("([a-z0-9]+)(=)([^\"\\s']+)", REGEX_FLAGS_SI);
    private static final Pattern P_PROTOCOL = Pattern.compile("^([^:]+):", REGEX_FLAGS_SI);
    private static final Pattern P_ENTITY = Pattern.compile("&#(\\d+);?");
    private static final Pattern P_ENTITY_UNICODE = Pattern.compile("&#x([0-9a-f]+);?");
    private static final Pattern P_ENCODE = Pattern.compile("%([0-9a-f]{2});?");
    private static final Pattern P_VALID_ENTITIES = Pattern.compile("&([^&;]*)(?=(;|&|$))");
    private static final Pattern P_VALID_QUOTES = Pattern.compile("(>|^)([^<]+?)(<|$)", Pattern.DOTALL);
    private static final Pattern P_END_ARROW = Pattern.compile("^>");
    private static final Pattern P_BODY_TO_END = Pattern.compile("<([^>]*?)(?=<|$)");
    private static final Pattern P_XML_CONTENT = Pattern.compile("(^|>)([^<]*?)(?=>)");
    private static final Pattern P_STRAY_LEFT_ARROW = Pattern.compile("<([^>]*?)(?=<|$)");
    private static final Pattern P_STRAY_RIGHT_ARROW = Pattern.compile("(^|>)([^<]*?)(?=>)");
    private static final Pattern P_AMP = Pattern.compile("&");
    private static final Pattern P_QUOTE = Pattern.compile("\"");
    private static final Pattern P_LEFT_ARROW = Pattern.compile("<");
    private static final Pattern P_RIGHT_ARROW = Pattern.compile(">");
    private static final Pattern P_BOTH_ARROWS = Pattern.compile("<>");
    private static final Pattern P_JS_SCRIPT = Pattern.compile("[javascript:]*(<script.*?</script>+)+", REGEX_FLAGS_SI);
    private static final Pattern P_JS_ALERT = Pattern.compile("[javascript:]*(alert[\\s\\S]*[\\)]+)+", REGEX_FLAGS_SI);
    private static final Pattern P_JS_PROMPT = Pattern.compile("[javascript:]*(prompt[\\s\\S]*[\\)]+)+", REGEX_FLAGS_SI);
    private static final Pattern P_A_HREF = Pattern.compile("<[\\s]*[a][\\s]*[href][\\s\\S]*[=][\\s\\S]*>", REGEX_FLAGS_SI);
   private static final Pattern P_A_IMG = Pattern.compile("<img\\s+.*\\s*\\/{0,1}>", REGEX_FLAGS_SI);
    private static final Pattern P_SINGLE_QUOTES = Pattern.compile("'", REGEX_FLAGS_SI);
    private static final Pattern P_SPECIAL_CHARATER=Pattern.compile("[<>\"\\\\()+]*");//特殊字符正则表达式
    // @xxx could grow large... maybe use sesat's ReferenceMap
    private static final ConcurrentMap<String,Pattern> P_REMOVE_PAIR_BLANKS = new ConcurrentHashMap<String, Pattern>();
    private static final ConcurrentMap<String,Pattern> P_REMOVE_SELF_BLANKS = new ConcurrentHashMap<String, Pattern>();

    /** set of allowed html elements, along with allowed attributes for each element **/
    private final Map<String, List<Attribute>> vAllowed;
    
    /** counts of open tags for each (allowable) html element **/
    private final Map<String, Integer> vTagCounts = new HashMap<String, Integer>();

    /** html elements which must always be self-closing (e.g. "<img />") **/
    private final String[] vSelfClosingTags;
    /** html elements which must always have separate opening and closing tags (e.g. "<b></b>") **/
    private final String[] vNeedClosingTags;
    /** set of disallowed html elements **/
    private final String[] vDisallowed;
    /** attributes which should be checked for valid protocols **/
    private final String[] vProtocolAtts;
    /** allowed protocols **/
    private final String[] vAllowedProtocols;
    /** tags which should be removed if they contain no content (e.g. "<b></b>" or "<b />") **/
    private final String[] vRemoveBlanks;
    /** entities allowed within html markup **/
    private final String[] vAllowedEntities;
    /** flag determining whether comments are allowed in input String. */
    private final boolean stripComment;
    private boolean vDebug = false;
    /**
     * flag determining whether to try to make tags when presented with "unbalanced"
     * angle brackets (e.g. "<b text </b>" becomes "<b> text </b>").  If set to false,
     * unbalanced angle brackets will be html escaped.
     */
    private final boolean alwaysMakeTags;
    
    private ArrayList<Attribute> empty_atts = new ArrayList<Attribute>();
    
    
    private static XSSSecurityManager filter = new XSSSecurityManager();

    // for complex attributes like style="color:red;font-style:italic"
    private class Attribute{
    	
    	String attrName;
    	
    	Map<String, Pattern> allowedAttrValues;
    	
    	public Attribute(String attrName)
    	{
    		this.attrName = attrName;
    	}
    	
    	public Attribute(String attrName, Map<String, Pattern> map)
    	{
    		this.attrName = attrName;
    		allowedAttrValues = map;
    	}

    	
    	
    }
    
    public static String filter(String input)
    {
    	return filter.dofilter(input);
    }
    
    public static String filter(String url,String input)
    {
    	String s = input;
    	s = filter.dofilter(s);
    	if(!checkUrl(url))
    	{
    		s = filter.needFilter(s);
    	}
    	 if(isFilterSpecialChar(url)){
         	s=filter.filterSpecialCharacter(s);
         }
    	return s;
    }
    /**
     * 过滤特殊字符
     * @param s
     * @return
     */
    private String filterSpecialCharacter(String s) {
    	Matcher matcher=P_SPECIAL_CHARATER.matcher(s);
    	if(matcher.find()){
    		s=matcher.replaceAll("");
    	}
		return s;
	}

	public static Boolean checkUrl(String url)
    {
    	List<DictItem> list = DictCacheUtil.getItemsByDictCode("UGC_FILTER_NONEED");
    	if(list != null && list.size()>0)
    	{
    		for(DictItem item : list)
        	{
        		if(url.equals(item.getItemValue()))
        		{
        			return true;
        		}
        	}
    	}
    	return false;
    }
    
    /** Default constructor.
     *
     */
    private XSSSecurityManager() {
        vAllowed = new HashMap<String, List<Attribute>>();

        
        
        {
	        final ArrayList<Attribute> f_atts = new ArrayList<Attribute>();
	        f_atts.add(new Attribute("color"));
	        f_atts.add(new Attribute("size"));
	        f_atts.add(new Attribute("face"));
	        //f_atts.add(".background-color");
	        vAllowed.put("font", f_atts);
        }

        {
	        final ArrayList<Attribute> span_atts = new ArrayList<Attribute>();  
	        Map<String, Pattern> allowedAttrValues = new HashMap<String, Pattern>();
	        allowedAttrValues.put("color", Pattern.compile("(#([0-9a-fA-F]{6}|[0-9a-fA-F]{3}))"));
	        allowedAttrValues.put("font-weight", Pattern.compile("bold"));
	        allowedAttrValues.put("text-align", Pattern.compile("(center|right|justify)"));
	        allowedAttrValues.put("font-style", Pattern.compile("italic"));
	        allowedAttrValues.put("text-decoration", Pattern.compile("underline"));
	        allowedAttrValues.put("margin-left", Pattern.compile("[0-9]+px"));
	        allowedAttrValues.put("text-align", Pattern.compile("center"));
	        
	        span_atts.add(new Attribute("style", allowedAttrValues));
	        vAllowed.put("span", span_atts);
        }
        
        {
	        final ArrayList<Attribute> div_atts = new ArrayList<Attribute>();
	        div_atts.add(new Attribute("class"));
	        div_atts.add(new Attribute("align"));        
	        vAllowed.put("div", div_atts);
        }
        
        {
	        final ArrayList<Attribute> table_atts = new ArrayList<Attribute>();
	        table_atts.add(new Attribute("class"));
	        table_atts.add(new Attribute("border"));
	        table_atts.add(new Attribute("cellspacing"));
	        table_atts.add(new Attribute("cellpadding"));
	        table_atts.add(new Attribute("width"));
	        table_atts.add(new Attribute("height"));
	        table_atts.add(new Attribute("align"));
	        vAllowed.put("table", table_atts);
        }
        
        vAllowed.put("tr", empty_atts); 
        vAllowed.put("td", empty_atts);
        vAllowed.put("br", empty_atts);

        {
	        final ArrayList<Attribute> a_atts = new ArrayList<Attribute>();
	        a_atts.add(new Attribute("href"));
	        a_atts.add(new Attribute("target"));
	        vAllowed.put("a", a_atts);
        }

        {
	        final ArrayList<Attribute> img_atts = new ArrayList<Attribute>();
	        img_atts.add(new Attribute("src"));
	        img_atts.add(new Attribute("width"));
	        img_atts.add(new Attribute("height"));
	        img_atts.add(new Attribute("alt"));
	        vAllowed.put("img", img_atts);
        }
        
        vAllowed.put("b", empty_atts);
        vAllowed.put("p", empty_atts);
        vAllowed.put("strong", empty_atts);
        vAllowed.put("i", empty_atts);
        vAllowed.put("em", empty_atts);

        vSelfClosingTags = new String[]{"img"};
        vNeedClosingTags = new String[]{"a", "font", "b", "p", "strong", "i", "em"};
        vDisallowed = new String[]{};
        vAllowedProtocols = new String[]{"http", "mailto"}; // no ftp.
        vProtocolAtts = new String[]{"src", "href"};
        vRemoveBlanks = new String[]{"a", "b", "strong", "i", "em"};
        vAllowedEntities = new String[]{"amp", "gt", "lt", "quot"};
        stripComment = true;
        alwaysMakeTags = true;
    }

    /** Set debug flag to true. Otherwise use default settings. See the default constructor.
     *
     * @param debug turn debug on with a true argument
     */
    public XSSSecurityManager(final boolean debug) {
        this();
        vDebug = debug;

    }


    private void reset() {
        vTagCounts.clear();
    }

    private void debug(final String msg) {
        if (vDebug) {
        	logger.debug(msg);
        }
    }

    //---------------------------------------------------------------
    // my versions of some PHP library functions
    public static String chr(final int decimal) {
        return String.valueOf((char) decimal);
    }

    public static String htmlSpecialChars(final String s) {
        String result = s;
        result = regexReplace(P_AMP, "&amp;", result);
        result = regexReplace(P_QUOTE, "&quot;", result);
        result = regexReplace(P_LEFT_ARROW, "&lt;", result);
        result = regexReplace(P_RIGHT_ARROW, "&gt;", result);
        return result;
    }

    //---------------------------------------------------------------
    /**
     * given a user submitted input String, filter out any invalid or restricted
     * html.
     *
     * @param input text (i.e. submitted by a user) than may contain html
     * @return "clean" version of input, with only valid, whitelisted html elements allowed
     */
    private String dofilter(final String input) {
        reset();
        String s = input;

        debug("************************************************");
        debug("              INPUT: " + input);

        s = escapeComments(s);
        debug("     escapeComments: " + s);

        s = balanceHTML(s);
        debug("        balanceHTML: " + s);

        s = processRemoveBlanks(s);
        debug("processRemoveBlanks: " + s);

        s = validateEntities(s);
        debug("    validateEntites: " + s);
        
        s = removeJS(s);
        debug("************************************************\n\n");
        return s;
    }
    /**
     * 是否过滤特殊字符
     * @return
     */
    private static boolean isFilterSpecialChar(String url) {
    	List<DictItem> list = DictCacheUtil.getItemsByDictCode("UGC_FILTER_SPECIAL_NOT_NEED");
    	if(list != null && list.size()>0)
    	{
    		for(DictItem item : list)
        	{
        		if(url.equals(item.getItemValue()))
        		{
        			return false;
        		}
        	}
    	}
    	return true;
	}

	public String needFilter(String input)
    {
    	String s = input;
    	s = removeScript(s);
    	s = checkTags(s);
		s = removeAHref(s);
		s = removeSingleQuotes(s);
		s = removeImg(s);
		return s;
    }
    
    public boolean isAlwaysMakeTags(){
        return alwaysMakeTags;
    }

    public boolean isStripComments(){
        return stripComment;
    }

    private String escapeComments(final String s) {
        final Matcher m = P_COMMENTS.matcher(s);
        final StringBuffer buf = new StringBuffer();
        if (m.find()) {
            final String match = m.group(1); //(.*?)
            m.appendReplacement(buf, "<!--" + htmlSpecialChars(match) + "-->");
        }
        m.appendTail(buf);

        return buf.toString();
    }

    private String balanceHTML(String s) {
        if (alwaysMakeTags) {
            //
            // try and form html
            //
            s = regexReplace(P_END_ARROW, "", s);
            s = regexReplace(P_BODY_TO_END, "<$1>", s);
            s = regexReplace(P_XML_CONTENT, "$1<$2", s);

        } else {
            //
            // escape stray brackets
            //
            s = regexReplace(P_STRAY_LEFT_ARROW, "&lt;$1", s);
            s = regexReplace(P_STRAY_RIGHT_ARROW, "$1$2&gt;<", s);

            //
            // the last regexp causes '<>' entities to appear
            // (we need to do a lookahead assertion so that the last bracket can
            // be used in the next pass of the regexp)
            //
            s = regexReplace(P_BOTH_ARROWS, "", s);
        }

        return s;
    }

    private String checkTags(String s) {
        Matcher m = P_TAGS.matcher(s);

        final StringBuffer buf = new StringBuffer();
        while (m.find()) {
            String replaceStr = m.group(1);
            replaceStr = processTag(replaceStr);
            m.appendReplacement(buf, replaceStr);
        }
        m.appendTail(buf);

        s = buf.toString();

        // these get tallied in processTag
        // (remember to reset before subsequent calls to filter method)
        for (String key : vTagCounts.keySet()) {
            for (int ii = 0; ii < vTagCounts.get(key); ii++) {
                s += "</" + key + ">";
            }
        }

        return s;
    }

    private String processRemoveBlanks(final String s) {
        String result = s;
        for (String tag : vRemoveBlanks) {
            if(!P_REMOVE_PAIR_BLANKS.containsKey(tag)){
                P_REMOVE_PAIR_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?></" + tag + ">"));
            }
            result = regexReplace(P_REMOVE_PAIR_BLANKS.get(tag), "", result);
            if(!P_REMOVE_SELF_BLANKS.containsKey(tag)){
                P_REMOVE_SELF_BLANKS.putIfAbsent(tag, Pattern.compile("<" + tag + "(\\s[^>]*)?/>"));
            }
            result = regexReplace(P_REMOVE_SELF_BLANKS.get(tag), "", result);
        }

        return result;
    }

    private static String regexReplace(final Pattern regex_pattern, final String replacement, final String s) {
        Matcher m = regex_pattern.matcher(s);
        return m.replaceAll(replacement);
    }

    private String processTag(final String s) {
        // ending tags
        Matcher m = P_END_TAG.matcher(s);
        if (m.find()) {
            final String name = m.group(1).toLowerCase();
            if (allowed(name)) {
                if (!inArray(name, vSelfClosingTags)) {
                    if (vTagCounts.containsKey(name)) {
                        vTagCounts.put(name, vTagCounts.get(name) - 1);
                        return "</" + name + ">";
                    }
                }
            }
        }

        // starting tags
        m = P_START_TAG.matcher(s);
        if (m.find()) {
            final String name = m.group(1).toLowerCase();
            final String body = m.group(2);
            String ending = m.group(3);

            //debug( "in a starting tag, name='" + name + "'; body='" + body + "'; ending='" + ending + "'" );
            if (allowed(name)) {
                String params = "";

                final Matcher m2 = P_QUOTED_ATTRIBUTES.matcher(body);
                final Matcher m3 = P_UNQUOTED_ATTRIBUTES.matcher(body);
                final List<String> paramNames = new ArrayList<String>();
                final List<String> paramValues = new ArrayList<String>();
                while (m2.find()) {
                    paramNames.add(m2.group(1)); //([a-z0-9]+)
                    paramValues.add(m2.group(3)); //(.*?)
                }
                while (m3.find()) {
                    paramNames.add(m3.group(1)); //([a-z0-9]+)
                    paramValues.add(m3.group(3)); //([^\"\\s']+)
                }

                String paramName, paramValue;
                for (int ii = 0; ii < paramNames.size(); ii++) {
                    paramName = paramNames.get(ii).toLowerCase();
                    paramValue = paramValues.get(ii);

//          debug( "paramName='" + paramName + "'" );
//          debug( "paramValue='" + paramValue + "'" );
//          debug( "allowed? " + vAllowed.get( name ).contains( paramName ) );

                    if (allowedAttribute(name, paramName, paramValue)) {
                        if (inArray(paramName, vProtocolAtts)) {
                            paramValue = processParamProtocol(paramValue);
                        }
                        params += " " + paramName + "=\"" + paramValue + "\"";
                    }
                }

                if (inArray(name, vSelfClosingTags)) {
                    ending = " /";
                }

                if (inArray(name, vNeedClosingTags)) {
                    ending = "";
                }

                if (ending == null || ending.length() < 1) {
                    if (vTagCounts.containsKey(name)) {
                        vTagCounts.put(name, vTagCounts.get(name) + 1);
                    } else {
                        vTagCounts.put(name, 1);
                    }
                } else {
                    ending = " /";
                }
                return "<" + name + params + ending + ">";
            } else {
                return "";
            }
        }

        // comments
        m = P_COMMENT.matcher(s);
        if (!stripComment && m.find()) {
            return  "<" + m.group() + ">";
        }

        return "";
    }

    private String processParamProtocol(String s) {
        s = decodeEntities(s);
        final Matcher m = P_PROTOCOL.matcher(s);
        if (m.find()) {
            final String protocol = m.group(1);
            if (!inArray(protocol, vAllowedProtocols)) {
                // bad protocol, turn into local anchor link instead
                s = "#" + s.substring(protocol.length() + 1, s.length());
                if (s.startsWith("#//")) {
                    s = "#" + s.substring(3, s.length());
                }
            }
        }

        return s;
    }

    private String decodeEntities(String s) {
        StringBuffer buf = new StringBuffer();

        Matcher m = P_ENTITY.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.decode(match).intValue();
            m.appendReplacement(buf, chr(decimal));
        }
        m.appendTail(buf);
        s = buf.toString();

        buf = new StringBuffer();
        m = P_ENTITY_UNICODE.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.valueOf(match, 16).intValue();
            m.appendReplacement(buf, chr(decimal));
        }
        m.appendTail(buf);
        s = buf.toString();

        buf = new StringBuffer();
        m = P_ENCODE.matcher(s);
        while (m.find()) {
            final String match = m.group(1);
            final int decimal = Integer.valueOf(match, 16).intValue();
            m.appendReplacement(buf, chr(decimal));
        }
        m.appendTail(buf);
        s = buf.toString();

        s = validateEntities(s);
        return s;
    }

    private String validateEntities(String s) {
        StringBuffer buf = new StringBuffer();

        // validate entities throughout the string
        Matcher m = P_VALID_ENTITIES.matcher(s);
        while (m.find()) {
            final String one = m.group(1); //([^&;]*)
            final String two = m.group(2); //(?=(;|&|$))
            final String replacement = Matcher.quoteReplacement(checkEntity(one, two));
            m.appendReplacement(buf, replacement);
        }
        m.appendTail(buf);
        s = buf.toString();

        // validate quotes outside of tags
        buf = new StringBuffer();
        m = P_VALID_QUOTES.matcher(s);
        while (m.find()) {
            final String one = m.group(1); //(>|^)
            final String two = m.group(2); //([^<]+?)
            final String three = m.group(3); //(<|$)
            m.appendReplacement(buf, Matcher.quoteReplacement(one + regexReplace(P_QUOTE, "&quot;", two) + three));
        }
        m.appendTail(buf);
        s = buf.toString();

        return s;
    }
    
    private String removeJS(final String s){
    	String ss=regexReplace(P_JS_PROMPT, "", s);
    	return regexReplace(P_JS_ALERT, "", ss);
    }
    
    private String removeScript(final String s){
    	String ss=regexReplace(P_JS_SCRIPT, "", s);
    	return regexReplace(P_JS_SCRIPT, "", ss);
    }
    
    private String removeAHref(final String s){
    	return regexReplace(P_A_HREF, "", s);
    }
    
    private String removeImg(final String s){
    	return regexReplace(P_A_IMG, "", s);
    }
    
    /**
     * 替换“'”号内容
     * 
     *@param s
     *@return String
     */
    private String removeSingleQuotes(final String s){
    	return regexReplace(P_SINGLE_QUOTES, "", s);
    	
    }

    private String checkEntity(final String preamble, final String term) {

        return ";".equals(term) && isValidEntity(preamble)
                ? '&' + preamble
                : "&amp;" + preamble;
    }

    private boolean isValidEntity(final String entity) {
        return inArray(entity, vAllowedEntities);
    }

    private static boolean inArray(final String s, final String[] array) {
        for (String item : array) {
            if (item != null && item.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean allowed(final String name) {
        return (vAllowed.isEmpty() || vAllowed.containsKey(name)) && !inArray(name, vDisallowed);
    }

    private boolean allowedAttribute(final String name, final String paramName, final String paramValue) {
    	if(!allowed(name))
    	{
    		return false;
    	}
    	
    	if(vAllowed.isEmpty() )
    	{
    		return false;
    	}
    	
    	List<Attribute> list = vAllowed.get(name);
    	if(null != list)
    	{
    		for(Attribute attr : list)
    		{
    			if(attr.attrName.equalsIgnoreCase(paramName))
    			{
    				// see if no constaints 
    				if(null == attr.allowedAttrValues)
    				{
    					
    					return true;
    				}
    				else
    				{
    					Map<String, String> attrValues = parseAttrValues(paramValue);
    					
    					for(String key: attrValues.keySet())
    					{
    						String value = attrValues.get(key);
    						
    						if(attr.allowedAttrValues.containsKey(key) && attr.allowedAttrValues.get(key).matcher(value).matches())
    						{
    							return true;
    						}
    						
    					}
    				}
    			}
    		}
    	}
    	//if((null != list) && (list.size() > 0))
    	
    	return false;
        //return  && (|| .contains(paramName));
    }
    
    
    /**
     * 
     * @param attrValue
     * @return
     */
    private static Map<String, String> parseAttrValues(String attrValue)
    {	
    	Map<String, String> values = new HashMap<String, String>();
    	
    	if((null != attrValue) && !"".equalsIgnoreCase(attrValue.trim()))
    	{
    		if(attrValue.startsWith("\""))
    		{
    			attrValue = attrValue.substring(1);
    		}
    		if(attrValue.endsWith("\""))
    		{
    			attrValue = attrValue.substring(0, attrValue.length()-1);
    		}
    		
    		String[] list = attrValue.split(";");
    		if(null != list)
    		{
    			for(String str: list)
    			{
    				int index = str.indexOf(":");
    				
    				if((index > 0) && (index < str.length()))
    				{
    					values.put(str.substring(0, index), str.substring(index+1, str.length()));
    				}
    			}
    		}
    	}
    	
    	return values;
    }
}
