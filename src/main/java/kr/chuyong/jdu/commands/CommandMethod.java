package kr.chuyong.jdu.commands;

import api.cosmoage.global.commandannotation.CosmoCMD;
import kr.chuyong.jdu.session.JduSession;
import kr.chuyong.jdu.session.SessionFactory;
import org.bukkit.command.CommandSender;

public class CommandMethod {
    @CosmoCMD(parent="dmethod", op=true, console = true)
    public void onMainCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        sender.sendMessage("/dmethod assign [methodKey] [classKey] [methodName]");
        sender.sendMessage("/dmethod invoke [methodKey] [instance] [instanceName..]");
    }
    @CosmoCMD(parent="dmethod", child="assign", op=true, console = true, minArgs = 4)
    public void onAssignCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.insertMethod(args[1], args[2], args[3]))
                sender.sendMessage("Successfully assigned method");
            else
                sender.sendMessage("Class not found or no method found!");
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
    @CosmoCMD(parent="dmethod", child="invoke", op=true, console = true, minArgs = 3)
    public void onInvoke(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            if(session.invokeMethod(args[1], args[2], args))
                sender.sendMessage("Successfully invoked method");
            else
                sender.sendMessage("Class not found or no method found!");
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
