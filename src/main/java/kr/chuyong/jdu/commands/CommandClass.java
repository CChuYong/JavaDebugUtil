package kr.chuyong.jdu.commands;

import api.cosmoage.global.commandannotation.CosmoCMD;
import kr.chuyong.jdu.session.JduSession;
import kr.chuyong.jdu.session.SessionFactory;
import org.bukkit.command.CommandSender;

public class CommandClass {
    @CosmoCMD(parent="dreset", op=true, console = true)
    public void onDReset(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        session.resetSession();
        sender.sendMessage("Session reset completed!");
    }
    @CosmoCMD(parent="dstack", op=true, console = true)
    public void onDStack(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        if(session.printStack){
            session.printStack = false;
            sender.sendMessage("Now printstack will disabled");
        }else{
            session.printStack = true;
            sender.sendMessage("Now printstack will enabled");
        }
    }
    @CosmoCMD(parent="dclass", op=true, console = true)
    public void onMainCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        sender.sendMessage("/dclass assign [key] [classname]");
    }
    @CosmoCMD(parent="dclass", child = "assign", op=true, console = true, minArgs = 3)
    public void onAssignCommand(CommandSender sender, String[] args){
        JduSession session = SessionFactory.getSession(sender);
        try{
            session.insertClass(args[1], args[2]);
            sender.sendMessage("Class injection success!");
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
