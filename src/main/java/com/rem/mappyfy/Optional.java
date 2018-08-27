package com.rem.mappyfy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Optional {

    private final List<Branch> branches = new ArrayList<>();

    public Optional(Object arg) {
        branches.add(new Branch(arg));
    }

    public Optional(Collection args) {
        for (var o : args) {
            branches.add(new Branch(o));
        }
    }

    public Optional(Object[] args) {
        if (args != null) {
            for (var o : args) {
                branches.add(new Branch(o));
            }
        }
    }

    public List<Branch> getBranches() {
        return branches;
    }
}
