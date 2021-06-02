package kr.chuyong.jdu.commands;

import api.cosmoage.global.commandannotation.CosmoCMD;
import kr.chuyong.jdu.session.JduSession;
import kr.chuyong.jdu.session.SessionFactory;
import org.bukkit.command.CommandSender;

public class CommandField {
    @CosmoCMD(parent="dfield", op=true, console = true)
    public void onMainCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        sender.sendMessage("/dfield assign [fieldKey] [classKey] [fieldName]");
        sender.sendMessage("/dfield set [fieldKey] [instance|null] [instanceKey||null]");
    }
    @CosmoCMD(parent="dfield", child="assign", op=true, console = true, minArgs = 4)
    public void onAssignCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.insertField(args[1], args[2], args[3]))
                sender.sendMessage("Successfully assigned field");
            else
                sender.sendMessage("Class not found!");
        }catch(Exception ex){
            sender.sendMessage("Encountered Exception!");
            sender.sendMessage(ex.getMessage());
            if(session.printStack){
                for(StackTraceElement el : ex.getStackTrace()){
                    sender.sendMessage(el.toString());
                }
            }
        }
    }
    @CosmoCMD(parent="dfield", child="set", op=true, console = true, minArgs = 3)
    public void onSetCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.setField(args[1], args[2], args[3]))
                sender.sendMessage("Successfully setted field");
            else
                sender.sendMessage("Class not found!");
        }catch(Exception ex){
            sender.sendMessage("Encountered Exception!");
            sender.sendMessage(ex.getMessage());
            if(session.printStack){
                for(StackTraceElement el : ex.getStackTrace()){
                    sender.sendMessage(el.toString());
                }
            }
        }
    }
}
