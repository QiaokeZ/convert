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

    public static final class OutputPattern {

        Set<String> blacklist;
        Set<String> whitelist;

        private OutputPattern() {
        }

        public static OutputPattern blacklist(Set<String> blacklist) {
            OutputPattern outputPattern = new OutputPattern();
            outputPattern.setBlacklist(blacklist);
            return outputPattern;
        }

        public static OutputPattern whitelist(Set<String> whitelist) {
            OutputPattern outputPattern = new OutputPattern();
            outputPattern.setWhitelist(whitelist);
            return outputPattern;
        }

        private void setBlacklist(Set<String> blacklist) {
            this.blacklist = blacklist;
        }

        private void setWhitelist(Set<String> whitelist) {
            this.whitelist = whitelist;
        }
    }

    public static final class Builder {

        Set<String> whitelist;
        Set<String> blacklist;

        Map<String, String> mapping = new HashMap<>();

        public Builder outputPattern(OutputPattern outputPattern) {
            this.whitelist = outputPattern.whitelist;
            this.blacklist = outputPattern.blacklist;
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