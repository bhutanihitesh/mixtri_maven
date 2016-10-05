package com.mixtri.media.transcoder;

import java.util.HashSet;
import java.util.Set;

import com.jezhumble.javasysmon.OsProcess;
import com.jezhumble.javasysmon.ProcessVisitor;

public class DirectUDKChildProcessVisitor implements ProcessVisitor {
    Set<Integer> udkPids = new HashSet<Integer>();

    @Override
    public boolean visit(OsProcess op, int i) {
       // if(op.processInfo().getName().equals("UDK.exe")){
            udkPids.add(op.processInfo().getPid());
        //}
        return false;
    }

    public Set<Integer> getUdkPids() {
        return udkPids;
    }




}
