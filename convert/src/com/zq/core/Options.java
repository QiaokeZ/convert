package com.zq.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Options {
    Set<String> whitelist;
    Set<String> blacklist;
    Map<String, String> mapping;

    public Set<String> getWhitelist() {
        return whitelist;
    }

    public Set<String> getBlacklist() {
        return blacklist;
    }

    public Map<String, String> getMapping() {
        return mapping;
    }

    private Options() {
    }

    private Options(Set<String> whitelist, Set<String> blacklist, Map<String, String> mapping) {
        this.whitelist = whitelist;
        this.blacklist = blacklist;
        this.mapping = mapping;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        Set<String> whitelist;
        Set<String> blacklist;

        Map<String, String> mapping = new HashMap<>();

        public Builder whitelist(Set<String> whitelist) {
            this.whitelist = whitelist;
            this.blacklist = null;
            return this;
        }

        public Builder blacklist(Set<String> blacklist) {
            this.blacklist = blacklist;
            this.whitelist = null;
            return this;
        }

        public Builder mapping(Map<String, String> mapping) {
            this.mapping = mapping;
            return this;
        }

        public Options build() {
            return new Options(whitelist, blacklist, mapping);
        }
    }
}