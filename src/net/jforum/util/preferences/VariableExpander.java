/*
 * Created on May 31, 2004 by pieter
 *
 */
package net.jforum.util.preferences;

import java.util.HashMap;
import java.util.Map;


public class VariableExpander {
    private static final int MAX_REPLACEMENTS = 15;
    
    private VariableStore variables;
    private String pre;
    private String post;
    
    private Map cache;
    
    public VariableExpander(VariableStore variables, String pre, String post) {
        this.variables = variables;
        this.pre = pre;
        this.post = post;
        cache = new HashMap();
    }

    public void clearCache() {
        cache.clear();
    }
    
    public String expandVariables(String source) {
        String result = (String) cache.get(source);
        if (result != null) {
            return result;
        }
        
        boolean found = true;
        int count = 0;
        
        while (found && count++ < MAX_REPLACEMENTS) {
            found = false;
            int start = source.lastIndexOf(pre);
            if (start != -1) {
                int end = source.indexOf(post, start);
                if (end != -1) {
                    found = true;
                    String prefix = source.substring(0, start);
                    String postfix = source.substring(end + post.length());
                    String name = source.substring(start + pre.length(), end);
                    name = expandVariables(name);
                    int assign = name.indexOf('=');
                    String defaultValue = null;
                    if (assign >= 0) {
                        defaultValue = name.substring(assign+1);
                        name = name.substring(0, assign);
                    }
                    String value = variables.getVariableValue(name);
                    if (value == null) {
                        value = defaultValue;
                        if (value == null) {
                            throw new RuntimeException("variable not defined: " + name);
                        }
                    }
                    source = prefix + value + postfix;
                }
            }
        }

        return source;
    }

}
